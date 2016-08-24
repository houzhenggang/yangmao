
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
  `yangmao_favorites_id` bigint NOT NULL AUTO_INCREMENT COMMENT '选品组ID',
  `favorites_id` bigint NOT NULL  COMMENT 'taobao选品组ID',
  `title` varchar(100) COMMENT '选品组名称',
  `type` int(11) COMMENT '1：普通类型，2高佣金类型',  
  `create_time` datetime comment '创建时间',
  `last_update_time` datetime comment '最后一次更新时间',
  PRIMARY KEY (`yangmao_favorites_id`),
  UNIQUE KEY (`favorites_id`),
  KEY `title` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='选品组表';

#淘宝客选品
CREATE TABLE `yangmao_favorites_item` (
  `item_id` bigint NOT NULL AUTO_INCREMENT COMMENT '选品ID',
  `num_iid` bigint NOT NULL  COMMENT 'taobao选品ID',
  `yangmao_favorites_id` bigint  COMMENT '选品组ID',
  `title` varchar(100) COMMENT '选品名称',
  `item_url` varchar(300) COMMENT '商品地址',
  `pict_url` varchar(300) COMMENT '商品主图地址',
  `item_images` varchar(2048) COMMENT '商品图列表,逗号分割',
  `nick` varchar(50) COMMENT '卖家昵称',
  `seller_id` bigint  COMMENT '卖家ID',
  `shop_title` varchar(150) COMMENT '店铺名称',
  `provcity` varchar(50) COMMENT '宝贝所在地',
  `original_price` decimal(7,2) COMMENT '原价，单位元',
  `final_price` decimal(7,2) COMMENT '折后价，单位元',
  `final_price_wap` decimal(7,2) COMMENT '无线折扣价，即宝贝在无线上的实际售卖价格',
  `click_url` varchar(2000) COMMENT '淘客地址',
  `tk_rate` decimal(7,2) COMMENT '淘客百分率,39.0表示39.0%',
  `volume` int(11) COMMENT '30天销量',   
  `create_time` datetime comment '创建时间',
  `last_update_time` datetime comment '最后一次更新时间',
  PRIMARY KEY (`item_id`),
  UNIQUE KEY (`num_iid`,`yangmao_favorites_id`),
  KEY `yangmao_favorites_id` (`yangmao_favorites_id`),
  KEY `title` (`title`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='选品表';

#邮件模板表
CREATE TABLE `yangmao_mail_template` (
  `template_id` bigint NOT NULL  COMMENT '邮件模板ID',
  `name` varchar(50) COMMENT '模板名称，变量{}',
  `title` varchar(100) COMMENT '模板标题,变量{}',
  `content` text COMMENT '模板内容',
  `section` varchar(50) COMMENT '区段名称',
  `section_amount` int COMMENT '区段可容纳得商品数量',
  `status` tinyint COMMENT '0：可使用，1:已删除',
  `create_time` datetime comment '创建时间',
  `last_update_time` datetime comment '最后一次更新时间',
  PRIMARY KEY (`template_id`),
  KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件模板表';

#邮件实例表
CREATE TABLE `yangmao_mail_instance` (
  `mail_instance_id` bigint NOT NULL  COMMENT '邮件实例ID',
  `template_id` bigint COMMENT '邮件模板ID',
  `title` varchar(50) COMMENT '模板名称，变量{}',
  `content` text COMMENT '邮件内容，变量{}',
  `status` tinyint COMMENT '0：可使用，1:已下架',
  `expire_time` datetime comment '到期时间',
  `create_time` datetime comment '创建时间',
  `last_update_time` datetime comment '最后一次更新时间',
  PRIMARY KEY (`mail_instance_id`),
   KEY (`template_id`),
   KEY (`expire_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件实例表';

#邮件实例选品表
CREATE TABLE `yangmao_mail_instance_item` (
  `instance_item_id` bigint NOT NULL  COMMENT '邮件实例中的商品ID',
  `mail_instance_id` bigint NOT NULL  COMMENT '邮件实例ID',
  `item_id` bigint COMMENT '选品ID',
  `section` varchar(50) COMMENT '隶属的区段名称',
  PRIMARY KEY (`instance_item_id`),
  KEY (`mail_instance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件实例的选品表';



