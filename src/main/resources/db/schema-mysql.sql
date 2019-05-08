use `sys-basis-mybatis`;
drop table if exists user;
create table user(
    id int (11) not null AUTO_INCREMENT COMMENT '主键ID',
    name varchar (32) null default null COMMENT '用户名',
    password varchar(64) null  default null COMMENT '密码',
    age INT(3) NULL default NULL COMMENT '年龄',
	email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
	PRIMARY KEY (id)
);