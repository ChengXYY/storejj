/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.01
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : storejj

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 21/08/2019 09:39:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salt` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `group_id` int(4) NULL DEFAULT NULL,
  `last_login_ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `last_login_time` datetime(0) NULL DEFAULT NULL,
  `login_count` int(10) NULL DEFAULT 0,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `parent_id` int(11) NULL DEFAULT 0,
  `create_by` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for admin_auth
-- ----------------------------
DROP TABLE IF EXISTS `admin_auth`;
CREATE TABLE `admin_auth`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `parent_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_by` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 93 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for admin_group
-- ----------------------------
DROP TABLE IF EXISTS `admin_group`;
CREATE TABLE `admin_group`  (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `auth` varchar(600) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sort` int(4) NOT NULL DEFAULT 99,
  `parent_id` int(4) NULL DEFAULT 0,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `create_by` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for admin_log
-- ----------------------------
DROP TABLE IF EXISTS `admin_log`;
CREATE TABLE `admin_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `create_by` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 206 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `code` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `author` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `create_by` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `code` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `intro` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片 filename',
  `create_by` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for page_tpl
-- ----------------------------
DROP TABLE IF EXISTS `page_tpl`;
CREATE TABLE `page_tpl`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `create_by` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for picture
-- ----------------------------
DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `code` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `intro` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `link` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '跳转链接',
  `name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `size` int(11) NULL DEFAULT NULL,
  `ext` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_by` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `code` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pic` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_shop` tinyint(1) NULL DEFAULT 0 COMMENT '是否商城产品 0-否 1-是',
  `price` int(11) NULL DEFAULT 0 COMMENT '标价，积分数',
  `stock` int(4) NULL DEFAULT 0 COMMENT '库存',
  `category_code` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类编码',
  `tags` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态，1-在售  2-已预订 0-已售',
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除 1-已删除 0-未删除',
  `create_by` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_images
-- ----------------------------
DROP TABLE IF EXISTS `product_images`;
CREATE TABLE `product_images`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `product_id` int(11) NULL DEFAULT NULL,
  `size` int(11) NULL DEFAULT NULL,
  `ext` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_by` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_order
-- ----------------------------
DROP TABLE IF EXISTS `product_order`;
CREATE TABLE `product_order`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `code` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `product_id` int(11) NULL DEFAULT NULL,
  `count` int(11) NULL DEFAULT 0,
  `price` int(11) NULL DEFAULT 0,
  `amount` int(11) NULL DEFAULT 0,
  `user_account` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` tinyint(2) NULL DEFAULT 0,
  `remark` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `is_cash` tinyint(1) NULL DEFAULT 1,
  `create_by` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for site_page
-- ----------------------------
DROP TABLE IF EXISTS `site_page`;
CREATE TABLE `site_page`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `tpl_id` int(11) NULL DEFAULT 0,
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `css` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `create_by` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `value` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `text` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sort` int(4) NULL DEFAULT 0,
  `desc` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_by` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `nickname` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `account` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `salt` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `level` int(4) NULL DEFAULT 0 COMMENT '会员等级',
  `points` int(11) NULL DEFAULT 0 COMMENT '现有积分',
  `points_sum` int(11) NULL DEFAULT 0 COMMENT '积分累积',
  `status` tinyint(1) NULL DEFAULT 1,
  `create_by` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_membership
-- ----------------------------
DROP TABLE IF EXISTS `user_membership`;
CREATE TABLE `user_membership`  (
  `id` int(4) UNSIGNED NOT NULL AUTO_INCREMENT,
  `level` int(4) NULL DEFAULT 0 COMMENT '0-普通会员，1、2、3……星级会员',
  `title` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `min_points` int(11) NULL DEFAULT 0,
  `max_points` int(11) NULL DEFAULT 0,
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `create_by` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_record
-- ----------------------------
DROP TABLE IF EXISTS `user_record`;
CREATE TABLE `user_record`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `amount` int(11) NULL DEFAULT NULL COMMENT '消费额  分',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `create_by` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作员',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户消费记录' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
