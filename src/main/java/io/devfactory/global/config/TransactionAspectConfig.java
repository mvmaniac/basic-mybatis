package io.devfactory.global.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.List;
import java.util.Properties;

@ConditionalOnProperty(value = "app.aop.transaction.enabled", havingValue = "true")
@Configuration
public class TransactionAspectConfig {

  public static final int TRANSACTION_METHOD_TIMEOUT = 30;

  private final PlatformTransactionManager mySqlTxManager;
  private final PlatformTransactionManager mariadbTxManager;

  public TransactionAspectConfig(
      @Qualifier("mySqlTxManager") PlatformTransactionManager mySqlTxManager,
      @Qualifier("mariadbTxManager") PlatformTransactionManager mariadbTxManager) {
    this.mySqlTxManager = mySqlTxManager;
    this.mariadbTxManager = mariadbTxManager;
  }

  @Bean
  public Advisor mysqlTxAdvisor() {
    return buildDefaultPointcutAdvisor(mysqlTxAdvice());
  }

  @Bean
  public TransactionInterceptor mysqlTxAdvice() {
    return buildTransactionAdvice(mySqlTxManager);
  }

  @Bean
  public Advisor mariadbTxAdvisor() {
    return buildDefaultPointcutAdvisor(mariadbTxAdvice());
  }

  @Bean
  public TransactionInterceptor mariadbTxAdvice() {
    return buildTransactionAdvice(mariadbTxManager);
  }

  // mySqlTxManager, mariadbTxManager에 맞게 설정 필요함...현재는 그냥 전체 다...
  private DefaultPointcutAdvisor buildDefaultPointcutAdvisor(TransactionInterceptor interceptor) {
    final var pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression("execution(* io.devfactory.web..service.*Service.*(..))");
    return new DefaultPointcutAdvisor(pointcut, interceptor);
  }

  // @Transactional 보다 우선순위가 높은듯...
  private TransactionInterceptor buildTransactionAdvice(
      PlatformTransactionManager transactionManager) {
    final var rollbackRules = List.of(new RollbackRuleAttribute(Exception.class));

    final var readOnlyAttribute = new DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED);
    readOnlyAttribute.setReadOnly(true);
    readOnlyAttribute.setTimeout(TRANSACTION_METHOD_TIMEOUT);

    final var writeAttribute = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules);
    writeAttribute.setTimeout(TRANSACTION_METHOD_TIMEOUT);

    final var requiresNewAttribute = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRES_NEW, rollbackRules);
    requiresNewAttribute.setTimeout(TRANSACTION_METHOD_TIMEOUT);

    final var transactionAttributes = new Properties();
    transactionAttributes.setProperty("*RequiresNewTransaction*", requiresNewAttribute.toString());
    transactionAttributes.setProperty("select*", readOnlyAttribute.toString());
    transactionAttributes.setProperty("insert*", writeAttribute.toString());
    transactionAttributes.setProperty("update*", writeAttribute.toString());
    transactionAttributes.setProperty("delete*", writeAttribute.toString());

    final var transactionInterceptor = new TransactionInterceptor();
    transactionInterceptor.setTransactionAttributes(transactionAttributes);
    transactionInterceptor.setTransactionManager(transactionManager);
    return transactionInterceptor;
  }

}
