package io.devfactory.member;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

  List<Member> selectMembers();

  Optional<Member> selectMember(long id);

  List<Map<String, Object>> selectMembersToMap();

  Optional<Map<String, Object>> selectMemberToMap(long id);

}
