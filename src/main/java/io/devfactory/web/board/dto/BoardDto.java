package io.devfactory.web.board.dto;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.type.Alias;

@SuppressWarnings("ClassCanBeRecord")
@Alias("BoardDto")
@RequiredArgsConstructor
public class BoardDto {

  private final String title;
  private final String contents;
  private final String writeId;

  public static BoardDto mockOf() {
    return new BoardDto("mock 타이틀", "mock 내용", "mock@gmail.com");
  }

}
