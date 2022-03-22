package io.devfactory.global.config;

import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Configuration
public class JasyptConfig {

  private final Environment environment;

  @Bean(name = "encryptorBean")
  public StringEncryptor stringEncryptor() {
    final var encryptor = new PooledPBEStringEncryptor();
    final var config = new SimpleStringPBEConfig();

    // 기본 설정 값들
    config.setPassword(getAndVerifyPassword());
    config.setAlgorithm("PBEWithMD5AndDES");
    config.setKeyObtentionIterations("1000");
    config.setPoolSize("1");
    config.setProviderName("SunJCE");
    config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
    config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
    config.setStringOutputType("base64");

    encryptor.setConfig(config);
    return encryptor;
  }

  private String getAndVerifyPassword() {
    final var password = environment.getProperty("jasypt.encryptor.password");

    if (isNull(password)) {
      throw new IllegalArgumentException("jasypt.encryptor.password is empty...");
    }

    return password;
  }

}
