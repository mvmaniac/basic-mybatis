package io.devfactory.global.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.devfactory.global.annotation.MysqlMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;

import javax.sql.DataSource;

@MapperScan(basePackages = "io.devfactory.web", annotationClass = MysqlMapper.class, sqlSessionFactoryRef = "mysqlSqlSessionFactory")
@Configuration
public class MysqlDataSourceConfig extends DataSourceConfigSupport {

  @Primary
  @ConfigurationProperties(prefix = "spring.datasource.hikari.mysql")
  @Bean
  public HikariConfig mysqlHikariConfig() {
    return new HikariConfig();
  }

  @Primary
  @Bean
  public DataSource mysqlDataSource() {
    return new HikariDataSource(mysqlHikariConfig());
  }

  @Primary
  @ConfigurationProperties(prefix = "mybatis.mysql")
  @Bean
  public MybatisProperties mysqlMybatisProperties() {
    MybatisProperties mybatisProperties = new MybatisProperties();
    mybatisProperties.setConfiguration(obtainMybatisConfiguration());
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
  public SqlSessionTemplate mysqlSqlSessionTemplate(
      @Qualifier("mysqlSqlSessionFactory") SqlSessionFactory mysqlSqlSessionFactory) {
    return new SqlSessionTemplate(mysqlSqlSessionFactory);
  }

  @Bean
  public DataSourceInitializer mysqlDataSourceInitializer(
      @Qualifier("mysqlDataSource") DataSource mysqlDataSource,
      @Qualifier("mysqlSqlInitProperty") SqlInitProperties.SqlInitProperty mysqlSqlInitProperty) {
    return super.buildDataSourceInitializer(mysqlDataSource, mysqlSqlInitProperty.toClassPathResources());
  }

}
