package io.devfactory.global.common.interceptor;

import io.devfactory.global.common.annotation.CreatedBy;
import io.devfactory.global.common.annotation.CreatedDate;
import io.devfactory.global.common.annotation.LastModifiedBy;
import io.devfactory.global.common.annotation.LastModifiedDate;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.ibatis.mapping.SqlCommandType.INSERT;
import static org.apache.ibatis.mapping.SqlCommandType.UPDATE;

// AOP로 서비스 단위로 처리하는게 나을지도?
// 같은 Mapper를 사용하면서도 특정 상황에서는 사용 안하는 방법이 있나?
@Slf4j
@Intercepts({
    @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}), // select
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}) // insert, update, delete
})
public class MybatisAuditInterceptor implements Interceptor {

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    final var method = invocation.getMethod().getName();
    final var args = invocation.getArgs();

    final var mappedStatement = (MappedStatement) args[0];
    final var sqlType = mappedStatement.getSqlCommandType();
    final var mapperId = mappedStatement.getId();

    Object param = args[1];
    final var sql = mappedStatement.getBoundSql(param).getSql();

    log.debug("[dev] MybatisInterceptor.intercept - method: {}", method);
    log.debug("[dev] MybatisInterceptor.intercept - sqlType: {}", sqlType);
    log.debug("[dev] MybatisInterceptor.intercept - mapperId: {}", mapperId);
    log.debug("[dev] MybatisInterceptor.intercept - param: {}", param);
    log.debug("[dev] MybatisInterceptor.intercept - sql: {}", sql);

    if (isNull(param) || (sqlType != INSERT && sqlType != UPDATE)) {
      return invocation.proceed();
    }

    final Class<?> clazz = param.getClass();
    if (BeanUtils.isSimpleValueType(clazz)) {
      return invocation.proceed();
    }

    auditing(sqlType, param);
    return invocation.proceed();
  }

  // TODO: Map 지원?
  @SuppressWarnings({"unchecked", "squid:S3740"})
  private void auditing(SqlCommandType sqlType, Object param) {
    if (param instanceof MapperMethod.ParamMap paramMap) {
      auditingWithParamMap(sqlType, paramMap);

    } else if (param instanceof final List<?> list) {
      auditingWithList(sqlType, list);

    } else {
      auditingWithObject(sqlType, param);
    }
  }

  private void auditingWithParamMap(SqlCommandType sqlType,
      MapperMethod.ParamMap<Object> paramMap) {
    for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
      if (!entry.getKey().startsWith("param") && !BeanUtils.isSimpleValueType(entry.getValue().getClass())) {
        auditing(sqlType, entry.getValue());
      }
    }
  }

  private void auditingWithList(SqlCommandType sqlType, List<?> list) {
    List<Field> foundFields = findAuditableField(sqlType, list.get(0).getClass());
    for (Object target : list) {
      determineFieldsValue(foundFields, target);
    }
  }

  private void auditingWithObject(SqlCommandType sqlType, Object param) {
    List<Field> foundFields = findAuditableField(sqlType, param.getClass());
    determineFieldsValue(foundFields, param);
  }

  private List<Field> findAuditableField(SqlCommandType sqlType, Class<?> clazz) {
    List<Field> results = new ArrayList<>();

    final var fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      if (hasAuditing(sqlType, field)) {
        ReflectionUtils.makeAccessible(field);
        results.add(field);
      }
    }

    return results;
  }

  private void determineFieldsValue(List<Field> auditableFields, Object target) {
    for (Field field : auditableFields) {
      if (isNull(ReflectionUtils.getField(field, target))) { // 값이 없는 경우에만 설정
        ReflectionUtils.setField(field, target, obtainValue(field));
      }
    }
  }

  private Object obtainValue(Field field) {
    if (hasAnnotationDate(field)) {
      return LocalDateTime.now();
    }
    
    // TODO: 시큐리티 적용?, 근데 여기서 꺼내서 사용하면 매번 호출하게됨...
    if (hasAnnotationBy(field)) {
      return "테스트ID";
    }

    return null;
  }

  private boolean hasAuditing(SqlCommandType sqlType, Field field) {
    if (sqlType == INSERT) {
      return isInsertable(field, CreatedBy.class) || isInsertable(field, CreatedDate.class)
          || isInsertable(field, LastModifiedBy.class) || isInsertable(field, LastModifiedDate.class);
    }

    return isUpdatable(field, CreatedBy.class) || isUpdatable(field, CreatedDate.class)
        || isUpdatable(field, LastModifiedBy.class) || isUpdatable(field, LastModifiedDate.class);
  }

  private boolean isInsertable(Field field, Class<? extends Annotation> annotationClass) {
    final var insertable = AnnotationUtils.getValue(field.getAnnotation(annotationClass), "insertable");
    return nonNull(insertable) && ((boolean) insertable);
  }

  private boolean isUpdatable(Field field, Class<? extends Annotation> annotationClass) {
    final var updatable = AnnotationUtils.getValue(field.getAnnotation(annotationClass), "updatable");
    return nonNull(updatable) && ((boolean) updatable);
  }

  private boolean hasAnnotationBy(Field field) {
    return field.isAnnotationPresent(CreatedBy.class) || field.isAnnotationPresent(LastModifiedBy.class);
  }

  private boolean hasAnnotationDate(Field field) {
    return field.isAnnotationPresent(CreatedDate.class) || field.isAnnotationPresent(LastModifiedDate.class);
  }

}
