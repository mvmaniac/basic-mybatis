package io.devfactory.member;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberApi {

  private final MemberService memberService;

  @GetMapping
  public List<Member> selectMembers() {
    return memberService.selectMembers();
  }

  @GetMapping("/{id:[\\d]+}")
  public Member selectMember(@PathVariable("id") long id) {
    return memberService.selectMember(id);
  }

  @GetMapping("/map")
  public List<Map<String, Object>> selectMembersToMap() {
    return memberService.selectMembersToMap();
  }

  @GetMapping("/map/{id:[\\d]+}")
  public Map<String, Object> selectMemberToMap(@PathVariable("id") long id) {
    return memberService.selectMemberToMap(id);
  }

}
