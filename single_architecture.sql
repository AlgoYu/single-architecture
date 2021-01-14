/*
 Navicat Premium Data Transfer

 Source Server         : Docker-MySQL
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : 127.0.0.1:3306
 Source Schema         : single_architecture

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 14/01/2021 10:54:15
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
INSERT INTO `account` VALUES (1347107263809724418, 'admin', '$2a$10$L7AMqBhiwRrCVNPSMbxIqOerf1WBTFjVDE24S.x2x.ZbofNxUNPii', '127.0.0.1', 1, 0, '2021-01-06 13:41:43', '2021-01-06 13:41:45', '2021-01-06 13:41:46');
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
INSERT INTO `account_role_relation` VALUES (1347107263809724418, 1347107367358693378);
COMMIT;

-- ----------------------------
-- Table structure for authority
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `id` bigint NOT NULL COMMENT '唯一标识',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '名称',
  `key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '关键字',
  `pid` bigint DEFAULT NULL COMMENT '父权力ID',
  `sort` int DEFAULT NULL COMMENT '排序',
  `uri` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'URI',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `authority_id_uindex` (`id`) USING BTREE,
  UNIQUE KEY `authority_key_uindex` (`key`) USING BTREE,
  UNIQUE KEY `authority_name_uindex` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统权力';

-- ----------------------------
-- Records of authority
-- ----------------------------
BEGIN;
INSERT INTO `authority` VALUES (1348514149771689985, '账号列表', 'ACCOUNT', 1348542368843452418, 0, '/Account');
INSERT INTO `authority` VALUES (1348514149771689986, '增加账号', 'ACCOUNT:ADD', 1348514149771689985, 0, NULL);
INSERT INTO `authority` VALUES (1348514149771689987, '删除账号', 'ACCOUNT:DELETE', 1348514149771689985, 0, NULL);
INSERT INTO `authority` VALUES (1348514149771689988, '修改账号', 'ACCOUNT:MODIFY', 1348514149771689985, 0, NULL);
INSERT INTO `authority` VALUES (1348514149771689989, '查询账号', 'ACCOUNT:GET', 1348514149771689985, 0, NULL);
INSERT INTO `authority` VALUES (1348516637811826690, '角色列表', 'ROLE', 1348542368843452418, 0, '/Role');
INSERT INTO `authority` VALUES (1348516637811826691, '增加角色', 'ROLE:ADD', 1348516637811826690, 0, NULL);
INSERT INTO `authority` VALUES (1348516637811826692, '删除角色', 'ROLE:DELETE', 1348516637811826690, 0, NULL);
INSERT INTO `authority` VALUES (1348516637811826693, '修改角色', 'ROLE:MODIFY', 1348516637811826690, 0, NULL);
INSERT INTO `authority` VALUES (1348516637811826694, '查询角色', 'ROLE:GET', 1348516637811826690, 0, NULL);
INSERT INTO `authority` VALUES (1348517662148952066, '权限列表', 'AUTHORITY', 1348542368843452418, 0, '/Authority');
INSERT INTO `authority` VALUES (1348517662148952067, '增加权限', 'AUTHORITY:ADD', 1348517662148952066, 0, NULL);
INSERT INTO `authority` VALUES (1348517662148952068, '删除权限', 'AUTHORITY:DELETE', 1348517662148952066, 0, NULL);
INSERT INTO `authority` VALUES (1348517662148952069, '修改权限', 'AUTHORITY:MODIFY', 1348517662148952066, 0, NULL);
INSERT INTO `authority` VALUES (1348517662148952070, '查询权限', 'AUTHORITY:GET', 1348517662148952066, 0, NULL);
INSERT INTO `authority` VALUES (1348542368843452418, '系统管理', 'SYSTEM', 0, 0, '/System');
INSERT INTO `authority` VALUES (1348542368843452419, '开发者工具', 'DEVTOOLS', 0, 0, '/DevTools');
INSERT INTO `authority` VALUES (1348542368843452420, '系统设置', 'SETTING', 0, 0, '/Setting');
INSERT INTO `authority` VALUES (1348542368843452421, '数据中心', 'DASHBOARD', 0, 1, '/Dashboard');
INSERT INTO `authority` VALUES (1348542368843452422, '代码生成器', 'CODEGENERATOR', 1348542368843452419, 0, '/CodeGenerator');
INSERT INTO `authority` VALUES (1348542368843452423, '数据监控', 'MONITOR', 1348542368843452419, 0, '/Monitor');
INSERT INTO `authority` VALUES (1348542368843452424, '异常信息', 'EXCEPTION', 1348542368843452419, 0, '/Exception');
INSERT INTO `authority` VALUES (1348542368843452425, '开发文档', 'DEVDOC', 1348542368843452419, 0, '/DevDoc');
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
INSERT INTO `role` VALUES (1347107367358693378, '超级管理员', 'ROLE_Administrator', 0, '2021-01-06 13:54:03', '2021-01-06 13:54:05');
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
INSERT INTO `role_authority_relation` VALUES (1347107367358693378, 1348514149771689985);
INSERT INTO `role_authority_relation` VALUES (1347107367358693378, 1348516637811826690);
INSERT INTO `role_authority_relation` VALUES (1347107367358693378, 1348516637811826692);
INSERT INTO `role_authority_relation` VALUES (1347107367358693378, 1348542368843452418);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
