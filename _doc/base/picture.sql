/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost:3306
 Source Schema         : storejj

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 17/09/2019 17:59:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of picture
-- ----------------------------
INSERT INTO `picture` (`code`)VALUES ('index01');
INSERT INTO `picture` (`code`)VALUES ('index-bg01');
INSERT INTO `picture` (`code`)VALUES ('index-bg02');
INSERT INTO `picture` (`code`)VALUES ('index-bg03');
INSERT INTO `picture` (`code`)VALUES ('index-bg04');
INSERT INTO `picture` (`code`)VALUES ('index-bg05');
INSERT INTO `picture` (`code`)VALUES ('index-bg06');
INSERT INTO `picture` (`code`)VALUES ('qrcode01');
INSERT INTO `picture` (`code`)VALUES ('usercenter');
INSERT INTO `picture` (`code`)VALUES ('userorder');
INSERT INTO `picture` (`code`)VALUES ('news');
INSERT INTO `picture` (`code`)VALUES ('jewellery');

SET FOREIGN_KEY_CHECKS = 1;
