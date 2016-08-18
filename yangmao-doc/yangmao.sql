
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

#淘宝客选品组
CREATE TABLE `yangmao_favorites` (
  `favorites_id` bigint NOT NULL  COMMENT '选品组ID',
  `title` varchar(100) COMMENT '选品组名称',
  `type` int(11) COMMENT '1：普通类型，2高佣金类型',  
  `create_time` datetime comment '创建时间',
  `last_update_time` datetime comment '最后一次更新时间',
  PRIMARY KEY (`favorites_id`),
  KEY `title` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='选品组表';

#淘宝客选品
CREATE TABLE `yangmao_favorites_item` (
  `num_iid` bigint NOT NULL  COMMENT '选品ID',
  `favorites_id` bigint  COMMENT '选品组ID',
  `title` varchar(100) COMMENT '选品名称',
  `item_url` varchar(300) COMMENT '商品地址',
  `pict_url` varchar(300) COMMENT '商品主图地址',
  `item_images` varchar(2048) COMMENT '商品图列表,逗号分割',
  `nick` varchar(50) COMMENT '卖家昵称',
  `seller_id` bigint  COMMENT '卖家ID',
  `provcity` varchar(50) COMMENT '宝贝所在地',
  `original_price` decimal(7,2) COMMENT '原价，单位元',
  `final_price` decimal(7,2) COMMENT '折后价，单位元',
  `final_price_wap` decimal(7,2) COMMENT '无线折扣价，即宝贝在无线上的实际售卖价格',
  `click_url` varchar(300) COMMENT '淘客地址',
  `tk_rate` decimal(7,2) COMMENT '淘客百分率,39.0表示39%',
  `volume` int(11) COMMENT '30天销量',   
  `create_time` datetime comment '创建时间',
  `last_update_time` datetime comment '最后一次更新时间',
  PRIMARY KEY (`num_iid`),
  KEY `favorites_id` (`favorites_id`),
  KEY `title` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='选品表';

