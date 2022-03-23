package io.devfactory.global.common.web;

import io.devfactory.web.board.service.BoardService;
import io.devfactory.web.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommonService {

  private final BoardService boardService;
  private final MemberService memberService;

}
