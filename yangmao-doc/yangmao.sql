
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

ALTER TABLE yangmao_email ADD email_instance_id bigint NOT NULL DEFAULT 0 COMMENT '最后一次发送的邮件实例ID';


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
  `template_id` bigint NOT NULL AUTO_INCREMENT COMMENT '邮件模板ID',
  `name` varchar(50) COMMENT '模板名称，变量{}',
  `title` varchar(100) COMMENT '模板标题,变量{}',
  `content` text COMMENT '模板内容',
  `status` tinyint COMMENT '0：可使用，1:已删除',
  `create_time` datetime comment '创建时间',
  `last_update_time` datetime comment '最后一次更新时间',
  PRIMARY KEY (`template_id`),
  KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件模板表';

CREATE TABLE `yangmao_template_section` (
  `section_id` bigint NOT NULL AUTO_INCREMENT COMMENT '邮件模板分区ID',
  `template_id` bigint NOT NULL COMMENT '邮件模板ID',
  `favorites_id` bigint COMMENT '对应选品组ID',
  `section` varchar(50) COMMENT '区段名称,就是选品库名称',
  `section_amount` int COMMENT '区段可容纳得商品数量',
  PRIMARY KEY (`section_id`),
  KEY (`template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件模板分区表';

#邮件实例表
CREATE TABLE `yangmao_mail_instance` (
  `mail_instance_id` bigint NOT NULL AUTO_INCREMENT COMMENT '邮件实例ID',
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
insert into yangmao_mail_instance(template_id,title,content,status,expire_time,create_time, last_update_time)
values(1,'测试','你好',0,'2016-08-28 00:00:00','2016-08-28 00:00:00','2016-08-28 00:00:00'); 

#邮件实例选品表
CREATE TABLE `yangmao_mail_instance_item` (
  `instance_item_id` bigint NOT NULL AUTO_INCREMENT COMMENT '邮件实例中的商品ID',
  `mail_instance_id` bigint NOT NULL  COMMENT '邮件实例ID',
  `item_id` bigint COMMENT '选品ID',
  `section_id` bigint COMMENT '邮件模板分区ID',
  PRIMARY KEY (`instance_item_id`),
  KEY (`mail_instance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件实例的选品表';

#链接点击记录
CREATE TABLE `yangmao_click` (
  `click_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'email点击 ID',
  `email` varchar(200) NOT NULL COMMENT 'email 地址',
  `mail_instance_id` bigint COMMENT '邮件实例ID',
  `item_id` bigint COMMENT '选品ID',
  `create_time` datetime comment '创建时间',
  PRIMARY KEY (`click_id`),
  KEY `item_id` (`item_id`),
  KEY `email` (`email`),
  KEY `mail_instance_id` (`mail_instance_id`)) 
  ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='商品点击表';
  
 #获取email记录表
CREATE TABLE `yangmao_email_getting_history` (
  `history_id` bigint NOT NULL AUTO_INCREMENT COMMENT '历史 ID',
  `ip_address` varchar(200) COMMENT 'ip地址',
  `sender_id` bigint COMMENT '发送者 ID',
  `amount` int COMMENT '本次返回email地址数量',
  `mail_instance_id` bigint COMMENT '本次返回邮件实例ID',
  `create_time` datetime comment '创建时间',
  PRIMARY KEY (`history_id`),
  KEY `create_time` (`create_time`),
  KEY `ip_address` (`ip_address`,`create_time`),
  KEY `mail_instance_id` (`mail_instance_id`)) 
  ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='邮件获取地址表';

#获取email记录表
CREATE TABLE `yangmao_email_sender` (
  `sender_id` bigint NOT NULL AUTO_INCREMENT COMMENT '发送者 ID',
  `name` varchar(200) COMMENT '发送者姓名',
  `email` varchar(200) COMMENT '邮箱地址',
  `password` varchar(200) COMMENT '邮箱地址',
  `host` varchar(100) COMMENT '服务器地址',
  `status` tinyint COMMENT '0：可使用，1:已下架',
  PRIMARY KEY (`sender_id`),
  KEY `email` (`email`))
  ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='发送者表';
  
  ALTER TABLE yangmao_email_sender ADD effecttive_date datetime NOT NULL default '2016-09-01 00:00:00' comment '生效时间';

  
  insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao1@92yangmao.com','yangmao_1',0,'smtp.92yangmao.com');

  insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9201@163.com','yangmao_9201',0,'smtp.163.com');
  insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9202@163.com','yangmao_9202',0,'smtp.163.com');
  insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9203@163.com','yangmao_9203',0,'smtp.163.com');
  
  insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9201@sina.com','yangmao_9201',0,'smtp.sina.com.cn');
  insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9202@sina.com','yangmao_9202',0,'smtp.sina.com.cn');
  insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9203@sina.com','yangmao_9203',0,'smtp.sina.com.cn');
  insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9204@sina.com','yangmao_9204',0,'smtp.sina.com.cn');
 insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9205@sina.com','yangmao_9205',0,'smtp.sina.com.cn');   

 insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9201@gmail.com','yangmao_9201',0,'smtp.gmail.com');   
 insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9202@gmail.com','yangmao_9202',0,'smtp.gmail.com'); 
 insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9203@gmail.com','yangmao_9203',0,'smtp.gmail.com');
 insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9204@gmail.com','yangmao_9204',0,'smtp.gmail.com');
 insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9205@gmail.com','yangmao_9205',0,'smtp.gmail.com');
 insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9206@gmail.com','yangmao_9206',0,'smtp.gmail.com');
 insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9207@gmail.com','yangmao_9207',0,'smtp.gmail.com');
 insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9208@gmail.com','yangmao_9208',0,'smtp.gmail.com');
 insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9210@gmail.com','yangmao_9210',0,'smtp.gmail.com');
insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9211@gmail.com','yangmao_9211',0,'smtp.gmail.com');
insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9212@gmail.com','yangmao_9212',0,'smtp.gmail.com');
insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9213@gmail.com','yangmao_9213',0,'smtp.gmail.com');
insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9214@gmail.com','yangmao_9214',0,'smtp.gmail.com');

 insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9201@21cn.com','maoyang_9201',0,'smtp.21cn.com');
 insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9202@21cn.com','maoyang_9202',0,'smtp.21cn.com');
 insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9203@21cn.com','maoyang_9203',0,'smtp.21cn.com');
 insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9204@21cn.com','maoyang_9204',0,'smtp.21cn.com');
 insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9205@21cn.com','maoyang_9205',0,'smtp.21cn.com');
 insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9206@21cn.com','maoyang_9206',0,'smtp.21cn.com');
 insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9207@21cn.com','maoyang_9207',0,'smtp.21cn.com');
 insert into yangmao_email_sender(name,email,password,status,host) values('羊毛情报站','yangmao9208@21cn.com','maoyang_9208',0,'smtp.21cn.com');
 
 
 CREATE TABLE `yangmao_replace_field` (
  `replace_field_id` bigint NOT NULL AUTO_INCREMENT COMMENT '替换字段 ID',
  `template_replace_name` varchar(200) COMMENT '模板中对应替换字段',
  `database_field` varchar(200) COMMENT '数据库orm对应字段 驼峰式命名',
  `level` tinyint COMMENT '层级 产品目录为1级 商品为2级',
  `create_time` datetime comment '创建时间',
  `last_update_time` datetime comment '最后一次更新时间',
  PRIMARY KEY (`replace_field_id`))
  ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT='模板替换字段表';

insert into yangmao.yangmao_replace_field(template_replace_name,database_field,level,create_time,last_update_time)
values("{sectionName}","section",1,now(),now());
insert into yangmao.yangmao_replace_field(template_replace_name,database_field,level,create_time,last_update_time)
values("{commodityTitle}","title",2,now(),now());
insert into yangmao.yangmao_replace_field(template_replace_name,database_field,level,create_time,last_update_time)
values("{commodityPic}","pictUrl",2,now(),now());
insert into yangmao.yangmao_replace_field(template_replace_name,database_field,level,create_time,last_update_time)
values("{commodityOriginalPrice}","originalPrice",2,now(),now());
insert into yangmao.yangmao_replace_field(template_replace_name,database_field,level,create_time,last_update_time)
values("{commodityfinalPrice}","finalPrice",2,now(),now());
insert into yangmao.yangmao_replace_field(template_replace_name,database_field,level,create_time,last_update_time)
values("{commodityClickUrl}","clickUrl",2,now(),now());
insert into yangmao.yangmao_replace_field(template_replace_name,database_field,level,create_time,last_update_time)
values("{commodityVolume}","Volume",2,now(),now());
insert into yangmao.yangmao_replace_field(template_replace_name,database_field,level,create_time,last_update_time)
values("{commodityImageClickUrl}","imageClickUrl",2,now(),now());