package io.devfactory.member;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/members")
  public List<Member> selectMembers() {
    return memberService.selectMembers();
  }

}
