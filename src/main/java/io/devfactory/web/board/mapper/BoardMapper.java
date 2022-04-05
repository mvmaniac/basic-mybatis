package io.devfactory.web.board.mapper;

import io.devfactory.global.common.annotation.MariadbMapper;
import io.devfactory.web.board.vo.BoardVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@MariadbMapper
public interface BoardMapper {

  List<BoardVo> selectBoards();

  Optional<BoardVo> selectBoard(Long seq);

  void insertBoard(BoardVo boardVo);

  void updateBoard(@Param("seq") Long seq, @Param("board") BoardVo boardVo);

  void deleteBoard(Long seq);

}
