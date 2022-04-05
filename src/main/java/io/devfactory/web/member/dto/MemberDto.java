package io.devfactory.web.member.dto;

import io.devfactory.web.member.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.type.Alias;

@SuppressWarnings("ClassCanBeRecord")
@Alias("MemberDto")
@RequiredArgsConstructor
public class MemberDto {

  private final String email;
  private final String name;
  private final String passwd;

  public static MemberVo mockOf() {
    return MemberVo.builder().email("mock@gamil.com").name("mock").passwd("1234").build();
  }

  public MemberVo toVo() {
    return MemberVo.builder().email(email).name(name).passwd(passwd).build();
  }

}
