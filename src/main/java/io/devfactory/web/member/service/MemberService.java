package io.devfactory.web.member.service;

import io.devfactory.global.common.annotation.MysqlTx;
import io.devfactory.global.error.ServiceRuntimeException;
import io.devfactory.web.member.dto.MemberDto;
import io.devfactory.web.member.mapper.MemberMapper;
import io.devfactory.web.member.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ClassCanBeRecord")
@RequiredArgsConstructor
@MysqlTx(readOnly = true)
@Service
public class MemberService {

  private final MemberMapper memberMapper;

  public List<MemberVo> selectMembers() {
    return memberMapper.selectMembers();
  }

  public MemberVo selectMember(Long seq) {
    return memberMapper.selectMember(seq).orElseGet(() -> MemberVo.builder().build());
  }

  public List<Map<String, Object>> selectMembersToMap() {
    return memberMapper.selectMembersToMap();
  }

  public Map<String, Object> selectMemberToMap(Long seq) {
    return memberMapper.selectMemberToMap(seq).orElseGet(Collections::emptyMap);
  }

  @MysqlTx
  public void insertMember(MemberDto memberDto) {
    memberMapper.insertMember(memberDto);
    throwRuntimeException();
  }


  @MysqlTx
  public void updateMember(Long seq, MemberDto memberDto) {
    memberMapper.updateMember(seq, memberDto);
    throwRuntimeException();
  }

  @MysqlTx
  public void deleteMember(Long seq) {
    memberMapper.deleteMember(seq);
    throwRuntimeException();
  }

  private void throwRuntimeException() {
    throw new ServiceRuntimeException("mysql 런타임 에러 발생");
  }

}
