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

}
