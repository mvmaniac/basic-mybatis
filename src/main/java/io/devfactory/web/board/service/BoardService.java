package io.devfactory.web.board.service;

import io.devfactory.global.common.annotation.MariadbTransaction;
import io.devfactory.global.error.ServiceRuntimeException;
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
  public void insertBoard(BoardVo boardVo) {
    boardMapper.insertBoard(boardVo);
  }

  @MariadbTransaction
  public void insertBoardException(BoardVo boardVo) throws Exception {
    boardMapper.insertBoard(boardVo);
    throwException();
  }

  @MariadbTransaction
  public void insertBoardRuntimeException(BoardVo boardVo) {
    boardMapper.insertBoard(boardVo);
    throwRuntimeException();
  }

  @MariadbTransaction
  public void updateBoard(Long seq, BoardVo boardVo) {
    boardMapper.updateBoard(seq, boardVo);
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
