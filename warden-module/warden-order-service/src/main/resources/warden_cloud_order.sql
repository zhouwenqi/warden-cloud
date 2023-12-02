/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.11
Source Server Version : 50743
Source Host           : 192.168.1.11:3306
Source Database       : warden_cloud_order

Target Server Type    : MYSQL
Target Server Version : 50743
File Encoding         : 65001

Date: 2023-12-02 18:15:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for wd_order
-- ----------------------------
DROP TABLE IF EXISTS `wd_order`;
CREATE TABLE `wd_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_sn` varchar(50) NOT NULL COMMENT '订单编号',
  `order_type` tinyint(4) DEFAULT NULL COMMENT '订单类型',
  `app_terminal_type` tinyint(4) DEFAULT NULL COMMENT '应用终端',
  `terminal_type` tinyint(4) DEFAULT NULL COMMENT '平台类型',
  `amount` decimal(10,2) DEFAULT '0.00' COMMENT '订单总额',
  `order_status` tinyint(4) DEFAULT '1' COMMENT '订单状态',
  `payment_status` tinyint(4) DEFAULT '0' COMMENT '支付状态',
  `memo` varchar(150) DEFAULT NULL COMMENT '订单备注',
  `user_id` bigint(20) DEFAULT NULL COMMENT '创建用户id',
  `uid` varchar(50) DEFAULT NULL COMMENT '创建用户帐号',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_sn` (`order_sn`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='订单';

-- ----------------------------
-- Records of wd_order
-- ----------------------------
INSERT INTO `wd_order` VALUES ('1', 'OD1725155871782211584', '2', '0', '3', '320.00', '1', '0', 'elit consequat nulla veniam', '1', 'apple', '0', '2023-11-16 22:16:26', '2023-11-16 22:16:26');
INSERT INTO `wd_order` VALUES ('2', 'OD1725178858791112704', '2', '5', '1', '5550.00', '1', '0', 'nisi in fugiat voluptate occaecat', '1', 'apple', '0', '2023-11-16 23:47:47', '2023-11-16 23:47:47');
INSERT INTO `wd_order` VALUES ('3', 'OD1725519318566965248', '1', '2', '1', '2706.00', '1', '0', 'id', '1', 'apple', '0', '2023-11-17 22:20:39', '2023-11-17 22:20:39');

-- ----------------------------
-- Table structure for wd_order_item
-- ----------------------------
DROP TABLE IF EXISTS `wd_order_item`;
CREATE TABLE `wd_order_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `prd_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `prd_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `quantity` int(11) DEFAULT '0' COMMENT '商品数量',
  `price` decimal(10,2) DEFAULT '0.00' COMMENT '商品单价',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '删除',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_id` (`order_id`,`prd_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='订单项';

-- ----------------------------
-- Records of wd_order_item
-- ----------------------------
INSERT INTO `wd_order_item` VALUES ('1', '1', '1', '型具此天', '40', '8.00', '0', '2023-11-16 22:16:28', '2023-11-16 22:16:28');
INSERT INTO `wd_order_item` VALUES ('2', '2', '1', '利联选指深', '75', '74.00', '0', '2023-11-16 23:47:47', '2023-11-16 23:47:47');
INSERT INTO `wd_order_item` VALUES ('3', '3', '2', '四支引', '52', '44.00', '0', '2023-11-17 22:20:39', '2023-11-17 22:20:39');
INSERT INTO `wd_order_item` VALUES ('4', '3', '1', '给进影段更而今', '22', '19.00', '0', '2023-11-17 22:20:39', '2023-11-17 22:20:39');

-- ----------------------------
-- Table structure for wd_order_payment
-- ----------------------------
DROP TABLE IF EXISTS `wd_order_payment`;
CREATE TABLE `wd_order_payment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `payment_sn` varchar(100) NOT NULL COMMENT '支付单号',
  `order_sn` varchar(100) DEFAULT NULL COMMENT '订单号',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单ID',
  `order_amount` decimal(10,0) DEFAULT NULL COMMENT '订单应付金额',
  `payment_amount` decimal(10,0) DEFAULT NULL COMMENT '订单已付金额',
  `payment_biz` varchar(100) DEFAULT NULL COMMENT '第三方支付流水号',
  `payment_method` tinyint(4) DEFAULT NULL COMMENT '支付方式',
  `payment_channel` tinyint(4) DEFAULT NULL COMMENT '支付渠道',
  `payment_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '支付状态',
  `payment_date` datetime DEFAULT NULL COMMENT '支付时间',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '删除',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `payment_sn` (`payment_sn`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of wd_order_payment
-- ----------------------------
INSERT INTO `wd_order_payment` VALUES ('1', 'PA1725521987150942208', 'OD1725519318566965248', '3', '2706', null, 'ipsum', '2', '0', '0', null, '0', '2023-11-17 22:31:15', '2023-11-17 22:31:15');

-- ----------------------------
-- Table structure for wd_product
-- ----------------------------
DROP TABLE IF EXISTS `wd_product`;
CREATE TABLE `wd_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `prd_name` varchar(100) NOT NULL COMMENT '商品名称',
  `prd_sn` varchar(50) NOT NULL COMMENT '商品编号',
  `price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '单价',
  `sales_price` decimal(10,2) DEFAULT '0.00' COMMENT '销售价',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `disabled` tinyint(4) DEFAULT '0' COMMENT '禁用',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `prd_sn` (`prd_sn`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='商品';

-- ----------------------------
-- Records of wd_product
-- ----------------------------
INSERT INTO `wd_product` VALUES ('1', 'apple', '003823424', '800.00', '900.00', 'xxxxxxxx', 'yyy442342344', '0', '0', '2023-11-14 21:12:52', '2023-11-24 21:12:56');
INSERT INTO `wd_product` VALUES ('2', 'orange', '00928342', '700.00', '670.00', 'ttt234234', '44234234234', '0', '0', '2023-11-16 21:13:29', '2023-11-16 21:13:32');
INSERT INTO `wd_product` VALUES ('3', '发程千', 'PD1725142245901996032', '84.00', '97.00', '公那子器龙', '文件在使标为意按总安规亲保。增热身着论生各下织保支级。府支五建速流强步少器期亲维好转条快。年受要信斯制机规反声线强示。水些生政把联应已自它适定转军我养。接周治般达己指革回资工是平术眼。', '0', '0', '2023-11-16 21:22:18', '2023-11-16 21:32:56');
INSERT INTO `wd_product` VALUES ('4', '低算生路', 'PD1725187688140771328', '67.00', '98.00', '节许做眼', '近越价王合众示南做单青算厂军。如石置界打会只由车号上华备如是些位东。及会相斗制布必话为南量口放信。发石列比见算同后参已争己信。清声外西最目口家温有进华白相。关东人半族象利厂可将真生运题最方住面。', '1', '0', '2023-11-17 00:22:52', '2023-11-17 00:22:52');
INSERT INTO `wd_product` VALUES ('5', '低算生路', 'PD1725585519338983424', '67.00', '98.00', '节许做眼', '近越价王合众示南做单青算厂军。如石置界打会只由车号上华备如是些位东。及会相斗制布必话为南量口放信。发石列比见算同后参已争己信。清声外西最目口家温有进华白相。关东人半族象利厂可将真生运题最方住面。', '1', '0', '2023-11-18 02:43:42', '2023-11-18 02:43:42');
