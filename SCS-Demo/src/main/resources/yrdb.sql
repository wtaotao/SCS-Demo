/*
Navicat MySQL Data Transfer

Source Server         : localDB
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : yrdb

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2023-09-15 14:01:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dim_monitor_point
-- ----------------------------
DROP TABLE IF EXISTS `dim_monitor_point`;
CREATE TABLE `dim_monitor_point` (
  `monitor_point_id` int(11) NOT NULL COMMENT '监控点编号',
  `monitor_point_name` char(32) DEFAULT NULL COMMENT '监控点名称',
  `monitor_point_amt` decimal(18,2) DEFAULT NULL COMMENT '监控收费(天)',
  `monitor_point_risk_grade` varchar(12) DEFAULT NULL COMMENT '监控点风险等级',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据更新时间',
  `flag` char(1) DEFAULT NULL COMMENT '数据有效标志(Y/N)',
  PRIMARY KEY (`monitor_point_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='监控点维度表';

-- ----------------------------
-- Records of dim_monitor_point
-- ----------------------------
INSERT INTO `dim_monitor_point` VALUES ('1', '标题', '10.11', '1', '2017-03-30 16:01:42', 'Y');
INSERT INTO `dim_monitor_point` VALUES ('2', '新建', '22.03', '1', '2017-05-12 14:48:00', 'Y');
INSERT INTO `dim_monitor_point` VALUES ('3', '删除', '16.12', '4', '2017-03-30 16:02:20', 'Y');
INSERT INTO `dim_monitor_point` VALUES ('4', '修改', '14.11', '3', '2017-03-30 16:02:39', 'Y');
INSERT INTO `dim_monitor_point` VALUES ('5', '查询', '66.30', '5', '2017-03-30 16:02:56', 'Y');

-- ----------------------------
-- Table structure for tb_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_permission`;
CREATE TABLE `tb_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `name` varchar(100) NOT NULL COMMENT '权限名称',
  `permission_type` tinyint(1) NOT NULL COMMENT '权限类型 0：未知 1：菜单 2：按钮',
  `url` varchar(256) DEFAULT NULL COMMENT '权限url（备用）',
  `permission_key` varchar(100) DEFAULT NULL COMMENT '权限标识（按钮的标识）',
  `sort_no` int(5) NOT NULL COMMENT '排列序号',
  `parent_id` int(11) DEFAULT '0' COMMENT '父权限id',
  `permission_class` varchar(30) DEFAULT NULL COMMENT '权限样式',
  `target_page` varchar(256) DEFAULT NULL COMMENT '跳转目标页面',
  `description` varchar(200) DEFAULT NULL COMMENT '描述信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `sys_id` int(11) DEFAULT '1' COMMENT '系统ID',
  `grade` varchar(30) NOT NULL DEFAULT '' COMMENT '等级',
  PRIMARY KEY (`id`),
  KEY `uk_parent_id` (`parent_id`) USING BTREE,
  KEY `uk_name` (`name`,`sys_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of tb_permission
-- ----------------------------

-- ----------------------------
-- Table structure for scs_user
-- ----------------------------
DROP TABLE IF EXISTS `scs_user`;
CREATE TABLE `scs_user` (
  `id` int(11) NOT NULL COMMENT '编号',
  `name` char(32) DEFAULT NULL COMMENT '姓名',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `sex` tinyint(1) DEFAULT NULL COMMENT '性别（0：男，1：女）',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据更新时间',
  `flag` char(1) DEFAULT NULL COMMENT '数据有效标志(Y/N)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';