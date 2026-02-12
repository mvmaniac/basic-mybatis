package io.devfactory.global.config;

import io.devfactory.global.common.interceptor.MybatisAuditInterceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

public class DataSourceConfigSupport {

  @Value("${app.mybatis.interceptor.enabled}")
  private boolean isMybatisInterceptorEnable;

  @ConfigurationProperties(prefix = "mybatis.configuration")
  @Scope(value = "prototype")
  @Bean
  public MybatisProperties.CoreConfiguration buildMybatisConfiguration() {
    return new MybatisProperties.CoreConfiguration();
  }

  protected SqlSessionFactory buildSqlSessionFactory(DataSource dataSource,
      MybatisProperties mybatisProperties)
      throws Exception {
    SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
    factory.setDataSource(dataSource);
    factory.setVfs(SpringBootVFS.class);

    factory.setConfiguration(mapTo(mybatisProperties.getConfiguration()));
    factory.setMapperLocations(mybatisProperties.resolveMapperLocations());
    factory.setTypeAliasesPackage(mybatisProperties.getTypeAliasesPackage());
    factory.setTypeHandlersPackage(mybatisProperties.getTypeHandlersPackage());

    if (this.isMybatisInterceptorEnable) {
      factory.setPlugins(new MybatisAuditInterceptor());
    }

    return factory.getObject();
  }

  protected DataSourceInitializer buildDataSourceInitializer(DataSource dataSource,
      ClassPathResource... resources) {
    final var dataSourceInitializer = new DataSourceInitializer();
    dataSourceInitializer.setDataSource(dataSource);
    dataSourceInitializer.setDatabasePopulator(new ResourceDatabasePopulator(resources));
    return dataSourceInitializer;
  }

  // MybatisAutoConfiguration 파일 참고, 무조건 yml로 설정하는게 아니라면
  // configLocation을 사용하는게 나을 것 같음...
  private Configuration mapTo(MybatisProperties.CoreConfiguration source) {
    Configuration target = new Configuration();
    
    PropertyMapper mapper = PropertyMapper.get();
    mapper.from(source.getSafeRowBoundsEnabled()).to(target::setSafeRowBoundsEnabled);
    mapper.from(source.getSafeResultHandlerEnabled()).to(target::setSafeResultHandlerEnabled);
    mapper.from(source.getMapUnderscoreToCamelCase()).to(target::setMapUnderscoreToCamelCase);
    mapper.from(source.getAggressiveLazyLoading()).to(target::setAggressiveLazyLoading);
    mapper.from(source.getUseGeneratedKeys()).to(target::setUseGeneratedKeys);
    mapper.from(source.getUseColumnLabel()).to(target::setUseColumnLabel);
    mapper.from(source.getCacheEnabled()).to(target::setCacheEnabled);
    mapper.from(source.getCallSettersOnNulls()).to(target::setCallSettersOnNulls);
    mapper.from(source.getUseActualParamName()).to(target::setUseActualParamName);
    mapper.from(source.getReturnInstanceForEmptyRow()).to(target::setReturnInstanceForEmptyRow);
    mapper.from(source.getShrinkWhitespacesInSql()).to(target::setShrinkWhitespacesInSql);
    mapper.from(source.getNullableOnForEach()).to(target::setNullableOnForEach);
    mapper.from(source.getArgNameBasedConstructorAutoMapping()).to(target::setArgNameBasedConstructorAutoMapping);
    mapper.from(source.getLazyLoadingEnabled()).to(target::setLazyLoadingEnabled);
    mapper.from(source.getLogPrefix()).to(target::setLogPrefix);
    mapper.from(source.getLazyLoadTriggerMethods()).to(target::setLazyLoadTriggerMethods);
    mapper.from(source.getDefaultStatementTimeout()).to(target::setDefaultStatementTimeout);
    mapper.from(source.getDefaultFetchSize()).to(target::setDefaultFetchSize);
    mapper.from(source.getLocalCacheScope()).to(target::setLocalCacheScope);
    mapper.from(source.getJdbcTypeForNull()).to(target::setJdbcTypeForNull);
    mapper.from(source.getDefaultResultSetType()).to(target::setDefaultResultSetType);
    mapper.from(source.getDefaultExecutorType()).to(target::setDefaultExecutorType);
    mapper.from(source.getAutoMappingBehavior()).to(target::setAutoMappingBehavior);
    mapper.from(source.getAutoMappingUnknownColumnBehavior()).to(target::setAutoMappingUnknownColumnBehavior);
    mapper.from(source.getVariables()).to(target::setVariables);
    mapper.from(source.getLogImpl()).to(target::setLogImpl);
    mapper.from(source.getVfsImpl()).to(target::setVfsImpl);
    mapper.from(source.getDefaultSqlProviderType()).to(target::setDefaultSqlProviderType);
    mapper.from(source.getConfigurationFactory()).to(target::setConfigurationFactory);
    mapper.from(source.getDefaultEnumTypeHandler()).to(target::setDefaultEnumTypeHandler);
    mapper.from(source.getDatabaseId()).to(target::setDatabaseId);

    return target;
  }

}
