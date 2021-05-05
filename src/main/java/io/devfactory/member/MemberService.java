package io.devfactory.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class MemberService {

  private final MemberMapper memberMapper;

  public List<Member> selectMembers() {
    return memberMapper.selectMembers();
  }

  public Member selectMember(long id) {
    return memberMapper.selectMember(id).orElseGet(() -> Member.builder().build());
  }

  public List<Map<String, Object>> selectMembersToMap() {
    return memberMapper.selectMembersToMap();
  }

  public Map<String, Object> selectMemberToMap(long id) {
    return memberMapper.selectMemberToMap(id).orElseGet(Collections::emptyMap);
  }

}
