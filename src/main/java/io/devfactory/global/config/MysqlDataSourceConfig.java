package io.devfactory.global.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.devfactory.global.common.annotation.MysqlMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@MapperScan(basePackages = "io.devfactory.web", annotationClass = MysqlMapper.class, sqlSessionFactoryRef = "mysqlSqlSessionFactory")
@Configuration
public class MysqlDataSourceConfig extends DataSourceConfigSupport {

  @ConfigurationProperties(prefix = "spring.datasource.hikari.mysql")
  @Bean
  public HikariConfig mysqlHikariConfig() {
    return new HikariConfig();
  }

  @ConditionalOnProperty(value="spring.jta.enabled", havingValue = "false")
  @Primary
  @Bean
  public DataSource mysqlDataSource() {
    return new HikariDataSource(mysqlHikariConfig());
  }

  @ConditionalOnProperty(value="spring.jta.enabled", havingValue = "true")
  @ConfigurationProperties(prefix = "spring.jta.atomikos.datasource.mysql")
  @Bean("mysqlDataSource")
  public DataSource mysqlJtaDataSource() {
    return new AtomikosDataSourceBean();
  }

  @Primary
  @ConfigurationProperties(prefix = "mybatis.mysql")
  @Bean
  public MybatisProperties mysqlMybatisProperties() {
    MybatisProperties mybatisProperties = new MybatisProperties();
    mybatisProperties.setConfiguration(buildMybatisConfiguration());
    return mybatisProperties;
  }

  @Primary
  @Bean
  public SqlSessionFactory mysqlSqlSessionFactory(
      @Qualifier("mysqlDataSource") DataSource mysqlDataSource,
      @Qualifier("mysqlMybatisProperties") MybatisProperties mysqlMybatisProperties)
      throws Exception {
    return super.buildSqlSessionFactory(mysqlDataSource, mysqlMybatisProperties);
  }

  @Primary
  @Bean
  public SqlSession mysqlSqlSessionTemplate(
      @Qualifier("mysqlSqlSessionFactory") SqlSessionFactory mysqlSqlSessionFactory) {
    return new SqlSessionTemplate(mysqlSqlSessionFactory);
  }

  @ConditionalOnProperty(value="spring.jta.enabled", havingValue = "false")
  @Primary
  @Bean
  public PlatformTransactionManager mySqlTxManager(
      @Qualifier("mysqlDataSource") DataSource mysqlDataSource) {
    return new DataSourceTransactionManager(mysqlDataSource);
  }

  @ConditionalOnProperty(value="spring.jta.enabled", havingValue = "false")
  @Bean
  public DataSourceInitializer mysqlDataSourceInitializer(
      @Qualifier("mysqlDataSource") DataSource mysqlDataSource,
      @Qualifier("mysqlSqlInitProperty") SqlInitProperties.SqlInitProperty mysqlSqlInitProperty) {
    return super.buildDataSourceInitializer(mysqlDataSource, mysqlSqlInitProperty.toClassPathResources());
  }

}
