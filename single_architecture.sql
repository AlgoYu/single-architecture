/*
 Navicat Premium Data Transfer

 Source Server         : Docker-MySQL
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : 127.0.0.1:3306
 Source Schema         : sigle_architecture

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 06/01/2021 18:58:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` bigint NOT NULL COMMENT '标识',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '账户名称',
  `password` char(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '密码',
  `ip` varchar(65) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'IP地址',
  `enable` tinyint DEFAULT NULL COMMENT '启用',
  `version` int DEFAULT NULL COMMENT '乐观锁',
  `last_login` datetime DEFAULT NULL COMMENT '上一次登录',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `account_id_uindex` (`id`) USING BTREE,
  UNIQUE KEY `account_uindex` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户';

-- ----------------------------
-- Records of account
-- ----------------------------
BEGIN;
INSERT INTO `account` VALUES (1, 'admin', '$2a$10$L7AMqBhiwRrCVNPSMbxIqOerf1WBTFjVDE24S.x2x.ZbofNxUNPii', '127.0.0.1', 1, 0, '2021-01-06 13:41:43', '2021-01-06 13:41:45', '2021-01-06 13:41:46');
COMMIT;

-- ----------------------------
-- Table structure for account_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `account_role_relation`;
CREATE TABLE `account_role_relation` (
  `account_id` bigint NOT NULL COMMENT '账户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`account_id`,`role_id`) USING BTREE,
  UNIQUE KEY `system_user_role_relation_uindex` (`account_id`,`role_id`) USING BTREE,
  KEY `system_user_role_relation_role_fk` (`role_id`) USING BTREE,
  CONSTRAINT `account_role_relation_role_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `account_role_relation_user_fk` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色关系';

-- ----------------------------
-- Records of account_role_relation
-- ----------------------------
BEGIN;
INSERT INTO `account_role_relation` VALUES (1, 1);
COMMIT;

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `id` bigint NOT NULL COMMENT '唯一标识',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '名称',
  `key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '关键字',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `authority_id_uindex` (`id`) USING BTREE,
  UNIQUE KEY `authority_key_uindex` (`key`) USING BTREE,
  UNIQUE KEY `authority_name_uindex` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统权力';

-- ----------------------------
-- Records of authority
-- ----------------------------
BEGIN;
INSERT INTO `authority` VALUES (1, '权限1', 'auth1');
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint NOT NULL COMMENT '唯一标识',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '角色名称',
  `key` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '关键字',
  `version` int DEFAULT NULL COMMENT '乐观锁',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `role_id_uindex` (`id`) USING BTREE,
  UNIQUE KEY `role_name_uindex` (`name`) USING BTREE,
  UNIQUE KEY `role_key_uindex` (`key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统角色';

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (1, '超级管理员', 'ROLE_Administrator', 0, '2021-01-06 13:54:03', '2021-01-06 13:54:05');
COMMIT;

-- ----------------------------
-- Table structure for role_authority_relation
-- ----------------------------
DROP TABLE IF EXISTS `role_authority_relation`;
CREATE TABLE `role_authority_relation` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `authority_id` bigint NOT NULL COMMENT '权力ID',
  PRIMARY KEY (`role_id`,`authority_id`) USING BTREE,
  UNIQUE KEY `role_authority_relation_uindex` (`role_id`,`authority_id`) USING BTREE,
  KEY `role_authority_relation_authority_fk` (`authority_id`) USING BTREE,
  CONSTRAINT `role_authority_relation_authority_fk` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `role_authority_relation_role_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色权力';

-- ----------------------------
-- Records of role_authority_relation
-- ----------------------------
BEGIN;
INSERT INTO `role_authority_relation` VALUES (1, 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
