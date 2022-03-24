package io.devfactory.global.config;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

public class DataSourceConfigSupport {

  @ConfigurationProperties(prefix = "mybatis.configuration")
  @Scope(value = "prototype")
  @Bean
  public Configuration buildMybatisConfiguration() {
    return new Configuration();
  }

  protected SqlSessionFactory buildSqlSessionFactory(DataSource dataSource,
      MybatisProperties mybatisProperties)
      throws Exception {
    SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
    factory.setDataSource(dataSource);
    factory.setVfs(SpringBootVFS.class);

    factory.setConfiguration(mybatisProperties.getConfiguration());
    factory.setMapperLocations(mybatisProperties.resolveMapperLocations());
    factory.setTypeAliasesPackage(mybatisProperties.getTypeAliasesPackage());
    factory.setTypeHandlersPackage(mybatisProperties.getTypeHandlersPackage());

    return factory.getObject();
  }

  protected DataSourceInitializer buildDataSourceInitializer(DataSource dataSource,
      ClassPathResource... resources) {
    final var dataSourceInitializer = new DataSourceInitializer();
    dataSourceInitializer.setDataSource(dataSource);
    dataSourceInitializer.setDatabasePopulator(new ResourceDatabasePopulator(resources));
    return dataSourceInitializer;
  }

}
