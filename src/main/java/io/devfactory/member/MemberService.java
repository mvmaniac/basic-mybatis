package io.devfactory.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberMapper memberMapper;

    public List<Member> selectMembers() {
        return memberMapper.selectMembers();
    }

}
