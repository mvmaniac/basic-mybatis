package io.devfactory.global.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.devfactory.global.annotation.MariadbMapper;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;

import javax.sql.DataSource;

@RequiredArgsConstructor
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
    mybatisProperties.setConfiguration(obtainMybatisConfiguration());
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
  public SqlSessionTemplate mariadbSqlSessionTemplate(
      @Qualifier("mariadbSqlSessionFactory") SqlSessionFactory mariadbSqlSessionFactory) {
    return new SqlSessionTemplate(mariadbSqlSessionFactory);
  }

  @Bean
  DataSourceInitializer mariadbDataSourceInitializer(
      @Qualifier("mariadbDataSource") DataSource mariadbDataSource,
      @Qualifier("mariadbSqlInitProperty") SqlInitProperties.SqlInitProperty mariadbSqlInitProperty) {
    return super.buildDataSourceInitializer(mariadbDataSource, mariadbSqlInitProperty.toClassPathResources());
  }

}

