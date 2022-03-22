package io.devfactory.web.board.mapper;

import io.devfactory.global.annotation.MariadbMapper;
import io.devfactory.web.board.vo.BoardVo;

import java.util.List;
import java.util.Optional;

@MariadbMapper
public interface BoardMapper {

  List<BoardVo> selectBoards();

  Optional<BoardVo> selectBoard(long id);

}
