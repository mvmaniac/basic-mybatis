package io.devfactory.global.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.devfactory.global.common.annotation.MariadbMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@MapperScan(basePackages = "io.devfactory.web", annotationClass = MariadbMapper.class, sqlSessionFactoryRef = "mariadbSqlSessionFactory")
@Configuration
public class MariadbDataSourceConfig extends DataSourceConfigSupport {

  @ConfigurationProperties(prefix = "spring.datasource.hikari.mariadb")
  @Bean
  public HikariConfig mariadbHikariConfig() {
    return new HikariConfig();
  }

  @Bean
  public DataSource mariadbDataSource() {
    return new HikariDataSource(mariadbHikariConfig());
  }

  @ConfigurationProperties(prefix = "mybatis.mariadb")
  @Bean
  public MybatisProperties mariadbMybatisProperties() {
    MybatisProperties mybatisProperties = new MybatisProperties();
    mybatisProperties.setConfiguration(buildMybatisConfiguration());
    return mybatisProperties;
  }

  @Bean
  public SqlSessionFactory mariadbSqlSessionFactory(
      @Qualifier("mariadbDataSource") DataSource mariadbDataSource,
      @Qualifier("mariadbMybatisProperties") MybatisProperties mariadbMybatisProperties)
      throws Exception {
    return super.buildSqlSessionFactory(mariadbDataSource, mariadbMybatisProperties);
  }

  @Bean
  public SqlSession mariadbSqlSessionTemplate(
      @Qualifier("mariadbSqlSessionFactory") SqlSessionFactory mariadbSqlSessionFactory) {
    return new SqlSessionTemplate(mariadbSqlSessionFactory);
  }

  @Bean
  public PlatformTransactionManager mariadbTxManager(
      @Qualifier("mariadbDataSource") DataSource mariadbDataSource) {
    return new DataSourceTransactionManager(mariadbDataSource);
  }

  @Bean
  public DataSourceInitializer mariadbDataSourceInitializer(
      @Qualifier("mariadbDataSource") DataSource mariadbDataSource,
      @Qualifier("mariadbSqlInitProperty") SqlInitProperties.SqlInitProperty mariadbSqlInitProperty) {
    return super.buildDataSourceInitializer(mariadbDataSource, mariadbSqlInitProperty.toClassPathResources());
  }

}

