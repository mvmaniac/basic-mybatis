package io.devfactory.global.common.web;

import io.devfactory.global.error.ServiceRuntimeException;
import io.devfactory.web.board.dto.BoardDto;
import io.devfactory.web.board.service.BoardService;
import io.devfactory.web.member.dto.MemberDto;
import io.devfactory.web.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("ClassCanBeRecord")
@RequiredArgsConstructor
@Transactional // 별도의 설정을 하지 않으면 @Primary로 설정된 DataSource의 트랜잭션을 사용함
@Service
public class CommonService {

  private final BoardService boardService;
  private final MemberService memberService;

  public void insertRuntimeException() {
    boardService.insertBoard(BoardDto.mockOf());
    memberService.insertMember(MemberDto.mockOf());
    throwRuntimeException();
  }

  public void insertMemberBeforeBoardRuntimeException() {
    memberService.insertMember(MemberDto.mockOf());
    boardService.insertBoardRuntimeException(BoardDto.mockOf());
  }

  public void insertBoardBeforeMemberRuntimeException() {
    boardService.insertBoard(BoardDto.mockOf());
    memberService.insertMemberRuntimeException(MemberDto.mockOf());
  }

  private void throwRuntimeException() {
    throw new ServiceRuntimeException("common 런타임 에러 발생");
  }

}
