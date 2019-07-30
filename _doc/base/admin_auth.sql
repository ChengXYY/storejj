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

 Date: 30/07/2019 17:50:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_auth
-- ----------------------------
INSERT INTO `admin_auth` VALUES (1, '9999', '后台管理系统', NULL, NULL, NULL);
INSERT INTO `admin_auth` VALUES (2, '9001', '系统设置', '9999', NULL, NULL);
INSERT INTO `admin_auth` VALUES (3, '1001', '网页配置', '9999', NULL, NULL);
INSERT INTO `admin_auth` VALUES (4, '1002', '资料管理', '9999', NULL, NULL);
INSERT INTO `admin_auth` VALUES (5, '1003', '产品管理', '9999', NULL, NULL);
INSERT INTO `admin_auth` VALUES (6, '1004', '会员管理', '9999', NULL, NULL);
INSERT INTO `admin_auth` VALUES (7, '2901', '管理员管理', '9001', NULL, NULL);
INSERT INTO `admin_auth` VALUES (8, '2902', '管理员组管理', '9001', NULL, NULL);
INSERT INTO `admin_auth` VALUES (9, '2903', '权限配置', '9001', NULL, NULL);
INSERT INTO `admin_auth` VALUES (10, '2904', '管理员日志', '9001', NULL, NULL);
INSERT INTO `admin_auth` VALUES (11, '2905', '会员制度配置', '9001', NULL, NULL);
INSERT INTO `admin_auth` VALUES (12, '2111', '页面管理', '1001', NULL, NULL);
INSERT INTO `admin_auth` VALUES (13, '2112', '模板管理', '1001', NULL, NULL);
INSERT INTO `admin_auth` VALUES (14, '2121', '文章', '1002', NULL, NULL);
INSERT INTO `admin_auth` VALUES (15, '2122', '图片', '1002', NULL, NULL);
INSERT INTO `admin_auth` VALUES (16, '2131', '产品', '1003', NULL, NULL);
INSERT INTO `admin_auth` VALUES (17, '2132', '分类', '1003', NULL, NULL);
INSERT INTO `admin_auth` VALUES (18, '2133', '积分商城', '1003', NULL, NULL);
INSERT INTO `admin_auth` VALUES (19, '2134', '已删除产品', '1003', NULL, NULL);
INSERT INTO `admin_auth` VALUES (20, '2141', '会员列表', '1004', NULL, NULL);
INSERT INTO `admin_auth` VALUES (21, '2142', '会员积分等级查询', '2146', NULL, NULL);
INSERT INTO `admin_auth` VALUES (22, '2143', '会员积分等级修改', '2146', NULL, NULL);
INSERT INTO `admin_auth` VALUES (23, '2144', '注册会员', '1004', NULL, NULL);
INSERT INTO `admin_auth` VALUES (24, '2145', '编辑会员', '1004', NULL, NULL);
INSERT INTO `admin_auth` VALUES (25, '2146', '信息查询', '1004', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
