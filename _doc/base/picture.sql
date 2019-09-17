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
INSERT INTO `picture` VALUES (15, 'index01', '', NULL, NULL, '1568626760953_shouzhuo2.jpg', NULL, NULL, 'System', '2019-09-16 17:28:23');
INSERT INTO `picture` VALUES (16, 'index-bg01', '', NULL, NULL, '1568627021446_xianglian2.jpg', NULL, NULL, 'System', '2019-09-16 17:43:43');
INSERT INTO `picture` VALUES (17, 'index-bg02', '', NULL, NULL, '1568627057217_xianglian.jpeg', NULL, NULL, 'System', '2019-09-16 17:44:18');
INSERT INTO `picture` VALUES (18, 'index-bg03', '', NULL, NULL, '1568627079566_yupai6.jpg', NULL, NULL, 'System', '2019-09-16 17:44:41');
INSERT INTO `picture` VALUES (19, 'index-bg04', '', NULL, NULL, '1568627097900_yupai4.png', NULL, NULL, 'System', '2019-09-16 17:44:48');
INSERT INTO `picture` VALUES (20, 'index-bg05', '', NULL, NULL, '1568627111492_pingankou.jpg', NULL, NULL, 'System', '2019-09-16 17:45:17');
INSERT INTO `picture` VALUES (21, 'index-bg06', '', NULL, NULL, '1568627130347_shouzhuo3.jpg', NULL, NULL, 'System', '2019-09-16 17:45:31');
INSERT INTO `picture` VALUES (22, 'qrcode01', '', NULL, NULL, '1568627489890_QR_code.png', NULL, NULL, 'System', '2019-09-16 17:47:59');
INSERT INTO `picture` VALUES (23, 'usercenter', '', NULL, NULL, '1568628237614_xianglian.jpeg', NULL, NULL, 'System', '2019-09-16 18:02:54');
INSERT INTO `picture` VALUES (24, 'userorder', '', NULL, NULL, '1568628316663_yupai6.jpg', NULL, NULL, 'System', '2019-09-16 18:04:38');
INSERT INTO `picture` VALUES (25, 'store01', '', NULL, NULL, '1568684762236_yupai7.jpg', NULL, NULL, 'System', '2019-09-17 09:46:03');
INSERT INTO `picture` VALUES (26, 'store02', '', NULL, NULL, '1568684776588_jiezhi2.jpg', NULL, NULL, 'System', '2019-09-17 09:46:18');
INSERT INTO `picture` VALUES (27, 'store03', '', NULL, NULL, '1568684787079_shouzhuo5.jpeg', NULL, NULL, 'System', '2019-09-17 09:46:28');

SET FOREIGN_KEY_CHECKS = 1;
