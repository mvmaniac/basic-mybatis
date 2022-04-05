package io.devfactory.web.board.vo;

import io.devfactory.global.common.annotation.CreatedBy;
import io.devfactory.global.common.annotation.CreatedDate;
import io.devfactory.global.common.annotation.LastModifiedBy;
import io.devfactory.global.common.annotation.LastModifiedDate;
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
  private String contents;
  private String writeId;

  @CreatedBy(updatable = false)
  private String createdId;

  @CreatedDate(updatable = false)
  private LocalDateTime createdDate;

  @LastModifiedBy
  private String updatedId;

  @LastModifiedDate
  private LocalDateTime updatedDate;

  @Builder
  private BoardVo(long seq, String title, String contents, String writeId, String createdId,
      LocalDateTime createdDate, String updatedId, LocalDateTime updatedDate) {
    this.seq = seq;
    this.title = title;
    this.contents = contents;
    this.writeId = writeId;
    this.createdId = createdId;
    this.createdDate = createdDate;
    this.updatedId = updatedId;
    this.updatedDate = updatedDate;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", BoardVo.class.getSimpleName() + "[", "]")
        .add("seq=" + seq)
        .add("title='" + title + "'")
        .add("contents='" + contents + "'")
        .add("writeId='" + writeId + "'")
        .add("createdId='" + createdId + "'")
        .add("updatedId='" + updatedId + "'")
        .add("createdDate=" + createdDate)
        .add("updatedDate=" + updatedDate)
        .toString();
  }

}
