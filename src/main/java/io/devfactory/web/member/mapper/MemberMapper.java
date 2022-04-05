package io.devfactory.web.member.mapper;

import io.devfactory.global.common.annotation.MysqlMapper;
import io.devfactory.web.member.vo.MemberVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@MysqlMapper
public interface MemberMapper {

  List<MemberVo> selectMembers();

  Optional<MemberVo> selectMember(Long seq);

  List<Map<String, Object>> selectMembersToMap();

  Optional<Map<String, Object>> selectMemberToMap(Long seq);

  void insertMember(MemberVo memberVo);

  void updateMember(@Param("seq") Long seq, @Param("member") MemberVo memberVo);

  void deleteMember(Long seq);

}
