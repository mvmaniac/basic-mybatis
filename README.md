# Basic Mybatis

### 1. 블로그 실습 예제 기반

### 2. 차이점

- jasypt 예제 추가

### 3. TODO

### 4. 참고

- 아래 VM 옵션을 주어서 실행
  `-Djasypt.encryptor.password=dbgmltlr`
- TransactionInterceptor
  - Exception의 경우에도 롤백이 되려면 아래와 같이 사용하면 되나 TransactionInterceptor을 사용하여 할 수 있음
    `@Transactional(rollbackFor = Exception.class)`
  - 테스트 시 app.aop.transaction.enabled 갑을 true로 설정
- JTA Transaction
  - 일단 예제에서는 다른 DB간 트랜잭션을 하나로 처리하는 용도로 사용
  - 테스트 방법
    1. spring.jta.enabled 값을 true로 설정 해야 함 (@ConditionalOnProperty로 분리 처리)
    2. @MysqlTransaction과 @MariadbTransaction 파일 상단에 기본 @Transactional 어노테이션으로 변경
  - 문제점
    1. DataSourceInitializer 적용 안됨
    2. 트랜잭션은 적용은 되나 아래 예외 발생
       `XAER_RMERR: Fatal error occurred in the transaction branch - check your data for consistency`
