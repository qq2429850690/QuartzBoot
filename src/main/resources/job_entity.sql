/*
 Navicat Premium Data Transfer

 Source Server         : iomp
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : 10.114.10.28:13306
 Source Schema         : iomp

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 08/02/2021 09:58:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for job_entity
-- ----------------------------
DROP TABLE IF EXISTS `job_entity`;
CREATE TABLE `job_entity`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job_group` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cron` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `job_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fail_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `exe_status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `last_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of job_entity
-- ----------------------------
INSERT INTO `job_entity` VALUES (6, 'OLAY Job', 'Nomal', '0/5 * * * * ?', 'com.pactera.job.DynamicJob', '第五个', NULL, 'CLOSE', 'success', '2021-02-03 09:31:55');
INSERT INTO `job_entity` VALUES (7, 'A001', 'A', '0 0/2 * * * ?', 'com.pactera.job.DynamicJob', 'A001财富管理AUM(亿元)任务跑批', NULL, 'CLOSE', 'success', '2021-01-27 10:32:54');
INSERT INTO `job_entity` VALUES (8, 'A002', 'A', '0 0/2 * * * ?', 'com.pactera.job.DynamicJob', '人民币个人存款（时点）', NULL, 'CLOSE', 'success', '2021-01-27 10:32:54');
INSERT INTO `job_entity` VALUES (9, 'A003', 'A', '0 0/5 * * * ?', 'com.pactera.job.DynamicJob', '人民币个人核心存款（日均）', NULL, 'CLOSE', 'success', '2021-01-27 10:32:50');
INSERT INTO `job_entity` VALUES (10, 'A004', 'A', '0 0/10 * * * ?', 'com.pactera.job.DynamicJob', '个人存款效率指标（亿元）', NULL, 'CLOSE', 'success', '2021-01-27 10:32:45');
INSERT INTO `job_entity` VALUES (11, 'RP0009', 'B', '0 31 9 * * ?', 'com.pactera.job.ReportDataBuildJob', '生成 个贷 的报表文件', '/ by zero', 'OPEN', 'fail', '2021-02-08 09:31:01');
INSERT INTO `job_entity` VALUES (12, 'B001', 'B', '0 16 9 * * ?', 'com.pactera.job.DynamicJob', '个人贷款', '', 'OPEN', 'success', '2021-02-08 09:16:00');
INSERT INTO `job_entity` VALUES (13, 'B002', 'B', '0 16 9 * * ?', 'com.pactera.job.DynamicJob', '个人住房贷款', '', 'OPEN', 'success', '2021-02-08 09:16:01');
INSERT INTO `job_entity` VALUES (14, 'B003', 'B', '0 16 9 * * ?', 'com.pactera.job.DynamicJob', '个人消费贷款', '', 'OPEN', 'success', '2021-02-08 09:16:01');
INSERT INTO `job_entity` VALUES (15, 'B004', 'B', '0 16 9 * * ?', 'com.pactera.job.DynamicJob', '个人生产经营贷款', '', 'OPEN', 'success', '2021-02-08 09:16:01');

SET FOREIGN_KEY_CHECKS = 1;
