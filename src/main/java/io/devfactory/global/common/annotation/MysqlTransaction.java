package io.devfactory.global.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Transactional // JTA 테스트 시 사용
@Transactional(transactionManager = "mySqlTxManager")
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MysqlTransaction {

  String value() default "";

  @AliasFor(value = "readOnly", annotation = Transactional.class)
  boolean readOnly() default false;

  @AliasFor(value = "rollbackFor", annotation = Transactional.class)
  Class<? extends Throwable>[] rollbackFor() default {};

}
