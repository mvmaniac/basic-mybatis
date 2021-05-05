package io.devfactory.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.StringJoiner;

import static lombok.AccessLevel.PROTECTED;

@Alias("memberAlias")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Member {

  private long seq;
  private String name;
  private String passwd;
  private String email;
  private int loginCount;
  private LocalDateTime lastLoginAt;
  private LocalDateTime createdDate;

  @Builder
  private Member(long seq, String name, String passwd, String email, int loginCount,
      LocalDateTime lastLoginAt, LocalDateTime createdDate) {
    this.seq = seq;
    this.name = name;
    this.passwd = passwd;
    this.email = email;
    this.loginCount = loginCount;
    this.lastLoginAt = lastLoginAt;
    this.createdDate = createdDate;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Member.class.getSimpleName() + "[", "]")
        .add("seq=" + seq)
        .add("name='" + name + "'")
        .add("passwd='" + passwd + "'")
        .add("email='" + email + "'")
        .add("loginCount=" + loginCount)
        .add("lastLoginAt=" + lastLoginAt)
        .add("createdDate=" + createdDate)
        .toString();
  }

}
