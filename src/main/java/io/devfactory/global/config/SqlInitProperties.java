package io.devfactory.global.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

@Configuration
public class SqlInitProperties {

  @ConfigurationProperties("sql.init.mysql")
  @Bean
  public SqlInitProperty mysqlSqlInitProperty() {
    return new SqlInitProperty();
  }

  @ConfigurationProperties("sql.init.mariadb")
  @Bean
  public SqlInitProperty mariadbSqlInitProperty() {
    return new SqlInitProperty();

  }

  // Bean으로 등록시 binding 되기 때문에 setter가 있어야 함
  @Setter
  @Getter
  public static class SqlInitProperty {

    private List<String> locations;

    public ClassPathResource[] toClassPathResources() {
      return locations.stream().map(ClassPathResource::new)
          .toList().toArray(new ClassPathResource[0]);
    }

  }

}
