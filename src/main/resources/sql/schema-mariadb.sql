drop table if exists tb_board cascade;

create table tb_board
(
  seq          bigint        not null auto_increment,
  title        varchar(100)  not null,
  contents     varchar(4000) not null,
  write_id     varchar(50)   not null,
  created_id   varchar(30)   not null,
  created_date datetime(6)   not null default current_timestamp(6),
  updated_id   varchar(30)   not null,
  updated_date datetime(6)   not null default current_timestamp(6),
  primary key (seq)
);
