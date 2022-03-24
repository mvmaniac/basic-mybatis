package io.devfactory.web.member.api;

import io.devfactory.web.member.dto.MemberDto;
import io.devfactory.web.member.service.MemberService;
import io.devfactory.web.member.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@SuppressWarnings("squid:S112")
@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberApi {

  private final MemberService memberService;

  @GetMapping
  public ResponseEntity<List<MemberVo>> selectMembers() {
    return ResponseEntity.ok(memberService.selectMembers());
  }

  @GetMapping("/{seq:[\\d]+}")
  public ResponseEntity<MemberVo> selectMember(@PathVariable("seq") Long seq) {
    return ResponseEntity.ok(memberService.selectMember(seq));
  }

  @GetMapping("/map")
  public ResponseEntity<List<Map<String, Object>>> selectMembersToMap() {
    return ResponseEntity.ok(memberService.selectMembersToMap());
  }

  @GetMapping("/map/{seq:[\\d]+}")
  public ResponseEntity<Map<String, Object>> selectMemberToMap(@PathVariable("seq") Long seq) {
    return ResponseEntity.ok(memberService.selectMemberToMap(seq));
  }

  @PostMapping
  public ResponseEntity<Object> insertMember(@RequestBody MemberDto memberDto) {
    memberService.insertMember(memberDto);
    return ResponseEntity.ok().build();
  }

  @PostMapping("ex")
  public ResponseEntity<Object> insertMemberWithException(
      @RequestBody MemberDto memberDto) throws Exception {
    memberService.insertMemberException(memberDto);
    return ResponseEntity.ok().build();
  }

  @PostMapping("run-ex")
  public ResponseEntity<Object> insertMemberWithRuntimeException(@RequestBody MemberDto memberDto) {
    memberService.insertMemberRuntimeException(memberDto);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{seq:[\\d]+}")
  public ResponseEntity<Object> updateMember(@PathVariable("seq") Long seq,
      @RequestBody MemberDto memberDto) {
    memberService.updateMember(seq, memberDto);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{seq:[\\d]+}")
  public ResponseEntity<Object> updateBoard(@PathVariable("seq") Long seq) {
    memberService.deleteMember(seq);
    return ResponseEntity.ok().build();
  }

}
