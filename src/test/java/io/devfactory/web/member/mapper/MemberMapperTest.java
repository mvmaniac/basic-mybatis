package io.devfactory.web.member.mapper;

import com.ulisesbocchio.jasyptspringbootstarter.JasyptSpringBootAutoConfiguration;
import io.devfactory.global.config.JasyptConfig;
import io.devfactory.global.config.MysqlDataSourceConfig;
import io.devfactory.global.config.SqlInitProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@Slf4j
@Import({JasyptSpringBootAutoConfiguration.class, JasyptConfig.class, SqlInitProperties.class, MysqlDataSourceConfig.class})
@TestPropertySource(properties = {"jasypt.encryptor.password=dbgmltlr"})
@AutoConfigureTestDatabase(replace = NONE)
@MybatisTest
class MemberMapperTest {

  @Autowired
  private MemberMapper memberMapper;

  @DisplayName("멤버 목록 조회 테스트")
  @Test
  void testSelectMembers() {
    var foundMembers = memberMapper.selectMembers();
    assertThat(foundMembers).isNotEmpty();
  }

}
