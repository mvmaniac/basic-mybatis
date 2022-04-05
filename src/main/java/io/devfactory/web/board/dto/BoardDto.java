package io.devfactory.web.board.dto;

import io.devfactory.web.board.vo.BoardVo;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.type.Alias;

@SuppressWarnings("ClassCanBeRecord")
@Alias("BoardDto")
@RequiredArgsConstructor
public class BoardDto {

  private final String title;
  private final String contents;
  private final String writeId;

  public static BoardVo mockOf() {
    return BoardVo.builder().title("mock 타이틀").contents("mock 내용").writeId("mock@gmail.com").build();
  }

  public BoardVo toVo() {
    return BoardVo.builder().title(title).contents(contents).writeId(writeId).build();
  }

}
