package io.devfactory.web.member.mapper;

import io.devfactory.global.annotation.MysqlMapper;
import io.devfactory.web.member.vo.MemberVo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@MysqlMapper
public interface MemberMapper {

  List<MemberVo> selectMembers();

  Optional<MemberVo> selectMember(long id);

  List<Map<String, Object>> selectMembersToMap();

  Optional<Map<String, Object>> selectMemberToMap(long id);

}
