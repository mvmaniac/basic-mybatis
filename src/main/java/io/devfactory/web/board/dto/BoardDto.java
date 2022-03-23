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

}
