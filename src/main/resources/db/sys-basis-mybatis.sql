# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.6.43)
# Database: sys-basis-mybatis
# Generation Time: 2019-05-06 08:18:41 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table image_code
# ------------------------------------------------------------

DROP TABLE IF EXISTS `image_code`;

CREATE TABLE `image_code` (
  `id` varchar(64) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `code` varchar(8) COLLATE utf8_unicode_ci DEFAULT NULL,
  `expire_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `image_code` WRITE;
/*!40000 ALTER TABLE `image_code` DISABLE KEYS */;

INSERT INTO `image_code` (`id`, `code`, `expire_time`)
VALUES
	('392ce391-e074-4850-a396-21bd4b22fc87','0870','2019-05-06 04:02:36'),
	('b1f6e92c-6fb4-11e9-abf1-1681be663d3e','6225','2019-05-06 04:14:23'),
	('ec1220e6-6fb3-11e9-a923-1681be663d3e','9720','2019-05-06 04:08:51'),
	('f17fba24-6fb4-11e9-a923-1681be663d36','5256','2019-05-06 04:19:35'),
	('f17fba24-6fb4-11e9-a923-1681be663d3e','2974','2019-05-06 04:16:22'),
	('f17fba24-6fb4-11e9-a923-1681be663d3q','371524','2019-05-06 06:18:25'),
	('f17fba24-6fb4-11e9-a923-1681be663d3t','8530','2019-05-06 04:19:27'),
	('f17fba24-6fb4-11e9-a923-1681be663d3w','6299','2019-05-06 04:17:03'),
	('f17fba24-6fb4-11e9-a923-1681be663d556','4940','2019-05-06 04:19:42'),
	('f17fba24-6fb4-11e9-a923-1681bebddd31','2YNSc','2019-05-06 06:58:37'),
	('f17fba24-6fb4-11e9-a923-1681bebddd3q','aMBjY','2019-05-06 06:58:32'),
	('f17fba24-6fb4-11e9-a923-1681bebdkjcd3q','DMnFC','2019-05-06 06:58:27'),
	('f17fba24-6fsd-11e9-a923-1681be663d556','0993','2019-05-06 04:26:53'),
	('f17fba24-6fsd-11e9-a923-1681be663d55w','614713','2019-05-06 04:31:13'),
	('f17fba2z-6fb4-11e9-a923-1681bebddd31','BQnnJ','2019-05-06 06:59:08'),
	('f17fba2z!6fb4-11e9-a923-1681bebdd431','dumnu','2019-05-06 07:03:55'),
	('f17fba2z!6fb4-11e9-a923-1681bebddd31','KMaDe','2019-05-06 06:59:44');

/*!40000 ALTER TABLE `image_code` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table menu
# ------------------------------------------------------------

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(64) DEFAULT NULL,
  `path` varchar(64) DEFAULT NULL,
  `component` varchar(64) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `icon_cls` varchar(64) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `parentId` (`parent_id`),
  CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table menu_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `menu_role`;

CREATE TABLE `menu_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mid` int(11) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `mid` (`mid`),
  KEY `rid` (`rid`),
  CONSTRAINT `menu_role_ibfk_1` FOREIGN KEY (`mid`) REFERENCES `menu` (`id`),
  CONSTRAINT `menu_role_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `cname` varchar(64) DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;

INSERT INTO `role` (`id`, `name`, `cname`)
VALUES
	(1,'ROLE_manager','部门经理'),
	(2,'ROLE_personnel','人事专员'),
	(3,'ROLE_recruiter','招聘主管'),
	(4,'ROLE_train','培训主管'),
	(5,'ROLE_performance','薪酬绩效主管'),
	(6,'ROLE_admin','系统管理员'),
	(13,'ROLE_test2','测试角色2'),
	(14,'ROLE_test1','测试角色1');

/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table syslog
# ------------------------------------------------------------

DROP TABLE IF EXISTS `syslog`;

CREATE TABLE `syslog` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL,
  `operation` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
  `time` int(11) DEFAULT NULL,
  `method` varchar(128) COLLATE utf8_unicode_ci DEFAULT NULL,
  `params` varchar(256) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ip` varchar(32) COLLATE utf8_unicode_ci DEFAULT '',
  `message` varchar(64) COLLATE utf8_unicode_ci DEFAULT 'SUCCESS',
  `code` varchar(2) COLLATE utf8_unicode_ci DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `syslog` WRITE;
/*!40000 ALTER TABLE `syslog` DISABLE KEYS */;

INSERT INTO `syslog` (`id`, `username`, `operation`, `time`, `method`, `params`, `ip`, `message`, `code`, `create_time`)
VALUES
	(1,NULL,'注册接口',6414,'top.luoren.basis.controller.LoginRegController.userReg',' username: test5 password: 123456','127.0.0.1',NULL,NULL,'2019-05-05 09:52:47'),
	(2,NULL,'注册接口',99147,'top.luoren.basis.controller.LoginRegController.userReg',' username: test5 password: 123456','127.0.0.1',NULL,NULL,'2019-05-05 10:02:22'),
	(3,NULL,'注册接口',359525,'top.luoren.basis.controller.LoginRegController.userReg',' username: test5 password: 123456','127.0.0.1',NULL,NULL,'2019-05-06 01:54:57'),
	(4,NULL,'注册接口',19881,'top.luoren.basis.controller.LoginRegController.userReg',' username: test5 password: 123456','127.0.0.1',NULL,NULL,'2019-05-06 01:56:04'),
	(5,NULL,'注册接口',73399,'top.luoren.basis.controller.LoginRegController.userReg',' username: test5 password: 123456','127.0.0.1','SUCCESS','0','2019-05-06 02:24:16'),
	(6,NULL,'注册接口',3769,'top.luoren.basis.controller.LoginRegController.userReg',' username: test5 password: 123456','127.0.0.1','用户名已存在,注册失败','-1','2019-05-06 02:24:48');

/*!40000 ALTER TABLE `syslog` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(32) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户名',
  `username` varchar(32) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(64) COLLATE utf8_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `enabled` tinyint(1) DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `name`, `username`, `password`, `age`, `email`, `enabled`)
VALUES
	(1,'test1','test1','123456',21,'test@top.com',1),
	(2,'test2','test2','123456',22,'test2@top.com',1),
	(6,NULL,'test3','$2a$10$ZPn5vIVgXdy6smMRP7/2r.4WrABAR/8gEYOGlNVpPiEmJ3ipJlOGy',NULL,NULL,1),
	(8,NULL,'test4','$2a$10$xQbATJWvtDTM.kK93lwhAO2kF/uMzEo74bSblTg6B2qsCNYE3SI4q',NULL,NULL,NULL),
	(9,NULL,'test5','$2a$10$zJp6qS6GcTUia7eRpLdtxe64QsNYyUhSncP6DctGvrr0B7rgsDMQi',NULL,NULL,NULL);

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `hr_role_ibfk_2` (`rid`),
  KEY `hr_role_ibfk_1` (`uid`),
  CONSTRAINT `hr_role_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `hr_role_ibfk_2` FOREIGN KEY (`rid`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;

INSERT INTO `user_role` (`id`, `uid`, `rid`)
VALUES
	(1,1,13),
	(2,1,14);

/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
