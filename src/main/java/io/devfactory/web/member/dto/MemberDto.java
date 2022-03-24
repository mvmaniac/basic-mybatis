package io.devfactory.web.member.dto;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.type.Alias;

@SuppressWarnings("ClassCanBeRecord")
@Alias("MemberDto")
@RequiredArgsConstructor
public class MemberDto {

  private final String email;
  private final String name;
  private final String passwd;

  public static MemberDto mockOf() {
    return new MemberDto("mock@gamil.com", "mock", "1234");
  }

}
