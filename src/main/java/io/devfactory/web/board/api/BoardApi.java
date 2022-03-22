package io.devfactory.web.board.api;

import io.devfactory.web.board.service.BoardService;
import io.devfactory.web.board.vo.BoardVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/boards")
@RestController
public class BoardApi {

  private final BoardService boardService;

  @GetMapping
  public List<BoardVo> selectMembers() {
    return boardService.selectBoards();
  }

  @GetMapping("/{id:[\\d]+}")
  public BoardVo selectBoard(@PathVariable("id") long id) {
    return boardService.selectBoard(id);
  }

}
