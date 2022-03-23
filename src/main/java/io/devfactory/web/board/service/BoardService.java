package io.devfactory.web.board.service;

import io.devfactory.global.common.annotation.MariadbTx;
import io.devfactory.global.error.ServiceRuntimeException;
import io.devfactory.web.board.dto.BoardDto;
import io.devfactory.web.board.mapper.BoardMapper;
import io.devfactory.web.board.vo.BoardVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("ClassCanBeRecord")
@RequiredArgsConstructor
@MariadbTx(readOnly = true)
@Service
public class BoardService {

  private final BoardMapper boardMapper;

  public List<BoardVo> selectBoards() {
    return boardMapper.selectBoards();
  }

  public BoardVo selectBoard(Long seq) {
    return boardMapper.selectBoard(seq).orElseGet(() -> BoardVo.builder().build());
  }

  @MariadbTx
  public void insertBoard(BoardDto boardDto) {
    boardMapper.insertBoard(boardDto);
    throwRuntimeException();
  }

  @MariadbTx
  public void updateBoard(Long seq, BoardDto boardDto) {
    boardMapper.updateBoard(seq, boardDto);
  }

  @MariadbTx
  public void deleteBoard(Long seq) {
    boardMapper.deleteBoard(seq);
  }

  private void throwRuntimeException() {
    throw new ServiceRuntimeException("mysql 런타임 에러 발생");
  }

}
