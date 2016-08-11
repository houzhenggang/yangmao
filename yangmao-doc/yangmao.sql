
drop database yangmao;
create database if not exists yangmao default charset utf8 collate utf8_general_ci;

use yangmao;

CREATE TABLE `yangmao_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `name` varchar(50) COMMENT '用户名',
  `email` varchar(200) COMMENT '邮箱',
  `password` varchar(2048) COMMENT '用户密码，md5加密以后的值',
  `status` tinyint comment '状态:0=正常，1=冻结，2=删除',
  `is_admin` tinyint comment '是否管理员:0=不是，1=是',
  `last_frozen_time` datetime comment '最后一次冻结时间',
  `create_time` datetime comment '创建时间',
  `last_update_time` datetime comment '最后一次更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `name` (`name`)
) AUTO_INCREMENT = 20000 ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='用户表';

CREATE TABLE `yangmao_email` (
  `email_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'email ID',
  `email` varchar(200) NOT NULL COMMENT 'email 地址',
  `last_email_time` datetime comment '最后发送到该邮箱时间',
  `last_reject_time` datetime comment '最后拒收时间',
  PRIMARY KEY (`email_id`),
  KEY `email` (`email`),
  KEY `last_email_time` (`last_email_time`)) 
  ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='邮箱表'
	PARTITION BY RANGE (email_id) (
    PARTITION p0 VALUES LESS THAN (1000000),
    PARTITION p1 VALUES LESS THAN (2000000),
    PARTITION p2 VALUES LESS THAN (3000000),
    PARTITION p3 VALUES LESS THAN (4000000),
    PARTITION p4 VALUES LESS THAN (5000000),
    PARTITION p5 VALUES LESS THAN (6000000),
    PARTITION p6 VALUES LESS THAN (7000000),
    PARTITION p7 VALUES LESS THAN (8000000),
    PARTITION p8 VALUES LESS THAN (9000000),
    PARTITION p9 VALUES LESS THAN (10000000),
    PARTITION p10 VALUES LESS THAN (11000000),
    PARTITION p11 VALUES LESS THAN (12000000),
    PARTITION p12 VALUES LESS THAN (13000000),
    PARTITION p13 VALUES LESS THAN (14000000),
    PARTITION p14 VALUES LESS THAN (15000000),
    PARTITION p15 VALUES LESS THAN (16000000),
    PARTITION p16 VALUES LESS THAN (17000000),
    PARTITION p17 VALUES LESS THAN (18000000),
    PARTITION p18 VALUES LESS THAN (19000000),
    PARTITION p19 VALUES LESS THAN (20000000),
    PARTITION p20 VALUES LESS THAN MAXVALUE
);



