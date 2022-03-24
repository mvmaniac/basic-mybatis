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

@SuppressWarnings({"ClassCanBeRecord", "squid:S112"})
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
  }

  @MysqlTx
  public void insertMemberException(MemberDto memberDto) throws Exception {
    memberMapper.insertMember(memberDto);
    throwException();
  }

  @MysqlTx
  public void insertMemberRuntimeException(MemberDto memberDto) {
    memberMapper.insertMember(memberDto);
    throwRuntimeException();
  }

  @MysqlTx
  public void updateMember(Long seq, MemberDto memberDto) {
    memberMapper.updateMember(seq, memberDto);
  }

  @MysqlTx
  public void deleteMember(Long seq) {
    memberMapper.deleteMember(seq);
  }

  private void throwException() throws Exception {
    throw new Exception("mysql 에러 발생");
  }

  private void throwRuntimeException() {
    throw new ServiceRuntimeException("mysql 런타임 에러 발생");
  }

}
