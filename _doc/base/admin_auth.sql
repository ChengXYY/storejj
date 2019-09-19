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

 Date: 19/09/2019 11:13:47
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
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 115 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_auth
-- ----------------------------
INSERT INTO `admin_auth` VALUES (1, '9999', '后台管理系统', NULL, NULL, NULL);
INSERT INTO `admin_auth` VALUES (2, '1000', '网页配置', '9999', NULL, NULL);
INSERT INTO `admin_auth` VALUES (3, '2000', '资料管理', '9999', NULL, NULL);
INSERT INTO `admin_auth` VALUES (4, '3000', '产品管理', '9999', NULL, NULL);
INSERT INTO `admin_auth` VALUES (5, '4000', '会员管理', '9999', NULL, NULL);
INSERT INTO `admin_auth` VALUES (6, '5000', '订单管理', '9999', NULL, NULL);
INSERT INTO `admin_auth` VALUES (7, '6000', '系统设置', '9999', NULL, NULL);
INSERT INTO `admin_auth` VALUES (8, '1100', '页面管理', '1000', NULL, NULL);
INSERT INTO `admin_auth` VALUES (9, '1200', '模板配置', '1000', NULL, NULL);
INSERT INTO `admin_auth` VALUES (10, '2100', '文章', '2000', NULL, NULL);
INSERT INTO `admin_auth` VALUES (11, '2200', '图片', '2000', NULL, NULL);
INSERT INTO `admin_auth` VALUES (12, '3100', '产品列表', '3000', NULL, NULL);
INSERT INTO `admin_auth` VALUES (13, '3200', '积分商城', '3000', NULL, NULL);
INSERT INTO `admin_auth` VALUES (14, '3300', '已下架商品', '3000', NULL, NULL);
INSERT INTO `admin_auth` VALUES (15, '3400', '产品分类', '3000', NULL, NULL);
INSERT INTO `admin_auth` VALUES (16, '4100', '会员信息', '4000', NULL, NULL);
INSERT INTO `admin_auth` VALUES (17, '4200', '会员积分等级查询', '4000', NULL, NULL);
INSERT INTO `admin_auth` VALUES (18, '4300', '会员积分等级修改', '4000', NULL, NULL);
INSERT INTO `admin_auth` VALUES (19, '5100', '订单列表', '5000', NULL, NULL);
INSERT INTO `admin_auth` VALUES (20, '5200', '统计图', '5000', NULL, NULL);
INSERT INTO `admin_auth` VALUES (21, '6100', '管理员配置', '6000', NULL, NULL);
INSERT INTO `admin_auth` VALUES (22, '6200', '管理员组配置', '6000', NULL, NULL);
INSERT INTO `admin_auth` VALUES (23, '6300', '权限配置', '6000', NULL, NULL);
INSERT INTO `admin_auth` VALUES (24, '6400', '管理员日志', '6000', NULL, NULL);
INSERT INTO `admin_auth` VALUES (25, '6500', '会员制度配置', '6000', NULL, NULL);
INSERT INTO `admin_auth` VALUES (26, '1101', '网页管理-搜索', '1100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (27, '1102', '网页管理-新增', '1100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (28, '1103', '网页管理-编辑', '1100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (29, '1104', '网页管理-删除', '1100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (30, '1105', '网页管理-批量删除', '1100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (31, '1201', '模板配置-搜索', '1200', NULL, NULL);
INSERT INTO `admin_auth` VALUES (32, '1202', '模板配置-新增', '1200', NULL, NULL);
INSERT INTO `admin_auth` VALUES (33, '1203', '模板配置-编辑', '1200', NULL, NULL);
INSERT INTO `admin_auth` VALUES (34, '1204', '模板配置-删除', '1200', NULL, NULL);
INSERT INTO `admin_auth` VALUES (35, '1205', '模板配置-批量删除', '1200', NULL, NULL);
INSERT INTO `admin_auth` VALUES (36, '2101', '文章-搜索', '2100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (37, '2102', '文章-新增', '2100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (38, '2103', '文章-编辑', '2100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (39, '2104', '文章-删除', '2100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (40, '2105', '文章-批量删除', '2100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (41, '2201', '图片-搜索', '2200', NULL, NULL);
INSERT INTO `admin_auth` VALUES (42, '2202', '图片-新增', '2200', NULL, NULL);
INSERT INTO `admin_auth` VALUES (43, '2203', '图片-编辑', '2200', NULL, NULL);
INSERT INTO `admin_auth` VALUES (44, '2204', '图片-删除', '2200', NULL, NULL);
INSERT INTO `admin_auth` VALUES (45, '2205', '图片-批量删除', '2200', NULL, NULL);
INSERT INTO `admin_auth` VALUES (46, '3101', '产品-搜索', '3100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (47, '3102', '产品-新增', '3100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (48, '3103', '产品-编辑', '3100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (49, '3104', '产品-加入/移出积分商城', '3100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (50, '3105', '产品-下架', '3100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (51, '3106', '产品-批量下架', '3100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (52, '3107', '产品-售出', '3100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (53, '3201', '商城-搜索', '3200', NULL, NULL);
INSERT INTO `admin_auth` VALUES (54, '3202', '商城-兑换', '3200', NULL, NULL);
INSERT INTO `admin_auth` VALUES (57, '3301', '已下架-搜索', '3300', NULL, NULL);
INSERT INTO `admin_auth` VALUES (58, '3302', '已下架-批量上架', '3300', NULL, NULL);
INSERT INTO `admin_auth` VALUES (59, '3401', '分类-搜索', '3400', NULL, NULL);
INSERT INTO `admin_auth` VALUES (60, '3402', '分类-新增', '3400', NULL, NULL);
INSERT INTO `admin_auth` VALUES (61, '3403', '分类-编辑', '3400', NULL, NULL);
INSERT INTO `admin_auth` VALUES (62, '4101', '会员-搜索', '4100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (63, '4102', '会员-后台注册', '4100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (64, '4103', '会员-编辑会员信息', '4100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (66, '4104', '会员-账户启用/禁用', '4100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (69, '4301', '积分变更', '4300', NULL, NULL);
INSERT INTO `admin_auth` VALUES (70, '4302', '等级变更', '4300', NULL, NULL);
INSERT INTO `admin_auth` VALUES (73, '5101', '订单查询', '5100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (74, '5102', '订单编辑', '5100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (75, '5201', '多条件筛选', '5200', NULL, NULL);
INSERT INTO `admin_auth` VALUES (76, '6101', '管理员-搜索', '6100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (77, '6102', '管理员-新增', '6100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (78, '6103', '管理员-编辑', '6100', '', '2019-08-20 11:40:33');
INSERT INTO `admin_auth` VALUES (79, '6104', '管理员-删除', '6100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (80, '6105', '管理员-重置密码', '6100', NULL, NULL);
INSERT INTO `admin_auth` VALUES (81, '6201', '群组-搜索', '6200', NULL, NULL);
INSERT INTO `admin_auth` VALUES (82, '6202', '群组-新增', '6200', NULL, NULL);
INSERT INTO `admin_auth` VALUES (83, '6203', '群组-编辑', '6200', NULL, NULL);
INSERT INTO `admin_auth` VALUES (84, '6204', '群组-查看群成员', '6200', NULL, NULL);
INSERT INTO `admin_auth` VALUES (85, '6205', '群组-移除群成员', '6200', NULL, NULL);
INSERT INTO `admin_auth` VALUES (86, '6206', '群组-删除', '6200', NULL, NULL);
INSERT INTO `admin_auth` VALUES (87, '6301', '权限树分配权限', '6300', NULL, NULL);
INSERT INTO `admin_auth` VALUES (88, '6401', '日志搜索', '6400', NULL, NULL);
INSERT INTO `admin_auth` VALUES (89, '6501', '会员制度-搜索', '6500', NULL, NULL);
INSERT INTO `admin_auth` VALUES (90, '6502', '会员制度-新增', '6500', NULL, NULL);
INSERT INTO `admin_auth` VALUES (91, '6503', '会员制度-编辑', '6500', NULL, NULL);
INSERT INTO `admin_auth` VALUES (92, '6504', '会员制度-删除', '6500', NULL, NULL);
INSERT INTO `admin_auth` VALUES (93, '3404', '分类删除', '3400', NULL, '2019-08-26 11:21:43');
INSERT INTO `admin_auth` VALUES (94, '6600', '店铺配置', '6000', NULL, '2019-08-26 15:46:27');
INSERT INTO `admin_auth` VALUES (95, '6601', '店铺-列表', '6600', NULL, '2019-08-26 15:46:38');
INSERT INTO `admin_auth` VALUES (96, '6602', '店铺-新增', '6600', NULL, '2019-08-26 15:46:45');
INSERT INTO `admin_auth` VALUES (97, '6603', '店铺-编辑', '6600', NULL, '2019-08-26 15:46:54');
INSERT INTO `admin_auth` VALUES (98, '6604', '店铺-删除', '6600', NULL, '2019-08-26 15:47:03');
INSERT INTO `admin_auth` VALUES (99, '6700', '通知配置', '6000', NULL, '2019-09-08 15:28:39');
INSERT INTO `admin_auth` VALUES (100, '6701', '通知-列表', '6700', NULL, '2019-09-08 15:28:54');
INSERT INTO `admin_auth` VALUES (101, '6702', '通知-新增', '6700', NULL, '2019-09-08 15:29:09');
INSERT INTO `admin_auth` VALUES (102, '6703', '通知-编辑', '6700', NULL, '2019-09-08 15:29:24');
INSERT INTO `admin_auth` VALUES (103, '6704', '通知-删除', '6700', NULL, '2019-09-08 15:29:36');
INSERT INTO `admin_auth` VALUES (104, '6800', '服务配置', '6000', NULL, '2019-09-08 16:31:11');
INSERT INTO `admin_auth` VALUES (105, '6801', '服务-列表', '6800', NULL, '2019-09-08 16:31:19');
INSERT INTO `admin_auth` VALUES (106, '6802', '服务-新增', '6800', NULL, '2019-09-08 16:31:26');
INSERT INTO `admin_auth` VALUES (107, '6803', '服务-编辑', '6800', NULL, '2019-09-08 16:31:29');
INSERT INTO `admin_auth` VALUES (108, '6804', '服务-删除', '6800', NULL, '2019-09-08 16:31:33');
INSERT INTO `admin_auth` VALUES (109, '6900', '联系方式配置', '6000', NULL, '2019-09-17 10:59:51');
INSERT INTO `admin_auth` VALUES (110, '6901', '编辑联系方式', '6900', NULL, '2019-09-17 11:00:08');
INSERT INTO `admin_auth` VALUES (112, '7000', '用户意见', '9999', NULL, '2019-09-17 16:57:25');
INSERT INTO `admin_auth` VALUES (113, '7100', '意见查看', '7000', NULL, '2019-09-17 17:00:16');
INSERT INTO `admin_auth` VALUES (114, '7200', '意见处理', '7000', NULL, '2019-09-17 17:01:12');
