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

 Date: 21/08/2019 09:38:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
-- Records of product_order
-- ----------------------------
INSERT INTO `product_order` VALUES (1, 'DPZBrCzFFRCXmDSIW6R7', 5, 21, 15900, 333900, '18813197743', 0, '', 1, NULL, NULL);
INSERT INTO `product_order` VALUES (2, 'DPZB4wAqblESi0wzWOmh', 7, 2, 3399, 6798, '18813197743', 0, '南京市栖霞区学森路199号', 0, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
