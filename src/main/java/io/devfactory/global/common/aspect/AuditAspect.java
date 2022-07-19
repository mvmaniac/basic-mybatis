package io.devfactory.global.common.aspect;

import io.devfactory.global.common.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

// MybatisAuditInterceptor와 다르게 insert할건지, update할건지 알수 없음
// 굳이 알려고 한다면 메소드명이나 메소드에 어노테이션으로 명시를 해주어야 할 듯?
// 그리고 일단 insert 쿼리에서는 등록, 수정 컬럼 처리하고 update 쿼리에서는 수정 컬럼만 처리하면 되기 때문에
// 굳이 insertable, updateable를 판단할 필요는 없을 듯?
@Slf4j
@ConditionalOnProperty(value = "app.mybatis.aop.enabled", havingValue = "true")
@Aspect
@Component
public class AuditAspect {

    @Around("execution(* io.devfactory.web..service.*Service.*(..))")
    public Object auditIdAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            Class<?> argClazz = arg.getClass();
            List<?> argList = (arg instanceof List<?>) ? (List<?>) arg : emptyList();

            if (!argList.isEmpty()) {
                argClazz = argList.get(0).getClass();
            }

            if (!isAuditable(argClazz)) {
                continue;
            }

            if (argList.isEmpty()) {
                auditForObject(arg, argClazz);
            } else {
                auditForList(arg, argClazz);
            }
        }

        return joinPoint.proceed();
    }

    private boolean isAuditable(Class<?> clazz) {
        return clazz.isAnnotationPresent(Auditable.class);
    }

    private boolean hasAuditing(Field field) {
        return field.isAnnotationPresent(CreatedBy.class) || field.isAnnotationPresent(CreatedDate.class)
            || field.isAnnotationPresent(LastModifiedBy.class) || field.isAnnotationPresent(LastModifiedDate.class);
    }

  private void auditForList(Object object, Class<?> clazz) {
        List<?> objects = (List<?>) object;
        List<Field> foundFields = findAuditableFields(clazz);
        for (Object target : objects) {
            determineValuesByUserId(foundFields, target);
        }
    }

    private void auditForObject(Object object, Class<?> clazz) {
        List<Field> foundFields = findAuditableFields(clazz);
        determineValuesByUserId(foundFields, object);
    }

    private List<Field> findAuditableFields(Class<?> clazz) {
        List<Field> results = new ArrayList<>();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (hasAuditing(field)) {
                ReflectionUtils.makeAccessible(field);
                results.add(field);
            }
        }

        return results;
    }

    private void determineValuesByUserId(List<Field> auditableFields, Object target) {
        for (Field field : auditableFields) {
            ReflectionUtils.setField(field, target, obtainValue(field));
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

  private boolean hasAnnotationBy(Field field) {
    return field.isAnnotationPresent(CreatedBy.class) || field.isAnnotationPresent(LastModifiedBy.class);
  }

  private boolean hasAnnotationDate(Field field) {
    return field.isAnnotationPresent(CreatedDate.class) || field.isAnnotationPresent(LastModifiedDate.class);
  }

}
