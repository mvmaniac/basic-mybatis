drop table if exists tb_member cascade;

create table tb_member
(
    seq           bigint      not null auto_increment,
    email         varchar(50) not null,
    name          varchar(30) not null,
    passwd        varchar(80) not null,
    login_count   int         not null default 0,
    last_login_at datetime             default null,
    created_date  datetime    not null default current_timestamp(),
    primary key (seq),
    constraint unq_member_email unique (email)
);
