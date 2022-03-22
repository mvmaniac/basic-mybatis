package io.devfactory.web.board.service;

import io.devfactory.web.board.mapper.BoardMapper;
import io.devfactory.web.board.vo.BoardVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@SuppressWarnings("ClassCanBeRecord")
@RequiredArgsConstructor
@Service
public class BoardService {

  private final BoardMapper boardMapper;

  public List<BoardVo> selectBoards() {
    return boardMapper.selectBoards();
  }

  public BoardVo selectBoard(long id) {
    return boardMapper.selectBoard(id).orElseGet(() -> BoardVo.builder().build());
  }

}
