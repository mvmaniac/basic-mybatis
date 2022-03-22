package io.devfactory.web.member.service;

import io.devfactory.web.member.vo.MemberVo;
import io.devfactory.web.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ClassCanBeRecord")
@RequiredArgsConstructor
@Service
public class MemberService {

  private final MemberMapper memberMapper;

  public List<MemberVo> selectMembers() {
    return memberMapper.selectMembers();
  }

  public MemberVo selectMember(long id) {
    return memberMapper.selectMember(id).orElseGet(() -> MemberVo.builder().build());
  }

  public List<Map<String, Object>> selectMembersToMap() {
    return memberMapper.selectMembersToMap();
  }

  public Map<String, Object> selectMemberToMap(long id) {
    return memberMapper.selectMemberToMap(id).orElseGet(Collections::emptyMap);
  }

}
