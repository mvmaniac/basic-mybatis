package io.devfactory.web.member.api;

import java.util.List;
import java.util.Map;

import io.devfactory.web.member.vo.MemberVo;
import io.devfactory.web.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberApi {

  private final MemberService memberService;

  @GetMapping
  public List<MemberVo> selectMembers() {
    return memberService.selectMembers();
  }

  @GetMapping("/{id:[\\d]+}")
  public MemberVo selectMember(@PathVariable("id") long id) {
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
