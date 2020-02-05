DROP TABLE IF EXISTS tb_member CASCADE;

CREATE TABLE tb_member (
  seq           bigint NOT NULL AUTO_INCREMENT,
  email         varchar(50) NOT NULL,
  name          varchar(30) NOT NULL,
  passwd        varchar(80) NOT NULL,
  login_count   int NOT NULL DEFAULT 0,
  last_login_at datetime DEFAULT NULL,
  create_at     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP(),
  PRIMARY KEY (seq),
  CONSTRAINT unq_member_email UNIQUE (email)
);
