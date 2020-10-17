package io.devfactory.member;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

  private final MemberMapper memberMapper;

  public List<Member> selectMembers() {
    return memberMapper.selectMembers();
  }

}
