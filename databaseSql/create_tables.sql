drop database if exists sys_user;
create database sys_user;

use sys_user;

DROP TABLE IF EXISTS `t_user`;
create table t_user (
    id int primary key auto_increment COMMENT 'ID',
    user_name varchar(20) not null unique COMMENT '帐号',
    nickname varchar(20) not null unique COMMENT '昵称',
    password varchar(80) not null COMMENT '密码',
    type int(1) not null default 1 comment '用户类型：0 admin 1=普通用户',
    reg_time datetime not null COMMENT '注册时间',
    login_last_time datetime not null COMMENT '最后登陆时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户表';

DROP TABLE IF EXISTS `t_role`;
CREATE TABLE t_role (
    id int primary key auto_increment COMMENT 'ID',
    name varchar(128) not null unique COMMENT '角色名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '角色表';

DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE t_user_role (
   id int primary key auto_increment COMMENT 'ID',
   user_id int not null COMMENT '用户id',
   role_id int not null COMMENT '角色id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户角色表';


DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE t_permission (
    id INT auto_increment COMMENT 'ID' PRIMARY KEY,
    `name` VARCHAR ( 128 ) NULL COMMENT '资源名称',
    parentId INT NOT NULL COMMENT '父节点',
    `path` VARCHAR ( 128 ) NOT NULL COMMENT '访问地址',
    title VARCHAR ( 128 ) NULL COMMENT 'title名称',
    icon VARCHAR ( 128 ) NULL COMMENT '图标',
    permission VARCHAR ( 512 ) NULL COMMENT '权限',
    `description` VARCHAR ( 128 ) NULL COMMENT '描述',
    show_status INT ( 1 ) NULL COMMENT '是否展示',
    type INT ( 1 ) NULL COMMENT '类型:菜单,权限',
    order_num INT ( 10 ) NULL COMMENT '排序',
    create_time TIMESTAMP COMMENT '创建时间',
    CONSTRAINT parentId UNIQUE ( parentId ),
    CONSTRAINT path UNIQUE ( `path` )
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '资源表';


DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
   `id` INT ( 11 ) NOT NULL AUTO_INCREMENT COMMENT 'ID',
   `role_id` INT ( 11 ) NOT NULL COMMENT '角色id',
   `permission_id` INT ( 11 ) NOT NULL COMMENT '权限id',
   PRIMARY KEY ( `id` ),
   KEY `role_id` ( `role_id` ),
   KEY `permission_id` ( `permission_id` )
) ENGINE = INNODB AUTO_INCREMENT = 2 DEFAULT CHARSET = utf8 COMMENT = '角色资源表';