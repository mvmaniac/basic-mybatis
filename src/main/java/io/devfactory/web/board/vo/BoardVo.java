package io.devfactory.web.board.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.StringJoiner;

import static lombok.AccessLevel.PROTECTED;

@Alias("BoardVo")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class BoardVo {

  private long seq;
  private String title;
  private String content;
  private String writeId;
  private LocalDateTime createdDate;

  @Builder
  private BoardVo(long seq, String title, String content, String writeId,
      LocalDateTime createdDate) {
    this.seq = seq;
    this.title = title;
    this.content = content;
    this.writeId = writeId;
    this.createdDate = createdDate;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", BoardVo.class.getSimpleName() + "[", "]")
        .add("seq=" + seq)
        .add("title='" + title + "'")
        .add("content='" + content + "'")
        .add("writeId='" + writeId + "'")
        .add("createdDate=" + createdDate)
        .toString();
  }

}
