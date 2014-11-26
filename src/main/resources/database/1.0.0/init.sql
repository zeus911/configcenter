/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.1.63-log : Database - configcenter
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`configcenter` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `configcenter`;

/*Table structure for table `access_setting` */

DROP TABLE IF EXISTS `access_setting`;

CREATE TABLE `access_setting` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `ref_id` int(11) NOT NULL DEFAULT '0' COMMENT '引用id，由type类型决定。',
  `type` tinyint(3) DEFAULT NULL COMMENT '引用类型',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Table structure for table `cc_user` */

DROP TABLE IF EXISTS `cc_user`;

CREATE TABLE `cc_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '操作台登录密码',
  `api_password` varchar(50) DEFAULT NULL COMMENT 'api访问密码',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态0正常',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_uni_idx` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Data for the table `cc_user` */

insert into `cc_user` (`id`, `name`, `password`, `api_password`, `create_time`, `update_time`, `status`) values('1','admin','202cb962ac59075b964b07152d234b70','202cb962ac59075b964b07152d234b70',NOW(),NOW(),'0');

/*Table structure for table `config_group` */

DROP TABLE IF EXISTS `config_group`;

CREATE TABLE `config_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '分组名称',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `default_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '默认分组标识',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `version_id` int(11) DEFAULT NULL COMMENT '版本id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_version_uni_idx` (`version_id`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Table structure for table `config_item` */

DROP TABLE IF EXISTS `config_item`;

CREATE TABLE `config_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '配置名称',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `val` varchar(255) DEFAULT NULL COMMENT '配置值',
  `shareable` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否共享',
  `ref` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否引用值',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `group_id` int(11) DEFAULT NULL COMMENT '分组id',
  `version_id` int(11) DEFAULT NULL COMMENT 'versionID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_version_uni_idx` (`name`,`version_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*Table structure for table `environment` */

DROP TABLE IF EXISTS `environment`;

CREATE TABLE `environment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主建',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '运行环境名称',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `project_id` int(11) NOT NULL DEFAULT '0' COMMENT '工程id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_project_uni_idx` (`name`,`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='运行环境。如开发，测试，线上等';

/*Table structure for table `item_type` */

DROP TABLE IF EXISTS `item_type`;

CREATE TABLE `item_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` varchar(50) NOT NULL DEFAULT '' COMMENT '类型',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `project` */

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '工程项目名称',
  `memo` varchar(255) DEFAULT NULL COMMENT '工程说明',
  `create_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `name_uni_idx` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='工程';

/*Table structure for table `version` */

DROP TABLE IF EXISTS `version`;

CREATE TABLE `version` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '版本名称',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `check_sum` varchar(36) DEFAULT NULL COMMENT '更新标识，由该标识识别是否有配置更新',
  `create_chck_sum` tinyint(1) DEFAULT NULL COMMENT '是否运行check检查。1表示需要检查',
  `update_time` datetime DEFAULT NULL COMMENT '该版本下配置项的最后更新时间',
  `check_sum_date` datetime DEFAULT NULL COMMENT '版本检查编号对应的update时间',
  `project_id` int(11) DEFAULT NULL COMMENT '工程id',
  `environment_id` int(11) DEFAULT NULL COMMENT '运行环境id',
  PRIMARY KEY (`id`),
  KEY `name_uni_idx` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='配置版本信息';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
