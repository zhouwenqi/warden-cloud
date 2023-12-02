/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.11
Source Server Version : 50743
Source Host           : 192.168.1.11:3306
Source Database       : warden_cloud_user

Target Server Type    : MYSQL
Target Server Version : 50743
File Encoding         : 65001

Date: 2023-12-02 18:13:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for wd_user
-- ----------------------------
DROP TABLE IF EXISTS `wd_user`;
CREATE TABLE `wd_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(50) DEFAULT NULL COMMENT '帐号',
  `pwd` varchar(120) DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(40) DEFAULT NULL COMMENT '真实姓名',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮箱',
  `gender` tinyint(2) DEFAULT NULL COMMENT '性别(0:男,1:女,2:未知)',
  `face` varchar(80) DEFAULT NULL COMMENT '图像',
  `disabled` bit(1) DEFAULT b'0' COMMENT '禁用',
  `deleted` bit(1) DEFAULT b'0' COMMENT '删除',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='前台用户(C端客户)';

-- ----------------------------
-- Records of wd_user
-- ----------------------------
INSERT INTO `wd_user` VALUES ('1', 'apple', '$2a$10$YrLdiL24WJ6xmVkzqmppy.gqusqocT56kRlXv5Y1Ij5J6IpF/ka9i', '小苹果', 'apple', '18688888888', '186@163.com', '0', null, '\0', '\0', '2023-11-04 16:35:10', '2023-11-04 16:35:12');
INSERT INTO `wd_user` VALUES ('2', 'facebook', '$2a$10$YrLdiL24WJ6xmVkzqmppy.gqusqocT56kRlXv5Y1Ij5J6IpF/ka9i', '山大王', 'FB', '18666666666', 'facebook@gmail.com', '1', null, '\0', '\0', '2023-11-07 22:46:22', '2023-11-07 22:46:25');
INSERT INTO `wd_user` VALUES ('3', 'zhouwenqi', '$2a$10$vIeWKINtToHFOzfos0B1bOx0lBozJniC8VDE7mGwcpzz7pbhfkPfm', null, null, null, 'f.sehibsz@qq.com', null, null, null, '\0', '2023-11-15 00:07:44', '2023-11-15 00:07:44');

-- ----------------------------
-- Table structure for wd_user_permission
-- ----------------------------
DROP TABLE IF EXISTS `wd_user_permission`;
CREATE TABLE `wd_user_permission` (
  `user_id` bigint(21) NOT NULL COMMENT '用户id',
  `permission_value` varchar(50) NOT NULL COMMENT '权限值',
  UNIQUE KEY `user_id` (`user_id`,`permission_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of wd_user_permission
-- ----------------------------
