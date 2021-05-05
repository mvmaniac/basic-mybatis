package io.devfactory.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.TestConstructor.AutowireMode.ALL;

@Slf4j
@RequiredArgsConstructor
@TestPropertySource(properties = {"jasypt.encryptor.password=dbgmltlr"})
@TestConstructor(autowireMode = ALL)
@SpringBootTest(classes = JasyptConfig.class)
class JasyptConfigTest {

  private final StringEncryptor encryptorBean;

  @DisplayName("jasypt 암/복호화 테스트")
  @ParameterizedTest(name = "{index}, {displayName}, plainText={0}")
  @ValueSource(strings = {"테스트", "test", "1234"})
  void jasyptEncryptionAndDecryptionTest(String plainText) {
    // given & when
    String encryptedText = encryptorBean.encrypt(plainText);
    String decryptedText = encryptorBean.decrypt(encryptedText);

    log.debug("[dev] encryptedText: {}", encryptedText);
    log.debug("[dev] decryptedText: {}", decryptedText);

    // then
    assertThat(plainText).isEqualTo(decryptedText);
  }

}
