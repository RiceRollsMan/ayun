/*
Navicat MySQL Data Transfer

Source Server         : 1
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : gene

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2022-05-14 14:12:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `file`
-- ----------------------------
DROP TABLE IF EXISTS `file2`;
CREATE TABLE `file2` (
                         `id` bigint(127) NOT NULL AUTO_INCREMENT COMMENT '编号',
                         `userName` varchar(255) NOT NULL COMMENT '用户名',
                         `fileName` varchar(255) NOT NULL COMMENT '文件名',
                         `filePath` varchar(255) DEFAULT NULL COMMENT '文件路径',
                         `fileSize` varchar(255) NOT NULL COMMENT '文件大小',
                         `fileType` varchar(255) NOT NULL COMMENT '文件类型',
                         `state` int(2) NOT NULL COMMENT '状态，是否被删除',
                         `isDir` int(2) NOT NULL COMMENT '是否路径',
                         `addDate` datetime NOT NULL COMMENT '创建/修改时间',
                         `deleteDate` datetime DEFAULT NULL COMMENT '删除时间',

                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file
-- ----------------------------
