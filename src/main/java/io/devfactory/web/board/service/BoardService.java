package io.devfactory.web.board.service;

import io.devfactory.global.common.annotation.MariadbTransaction;
import io.devfactory.global.error.ServiceRuntimeException;
import io.devfactory.web.board.dto.BoardDto;
import io.devfactory.web.board.mapper.BoardMapper;
import io.devfactory.web.board.vo.BoardVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings({"ClassCanBeRecord", "squid:S112"})
@RequiredArgsConstructor
@MariadbTransaction(readOnly = true)
@Service
public class BoardService {

  private final BoardMapper boardMapper;

  public List<BoardVo> selectBoards() {
    return boardMapper.selectBoards();
  }

  public BoardVo selectBoard(Long seq) {
    return boardMapper.selectBoard(seq).orElseGet(() -> BoardVo.builder().build());
  }

  @MariadbTransaction
  public void insertBoard(BoardDto boardDto) {
    boardMapper.insertBoard(boardDto);
  }

  @MariadbTransaction
  public void insertBoardException(BoardDto boardDto) throws Exception {
    boardMapper.insertBoard(boardDto);
    throwException();
  }

  @MariadbTransaction
  public void insertBoardRuntimeException(BoardDto boardDto) {
    boardMapper.insertBoard(boardDto);
    throwRuntimeException();
  }

  @MariadbTransaction
  public void updateBoard(Long seq, BoardDto boardDto) {
    boardMapper.updateBoard(seq, boardDto);
  }

  @MariadbTransaction
  public void deleteBoard(Long seq) {
    boardMapper.deleteBoard(seq);
  }

  private void throwException() throws Exception {
    throw new Exception("mariadb 에러 발생");
  }

  private void throwRuntimeException() {
    throw new ServiceRuntimeException("mariadb 런타임 에러 발생");
  }

}
