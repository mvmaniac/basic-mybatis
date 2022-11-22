package io.devfactory.global.common.typehandler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.Alias;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Alias("MybatisTypeHandler")
public class MybatisTypeHandler implements TypeHandler<String> {

  @Override
  public void setParameter(PreparedStatement ps, int i, String parameter,
      JdbcType jdbcType) throws SQLException {

    if (!hasText(parameter)) {
      ps.setString(i, parameter);
      return;
    }

    // 암호화 처리 한다고 가정
    ps.setString(i, parameter + "_암호화");
  }

  @Override
  public String getResult(ResultSet rs, String columnName) throws SQLException {
    log.debug("[dev] getResult - rs with columnName");
    return decode(rs.getString(columnName));
  }

  @Override
  public String getResult(ResultSet rs, int columnIndex) throws SQLException {
    log.debug("[dev] getResult - rs with columnIndex");
    return decode(rs.getString(columnIndex));
  }

  @Override
  public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
    log.debug("[dev] getResult - cs with columnIndex");
    return decode(cs.getString(columnIndex));
  }

  private String decode(String value) {
    if (!hasText(value)) {
      return value;
    }

    // 복호화 처리 한다고 가정
    return value.replace("_암호화", "_복호화");
  }

}
