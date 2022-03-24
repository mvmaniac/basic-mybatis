package io.devfactory.web.board.api;

import io.devfactory.web.board.dto.BoardDto;
import io.devfactory.web.board.service.BoardService;
import io.devfactory.web.board.vo.BoardVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("squid:S112")
@RequiredArgsConstructor
@RequestMapping("/api/boards")
@RestController
public class BoardApi {

  private final BoardService boardService;

  @GetMapping
  public ResponseEntity<List<BoardVo>> selectMembers() {
    return ResponseEntity.ok(boardService.selectBoards());
  }

  @GetMapping("/{seq:[\\d]+}")
  public ResponseEntity<BoardVo> selectBoard(@PathVariable("seq") Long seq) {
    return ResponseEntity.ok(boardService.selectBoard(seq));
  }

  @PostMapping
  public ResponseEntity<Object> insertBoard(@RequestBody BoardDto boardDto) {
    boardService.insertBoard(boardDto);
    return ResponseEntity.ok().build();
  }

  @PostMapping("ex")
  public ResponseEntity<Object> insertBoardWithException(
      @RequestBody BoardDto boardDto) throws Exception {
    boardService.insertBoardWithException(boardDto);
    return ResponseEntity.ok().build();
  }

  @PostMapping("run-ex")
  public ResponseEntity<Object> insertBoardWithRuntimeException(@RequestBody BoardDto boardDto) {
    boardService.insertBoardWithRuntimeException(boardDto);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{seq:[\\d]+}")
  public ResponseEntity<Object> updateBoard(@PathVariable("seq") Long seq,
      @RequestBody BoardDto boardDto) {
    boardService.updateBoard(seq, boardDto);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{seq:[\\d]+}")
  public ResponseEntity<Object> updateBoard(@PathVariable("seq") Long seq) {
    boardService.deleteBoard(seq);
    return ResponseEntity.ok().build();
  }

}
