/*
 Navicat MySQL Data Transfer

 Source Server         : LocalMySQL
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : crown2

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 11/09/2019 15:13:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table` (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'table ID',
  `table_name` varchar(200) DEFAULT '' COMMENT 'table name',
  `table_comment` varchar(500) DEFAULT '' COMMENT 'table description',
  `class_name` varchar(100) DEFAULT '' COMMENT 'entity class name',
  `tpl_category` varchar(200) DEFAULT 'crud' COMMENT 'template used（crud single table operation & tree table operation）',
  `package_name` varchar(100) DEFAULT NULL COMMENT 'generate package path',
  `module_name` varchar(30) DEFAULT NULL COMMENT 'generate module name',
  `business_name` varchar(30) DEFAULT NULL COMMENT 'generate business name',
  `function_name` varchar(50) DEFAULT NULL COMMENT 'generate function name',
  `function_author` varchar(50) DEFAULT NULL COMMENT 'generate function author',
  `options` varchar(1000) DEFAULT NULL COMMENT 'other build options',
  `create_by` varchar(64) DEFAULT '' COMMENT 'creator',
  `create_time` datetime DEFAULT NULL COMMENT 'date created',
  `update_by` varchar(64) DEFAULT '' COMMENT 'updater',
  `update_time` datetime DEFAULT NULL COMMENT 'date modified',
  `remark` varchar(500) DEFAULT NULL COMMENT 'remarks',
  PRIMARY KEY (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Code generation business table';

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column` (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'serial number',
  `table_id` varchar(64) DEFAULT NULL COMMENT 'attribution table number',
  `column_name` varchar(200) DEFAULT NULL COMMENT 'column name',
  `column_comment` varchar(500) DEFAULT NULL COMMENT 'column description',
  `column_type` varchar(100) DEFAULT NULL COMMENT 'column type',
  `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA type',
  `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA field name',
  `is_pk` char(1) DEFAULT NULL COMMENT 'primary key（is 1）',
  `is_increment` char(1) DEFAULT NULL COMMENT 'whether to increase（is 1）',
  `is_required` char(1) DEFAULT NULL COMMENT 'is it required（is 1）',
  `is_insert` char(1) DEFAULT NULL COMMENT 'is it an insert field（is 1）',
  `is_edit` char(1) DEFAULT NULL COMMENT 'whether to edit the field（is 1）',
  `is_list` char(1) DEFAULT NULL COMMENT 'list field（is 1）',
  `is_query` char(1) DEFAULT NULL COMMENT 'whether to query the field（is 1）',
  `query_type` varchar(200) DEFAULT '=' COMMENT 'query method (equal to, not equal to, greater than, less than, range）',
  `html_type` varchar(200) DEFAULT NULL COMMENT 'display type (text box, text field, drop-down box, check box, radio button, date control)',
  `dict_type` varchar(200) DEFAULT '' COMMENT 'dictionary type',
  `sort` int(11) DEFAULT NULL COMMENT 'sort',
  `create_by` varchar(64) DEFAULT '' COMMENT 'creator',
  `create_time` datetime DEFAULT NULL COMMENT 'date created',
  `update_by` varchar(64) DEFAULT '' COMMENT 'modifier',
  `update_time` datetime DEFAULT NULL COMMENT 'date modified',
  PRIMARY KEY (`column_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='code generation business table fields';

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `config_id` int(5) NOT NULL AUTO_INCREMENT COMMENT 'parameter primary key',
  `config_name` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'parameter name',
  `config_key` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'parameter key name',
  `config_value` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'parameter key value',
  `config_type` char(1) COLLATE utf8mb4_bin DEFAULT 'N' COMMENT 'built-in system (Y yes, N no)',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'creator',
  `create_time` datetime DEFAULT NULL COMMENT 'date created',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'modifier',
  `update_time` datetime DEFAULT NULL COMMENT 'date modified',
  `remark` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'remarks',
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='parameter configuration table';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
BEGIN;
INSERT INTO `sys_config` VALUES (1, 'main frame page-default skin style name', 'sys.index.skinName', 'skin-random', 'Y', 'crown', '2018-03-16 11:33:00', NULL, '2019-07-23 17:35:13', '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow、随机 skin-random');
INSERT INTO `sys_config` VALUES (2, 'User Management-Account Initial Password', 'sys.user.initPassword', '123456', 'Y', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '初始化密码 123456');
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'department ID',
  `parent_id` bigint(20) DEFAULT '0' COMMENT 'parent department ID',
  `ancestors` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'list of parent departments',
  `dept_name` varchar(30) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'department name',
  `order_num` int(4) DEFAULT '0' COMMENT 'display order',
  `leader` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'person in charge',
  `phone` varchar(11) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'phone number',
  `email` varchar(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'email address',
  `status` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT 'department status (0 normal, 1 disabled)',
  `deleted` bit(1) DEFAULT NULL COMMENT 'delete flag (0 remaining, 1 deleted)',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'creator',
  `create_time` datetime DEFAULT NULL COMMENT 'date created',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'modifier',
  `update_time` datetime DEFAULT NULL COMMENT 'date modified',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='department table';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES (100, 0, '0', 'crown technology', 0, 'crown', '15888888888', 'crown@qq.com', '0', b'0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES (101, 100, '0,100', 'Shenzhen headquarter', 1, 'crown', '15888888888', 'crown@qq.com', '0', b'0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES (102, 100, '0,100', 'Changsha branch', 2, 'crown', '15888888888', 'crown@qq.com', '0', b'0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES (103, 101, '0,100,101', 'R&D department', 1, 'crown', '15888888888', 'crown@qq.com', '0', b'0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES (104, 101, '0,100,101', 'marketing department', 2, 'crown', '15888888888', 'crown@qq.com', '0', b'0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES (105, 101, '0,100,101', 'testing department', 3, 'crown', '15888888888', 'crown@qq.com', '0', b'0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES (106, 101, '0,100,101', 'financial department', 4, 'crown', '15888888888', 'crown@qq.com', '0', b'0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES (107, 101, '0,100,101', 'operation and maintenance department', 5, 'crown', '15888888888', 'crown@qq.com', '0', b'0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES (108, 102, '0,100,102', 'marketing department', 1, 'crown', '15888888888', 'crown@qq.com', '0', b'0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00');
INSERT INTO `sys_dept` VALUES (109, 102, '0,100,102', 'financial department', 2, 'crown', '15888888888', 'crown@qq.com', '0', b'0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'dictionary code',
  `dict_sort` int(4) DEFAULT '0' COMMENT 'dictionary sort',
  `dict_label` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'dictionary label',
  `dict_value` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'dictionary value',
  `dict_type` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'dictionary type',
  `css_class` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'style attributes (other style extensions)',
  `list_class` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'table echo style',
  `is_default` char(1) COLLATE utf8mb4_bin DEFAULT 'N' COMMENT 'whether is default (Y yes, N no)',
  `status` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT 'status (0 normal, 1 disabled)',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'creator',
  `create_time` datetime DEFAULT NULL COMMENT 'date created',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'modifier',
  `update_time` datetime DEFAULT NULL COMMENT 'date modified',
  `remark` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'remarks',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='dictionary data table';

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_data` VALUES (1, 1, 'male', '0', 'sys_user_sex', '', '', 'Y', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'Sex: Male');
INSERT INTO `sys_dict_data` VALUES (2, 2, 'female', '1', 'sys_user_sex', '', '', 'N', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'Sex: Female');
INSERT INTO `sys_dict_data` VALUES (3, 3, 'unknown', '2', 'sys_user_sex', '', '', 'N', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'Sex: Unknown');
INSERT INTO `sys_dict_data` VALUES (4, 1, 'display', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'show menu');
INSERT INTO `sys_dict_data` VALUES (5, 2, 'hide', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'hide menu');
INSERT INTO `sys_dict_data` VALUES (6, 1, 'normal', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'normal status');
INSERT INTO `sys_dict_data` VALUES (7, 2, 'deactivate', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'disabled state');
INSERT INTO `sys_dict_data` VALUES (8, 1, 'normal', 'false', 'sys_job_paused', '', 'primary', 'Y', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'normal status');
INSERT INTO `sys_dict_data` VALUES (9, 2, 'time out', 'true', 'sys_job_paused', '', 'danger', 'N', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'disabled state');
INSERT INTO `sys_dict_data` VALUES (10, 1, 'yes', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'default system');
INSERT INTO `sys_dict_data` VALUES (11, 2, 'no', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'not default system');
INSERT INTO `sys_dict_data` VALUES (12, 1, 'notice', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'notice');
INSERT INTO `sys_dict_data` VALUES (13, 2, 'announcement', '2', 'sys_notice_type', '', 'success', 'N', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'announcement');
INSERT INTO `sys_dict_data` VALUES (14, 1, 'normal', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'normal status');
INSERT INTO `sys_dict_data` VALUES (15, 2, 'shut down', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'disabled');
INSERT INTO `sys_dict_data` VALUES (16, 1, 'add', '1', 'sys_oper_type', '', 'info', 'N', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'new operation');
INSERT INTO `sys_dict_data` VALUES (17, 2, 'modify', '2', 'sys_oper_type', '', 'info', 'N', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'modify operation');
INSERT INTO `sys_dict_data` VALUES (18, 3, 'delete', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'delete operation');
INSERT INTO `sys_dict_data` VALUES (19, 4, 'authorization', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'authorize operation');
INSERT INTO `sys_dict_data` VALUES (20, 5, 'export', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'export operation');
INSERT INTO `sys_dict_data` VALUES (21, 6, 'import', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'import operation');
INSERT INTO `sys_dict_data` VALUES (22, 7, 'retreat', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'force back operation');
INSERT INTO `sys_dict_data` VALUES (23, 8, 'generate code', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'generate operation');
INSERT INTO `sys_dict_data` VALUES (24, 9, 'clear data', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'empty operation');
INSERT INTO `sys_dict_data` VALUES (25, 1, 'success', '1', 'sys_common_status', '', 'primary', 'N', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'normal status');
INSERT INTO `sys_dict_data` VALUES (26, 2, 'fail', '0', 'sys_common_status', '', 'danger', 'N', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'disabled state');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'dictionary primary key',
  `dict_name` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'dictionary name',
  `dict_type` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'Dictionary type',
  `status` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT 'Status (0 normal, 1 disabled)',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'creator',
  `create_time` datetime DEFAULT NULL COMMENT 'date created',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'modifier',
  `update_time` datetime DEFAULT NULL COMMENT 'date modified',
  `remark` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'remarks',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='dictionary type table';

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_type` VALUES (1, 'user gender', 'sys_user_sex', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'user gender list');
INSERT INTO `sys_dict_type` VALUES (2, 'menu status', 'sys_show_hide', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'menu status list');
INSERT INTO `sys_dict_type` VALUES (3, 'system switch', 'sys_normal_disable', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'system switch list');
INSERT INTO `sys_dict_type` VALUES (4, 'task status', 'sys_job_status', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'task status list');
INSERT INTO `sys_dict_type` VALUES (5, 'whether the system', 'sys_yes_no', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'is listed ?');
INSERT INTO `sys_dict_type` VALUES (6, 'notification type', 'sys_notice_type', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'list of notification types');
INSERT INTO `sys_dict_type` VALUES (7, 'notification status', 'sys_notice_status', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'notification status list');
INSERT INTO `sys_dict_type` VALUES (8, 'operation type', 'sys_oper_type', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'list of operation types');
INSERT INTO `sys_dict_type` VALUES (9, 'system status', 'sys_common_status', '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'login status list');
COMMIT;

-- ----------------------------
-- Table structure for sys_exce_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_exce_log`;
CREATE TABLE `sys_exce_log` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) COLLATE utf8mb4_bin NOT NULL COMMENT 'request path',
  `oper_name` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'current operator',
  `action_method` varchar(255) COLLATE utf8mb4_bin NOT NULL DEFAULT '-' COMMENT 'action method',
  `run_time` varchar(20) COLLATE utf8mb4_bin NOT NULL COMMENT 'interface running time unit: ms',
  `ip_addr` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT 'IP address',
  `content` longtext COLLATE utf8mb4_bin NOT NULL COMMENT 'log details',
  `create_time` datetime NOT NULL COMMENT 'time created',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='exception log table';

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
  `job_id` bigint(20) NOT NULL,
  `job_name` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT 'job name',
  `class_name` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT 'class name',
  `job_params` varchar(500) COLLATE utf8mb4_bin DEFAULT '{}' COMMENT 'parameter',
  `cron` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT 'cron expression',
  `paused` bit(1) NOT NULL COMMENT 'whether to start',
  `remark` varchar(128) COLLATE utf8mb4_bin NOT NULL COMMENT 'remarks',
  `update_time` datetime NOT NULL COMMENT 'time modified',
  `create_time` datetime NOT NULL COMMENT 'time created',
  `deleted` bit(1) DEFAULT NULL COMMENT 'delete flag (0 remaining, 1 deleted)',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='timed task';

-- ----------------------------
-- Records of sys_job
-- ----------------------------
BEGIN;
INSERT INTO `sys_job` VALUES (1, 'Welcome to Crown', 'org.crown.project.monitor.quartz.task.SystemOutWelcomeTask', '{}', '0 0/5 * * * ?', b'0', 'Welcome to Crown', '2019-09-10 18:10:00', '2019-05-29 15:40:41', b'0', 'crown', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log` (
  `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `class_name` varchar(128) NOT NULL COMMENT 'class name',
  `create_time` datetime NOT NULL COMMENT 'date created',
  `cron` varchar(128) NOT NULL COMMENT 'cron expression',
  `exception` text COMMENT 'exception information',
  `status` smallint(1) NOT NULL COMMENT 'execution status (1 successful, 0 failed)',
  `job_name` varchar(255) NOT NULL COMMENT 'job name',
  `job_params` varchar(500) DEFAULT NULL COMMENT 'parameter',
  `run_time` varchar(32) NOT NULL COMMENT 'operation hours',
  PRIMARY KEY (`job_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='scheduled task log';

-- ----------------------------
-- Table structure for sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `sys_logininfor`;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'access ID',
  `login_name` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'login account',
  `ipaddr` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'login IP address',
  `login_location` varchar(255) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'login location',
  `browser` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'browser type',
  `os` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'operating system',
  `status` smallint(1) DEFAULT '1' COMMENT 'login status (1 successful, 0 failed)',
  `msg` varchar(255) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'prompt message',
  `login_time` datetime DEFAULT NULL COMMENT 'interview time',
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='system access record';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'menu ID',
  `menu_name` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT 'menu name',
  `parent_id` bigint(20) DEFAULT '0' COMMENT 'parent menu ID',
  `order_num` int(4) DEFAULT '0' COMMENT 'display order',
  `url` varchar(200) COLLATE utf8mb4_bin DEFAULT '#' COMMENT 'request address',
  `target` varchar(20) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'open method (menuItem tab, menu blank new window)',
  `menu_type` char(1) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'menu type (M directory, C menu, F button)',
  `visible` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT 'menu status (0 display, 1 hidden)',
  `perms` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'authority ID',
  `icon` varchar(100) COLLATE utf8mb4_bin DEFAULT '#' COMMENT 'menu icon',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'creator',
  `create_time` datetime DEFAULT NULL COMMENT 'time created',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'modifier',
  `update_time` datetime DEFAULT NULL COMMENT 'time modified',
  `remark` varchar(500) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'remarks',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1063 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='menu permission table';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, 'system management', 0, 1, '#', '', 'M', '0', '', 'fa fa-gear', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'System Management Directory');
INSERT INTO `sys_menu` VALUES (2, 'system monitoring', 0, 2, '#', '', 'M', '0', '', 'fa fa-video-camera', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'System Monitoring Directory');
INSERT INTO `sys_menu` VALUES (3, 'system tools', 0, 3, '#', '', 'M', '0', '', 'fa fa-bars', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'System tool Catalog');
INSERT INTO `sys_menu` VALUES (100, 'user management', 1, 1, '/system/user', '', 'C', '0', 'system:user:view', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'User Management Menu');
INSERT INTO `sys_menu` VALUES (101, 'role management', 1, 2, '/system/role', '', 'C', '0', 'system:role:view', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'Role Management Menu');
INSERT INTO `sys_menu` VALUES (102, 'menu management', 1, 3, '/system/menu', '', 'C', '0', 'system:menu:view', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'Menu Management');
INSERT INTO `sys_menu` VALUES (103, 'department management', 1, 4, '/system/dept', '', 'C', '0', 'system:dept:view', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'Department Management Menu');
INSERT INTO `sys_menu` VALUES (104, 'job management', 1, 5, '/system/post', '', 'C', '0', 'system:post:view', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'Post Management Menu');
INSERT INTO `sys_menu` VALUES (105, 'dictionary management', 1, 6, '/system/dict', '', 'C', '0', 'system:dict:view', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'Dictionary Management Menu');
INSERT INTO `sys_menu` VALUES (106, 'parameter settings', 1, 7, '/system/config', '', 'C', '0', 'system:config:view', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'Parameter Setting Menu');
INSERT INTO `sys_menu` VALUES (107, 'announcement', 1, 8, '/system/notice', '', 'C', '0', 'system:notice:view', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'Notification Announcement Menu');
INSERT INTO `sys_menu` VALUES (108, 'log management', 1, 9, '#', '', 'M', '0', '', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'Log Management Menu');
INSERT INTO `sys_menu` VALUES (109, 'online user', 2, 1, '/monitor/online', '', 'C', '0', 'monitor:online:view', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'Online User Menu');
INSERT INTO `sys_menu` VALUES (110, 'timed task', 2, 2, '/monitor/job', '', 'C', '0', 'monitor:job:view', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'Timed Task Menu');
INSERT INTO `sys_menu` VALUES (112, 'service monitoring', 2, 3, '/monitor/server', '', 'C', '0', 'monitor:server:view', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'Service Monitoring Menu');
INSERT INTO `sys_menu` VALUES (113, 'form construction', 3, 1, '/tool/build', '', 'C', '0', 'tool:build:view', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'Form Build Menu');
INSERT INTO `sys_menu` VALUES (114, 'code generation', 3, 2, '/tool/gen', '', 'C', '0', 'tool:gen:view', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'Code Generation Menu');
INSERT INTO `sys_menu` VALUES (115, 'system interface', 3, 3, '/tool/swagger', '', 'C', '0', 'tool:swagger:view', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'System Interface Menu');
INSERT INTO `sys_menu` VALUES (500, 'operation log', 108, 1, '/monitor/operlog', '', 'C', '0', 'monitor:operlog:view', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'Operation Log Menu');
INSERT INTO `sys_menu` VALUES (501, 'login log', 108, 2, '/monitor/logininfor', '', 'C', '0', 'monitor:logininfor:view', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', 'Login Menu');
INSERT INTO `sys_menu` VALUES (1000, 'user query', 100, 1, '#', '', 'F', '0', 'system:user:list', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1001, 'user added', 100, 2, '#', '', 'F', '0', 'system:user:add', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1002, 'user modification', 100, 3, '#', '', 'F', '0', 'system:user:edit', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1003, 'user delete', 100, 4, '#', '', 'F', '0', 'system:user:remove', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1004, 'user export', 100, 5, '#', '', 'F', '0', 'system:user:export', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1005, 'user import', 100, 6, '#', '', 'F', '0', 'system:user:import', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1006, 'reset password', 100, 7, '#', '', 'F', '0', 'system:user:resetPwd', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1007, 'role query', 101, 1, '#', '', 'F', '0', 'system:role:list', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1008, 'new role', 101, 2, '#', '', 'F', '0', 'system:role:add', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1009, 'role modification', 101, 3, '#', '', 'F', '0', 'system:role:edit', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1010, 'role deletion', 101, 4, '#', '', 'F', '0', 'system:role:remove', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1011, 'role export', 101, 5, '#', '', 'F', '0', 'system:role:export', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1012, 'role import', 102, 1, '#', '', 'F', '0', 'system:menu:list', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1013, 'new menu', 102, 2, '#', '', 'F', '0', 'system:menu:add', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1014, 'menu modification', 102, 3, '#', '', 'F', '0', 'system:menu:edit', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1015, 'menu delete', 102, 4, '#', '', 'F', '0', 'system:menu:remove', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1016, 'department query', 103, 1, '#', '', 'F', '0', 'system:dept:list', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1017, 'new department', 103, 2, '#', '', 'F', '0', 'system:dept:add', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1018, 'department modification', 103, 3, '#', '', 'F', '0', 'system:dept:edit', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1019, 'department delete', 103, 4, '#', '', 'F', '0', 'system:dept:remove', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1020, 'job inquiry', 104, 1, '#', '', 'F', '0', 'system:post:list', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1021, 'new job', 104, 2, '#', '', 'F', '0', 'system:post:add', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1022, 'post modification', 104, 3, '#', '', 'F', '0', 'system:post:edit', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1023, 'post deletion', 104, 4, '#', '', 'F', '0', 'system:post:remove', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1024, 'post export', 104, 5, '#', '', 'F', '0', 'system:post:export', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1025, 'dictionary lookup', 105, 1, '#', '', 'F', '0', 'system:dict:list', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1026, 'new dictionary', 105, 2, '#', '', 'F', '0', 'system:dict:add', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1027, 'dictionary modification', 105, 3, '#', '', 'F', '0', 'system:dict:edit', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1028, 'dictionary delete', 105, 4, '#', '', 'F', '0', 'system:dict:remove', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1029, 'dictionary export', 105, 5, '#', '', 'F', '0', 'system:dict:export', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1030, 'parameter query', 106, 1, '#', '', 'F', '0', 'system:config:list', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1031, 'add parameter', 106, 2, '#', '', 'F', '0', 'system:config:add', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1032, 'parameter modification', 106, 3, '#', '', 'F', '0', 'system:config:edit', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1033, 'parameter deletion', 106, 4, '#', '', 'F', '0', 'system:config:remove', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1034, 'parameter export', 106, 5, '#', '', 'F', '0', 'system:config:export', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1035, 'announcement query', 107, 1, '#', '', 'F', '0', 'system:notice:list', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1036, 'announcement new', 107, 2, '#', '', 'F', '0', 'system:notice:add', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1037, 'announcement modification', 107, 3, '#', '', 'F', '0', 'system:notice:edit', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1038, 'announcement delete', 107, 4, '#', '', 'F', '0', 'system:notice:remove', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1039, 'operation query', 500, 1, '#', '', 'F', '0', 'monitor:operlog:list', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1040, 'operation delete', 500, 2, '#', '', 'F', '0', 'monitor:operlog:remove', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1041, 'details', 500, 3, '#', '', 'F', '0', 'monitor:operlog:detail', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1042, 'log export', 500, 4, '#', '', 'F', '0', 'monitor:operlog:export', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1043, 'login query', 501, 1, '#', '', 'F', '0', 'monitor:logininfor:list', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1044, 'login delete', 501, 2, '#', '', 'F', '0', 'monitor:logininfor:remove', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1045, 'log export', 501, 3, '#', '', 'F', '0', 'monitor:logininfor:export', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1046, 'online search', 109, 1, '#', '', 'F', '0', 'monitor:online:list', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1047, 'batch retreat', 109, 2, '#', '', 'F', '0', 'monitor:online:batchForceLogout', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1048, 'single forced retreat', 109, 3, '#', '', 'F', '0', 'monitor:online:forceLogout', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1049, 'task query', 110, 1, '#', '', 'F', '0', 'monitor:job:list', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1050, 'new task', 110, 2, '#', '', 'F', '0', 'monitor:job:add', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1051, 'task modification', 110, 3, '#', '', 'F', '0', 'monitor:job:edit', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1052, 'task delete', 110, 4, '#', '', 'F', '0', 'monitor:job:remove', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1053, 'state modification', 110, 5, '#', '', 'F', '0', 'monitor:job:changeStatus', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1054, 'task details', 110, 6, '#', '', 'F', '0', 'monitor:job:detail', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1055, 'task export', 110, 7, '#', '', 'F', '0', 'monitor:job:export', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1056, 'generate query', 114, 1, '#', '', 'F', '0', 'tool:gen:list', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1057, 'generate code', 114, 2, '#', '', 'F', '0', 'tool:gen:code', '#', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_menu` VALUES (1058, 'instant log', 2, 4, '/monitor/consolelog', 'menuItem', 'C', '0', 'monitor:consolelog:view', '', 'crown', '2019-07-25 01:59:15', NULL, '2019-07-25 09:52:52', '');
INSERT INTO `sys_menu` VALUES (1059, 'exception log', 2, 1, '/monitor/exceLog', 'menuItem', 'C', '0', 'monitor:exceLog:view', '#', 'crown', '2018-06-28 00:00:00', NULL, '2019-07-27 14:48:54', 'Exception Log Menu');
INSERT INTO `sys_menu` VALUES (1060, 'exception log query', 1059, 1, '#', 'menuItem', 'F', '0', 'monitor:exceLog:list', '#', 'crown', '2018-06-28 00:00:00', NULL, '2019-07-27 14:49:10', '');
INSERT INTO `sys_menu` VALUES (1061, 'exception log deletion', 1059, 4, '#', 'menuItem', 'F', '0', 'monitor:exceLog:remove', '#', 'crown', '2018-06-28 00:00:00', NULL, '2019-07-27 14:49:22', '');
INSERT INTO `sys_menu` VALUES (1062, 'exception log details', 1059, 3, '', 'menuItem', 'F', '0', 'monitor:exceLog:detail', '', 'crown', '2019-07-27 16:48:20', NULL, '2019-07-27 16:48:48', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice` (
  `notice_id` int(4) NOT NULL AUTO_INCREMENT COMMENT 'announcement ID',
  `notice_title` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT 'announcement title',
  `notice_type` char(1) COLLATE utf8mb4_bin NOT NULL COMMENT 'announcement type (1 notification, 2 announcement)',
  `notice_content` varchar(2000) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'announcement content',
  `status` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT 'announcement status (0 normal, 1 closed)',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'creator',
  `create_time` datetime DEFAULT NULL COMMENT 'time created',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'modifier',
  `update_time` datetime DEFAULT NULL COMMENT 'time modified',
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'remarks',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='Notice Announcement Form';

-- ----------------------------
-- Records of sys_notice
-- ----------------------------
BEGIN;
INSERT INTO `sys_notice` VALUES (1, 'Reminder：2018-07-01 The new version of crown is released', '2', 'New version content', '0', 'crown', '2018-03-16 11:33:00', NULL, '2019-07-23 17:05:28', 'administrator');
COMMIT;

-- ----------------------------
-- Table structure for sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_oper_log`;
CREATE TABLE `sys_oper_log` (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'log primary key',
  `title` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'module title',
  `business_type` int(2) DEFAULT '0' COMMENT 'business type (0 other, 1 added, 2 modified, 3 deleted)',
  `method` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'method name',
  `operator_type` int(1) DEFAULT '0' COMMENT 'operation category (0 other, 1 background user, 2 mobile terminal user)',
  `oper_name` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'operator',
  `dept_name` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'department name',
  `oper_url` varchar(255) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'request URL',
  `oper_ip` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'host address',
  `oper_location` varchar(255) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'operating location',
  `oper_param` varchar(2000) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'request parameter',
  `status` smallint(1) DEFAULT '1' COMMENT 'operation status (1 normal, 0 abnormal)',
  `error_msg` varchar(2000) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'wrong information',
  `oper_time` datetime DEFAULT NULL COMMENT 'operating time',
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='operation log record';

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post` (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'post ID',
  `post_code` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT 'post code',
  `post_name` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT 'post title',
  `post_sort` int(4) NOT NULL COMMENT 'display order',
  `status` char(1) COLLATE utf8mb4_bin NOT NULL COMMENT 'status (0 normal, 1 disabled)',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'creator',
  `create_time` datetime DEFAULT NULL COMMENT 'time created',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'modifier',
  `update_time` datetime DEFAULT NULL COMMENT 'time modified',
  `remark` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'remarks',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='Job Information Form';

-- ----------------------------
-- Records of sys_post
-- ----------------------------
BEGIN;
INSERT INTO `sys_post` VALUES (1, 'ceo', 'Chairman of the board', 1, '0', 'crown', '2018-03-16 11:33:00', 'crown', '2019-08-28 19:19:29', '');
INSERT INTO `sys_post` VALUES (2, 'se', 'Project manager', 2, '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_post` VALUES (3, 'hr', 'Human resources', 3, '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
INSERT INTO `sys_post` VALUES (4, 'user', 'General staff', 4, '0', 'crown', '2018-03-16 11:33:00', 'crown', '2018-03-16 11:33:00', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'role ID',
  `role_name` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT 'role name',
  `role_key` varchar(100) COLLATE utf8mb4_bin NOT NULL COMMENT 'role permission string',
  `role_sort` int(4) NOT NULL COMMENT 'display order',
  `data_scope` char(1) COLLATE utf8mb4_bin DEFAULT '1' COMMENT 'data range (1 all data permissions, 2 customized data permissions, 3 data permissions for this department, 4 data permissions for this department and below)',
  `status` char(1) COLLATE utf8mb4_bin NOT NULL COMMENT 'role status (0 normal, 1 disabled)',
  `deleted` bit(1) DEFAULT NULL COMMENT 'delete flag (0 remaining, 1 deleted)',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'creator',
  `create_time` datetime DEFAULT NULL COMMENT 'time created',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'modifier',
  `update_time` datetime DEFAULT NULL COMMENT 'time modified',
  `remark` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'remarks',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='role information table';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, 'administrator', 'admin', 1, '1', '0', b'0', 'crown', '2018-03-16 11:33:00', NULL, '2019-07-23 17:15:23', 'administrator');
INSERT INTO `sys_role` VALUES (2, 'normal role', 'common', 2, '2', '0', b'0', 'crown', '2018-03-16 11:33:00', NULL, '2019-08-28 19:17:04', 'normal role');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_dept`;
CREATE TABLE `sys_role_dept` (
  `role_id` bigint(20) NOT NULL COMMENT 'role ID',
  `dept_id` bigint(20) NOT NULL COMMENT 'menu ID',
  PRIMARY KEY (`role_id`,`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='role and department association table';

-- ----------------------------
-- Records of sys_role_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_dept` VALUES (2, 100);
INSERT INTO `sys_role_dept` VALUES (2, 101);
INSERT INTO `sys_role_dept` VALUES (2, 105);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT 'role ID',
  `menu_id` bigint(20) NOT NULL COMMENT 'menu ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='role and menu association table';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 100);
INSERT INTO `sys_role_menu` VALUES (2, 101);
INSERT INTO `sys_role_menu` VALUES (2, 102);
INSERT INTO `sys_role_menu` VALUES (2, 103);
INSERT INTO `sys_role_menu` VALUES (2, 104);
INSERT INTO `sys_role_menu` VALUES (2, 105);
INSERT INTO `sys_role_menu` VALUES (2, 106);
INSERT INTO `sys_role_menu` VALUES (2, 107);
INSERT INTO `sys_role_menu` VALUES (2, 108);
INSERT INTO `sys_role_menu` VALUES (2, 109);
INSERT INTO `sys_role_menu` VALUES (2, 110);
INSERT INTO `sys_role_menu` VALUES (2, 112);
INSERT INTO `sys_role_menu` VALUES (2, 113);
INSERT INTO `sys_role_menu` VALUES (2, 114);
INSERT INTO `sys_role_menu` VALUES (2, 115);
INSERT INTO `sys_role_menu` VALUES (2, 500);
INSERT INTO `sys_role_menu` VALUES (2, 501);
INSERT INTO `sys_role_menu` VALUES (2, 1000);
INSERT INTO `sys_role_menu` VALUES (2, 1001);
INSERT INTO `sys_role_menu` VALUES (2, 1002);
INSERT INTO `sys_role_menu` VALUES (2, 1003);
INSERT INTO `sys_role_menu` VALUES (2, 1004);
INSERT INTO `sys_role_menu` VALUES (2, 1005);
INSERT INTO `sys_role_menu` VALUES (2, 1006);
INSERT INTO `sys_role_menu` VALUES (2, 1007);
INSERT INTO `sys_role_menu` VALUES (2, 1008);
INSERT INTO `sys_role_menu` VALUES (2, 1009);
INSERT INTO `sys_role_menu` VALUES (2, 1010);
INSERT INTO `sys_role_menu` VALUES (2, 1011);
INSERT INTO `sys_role_menu` VALUES (2, 1012);
INSERT INTO `sys_role_menu` VALUES (2, 1013);
INSERT INTO `sys_role_menu` VALUES (2, 1014);
INSERT INTO `sys_role_menu` VALUES (2, 1015);
INSERT INTO `sys_role_menu` VALUES (2, 1016);
INSERT INTO `sys_role_menu` VALUES (2, 1017);
INSERT INTO `sys_role_menu` VALUES (2, 1018);
INSERT INTO `sys_role_menu` VALUES (2, 1019);
INSERT INTO `sys_role_menu` VALUES (2, 1020);
INSERT INTO `sys_role_menu` VALUES (2, 1021);
INSERT INTO `sys_role_menu` VALUES (2, 1022);
INSERT INTO `sys_role_menu` VALUES (2, 1023);
INSERT INTO `sys_role_menu` VALUES (2, 1024);
INSERT INTO `sys_role_menu` VALUES (2, 1025);
INSERT INTO `sys_role_menu` VALUES (2, 1026);
INSERT INTO `sys_role_menu` VALUES (2, 1027);
INSERT INTO `sys_role_menu` VALUES (2, 1028);
INSERT INTO `sys_role_menu` VALUES (2, 1029);
INSERT INTO `sys_role_menu` VALUES (2, 1030);
INSERT INTO `sys_role_menu` VALUES (2, 1031);
INSERT INTO `sys_role_menu` VALUES (2, 1032);
INSERT INTO `sys_role_menu` VALUES (2, 1033);
INSERT INTO `sys_role_menu` VALUES (2, 1034);
INSERT INTO `sys_role_menu` VALUES (2, 1035);
INSERT INTO `sys_role_menu` VALUES (2, 1036);
INSERT INTO `sys_role_menu` VALUES (2, 1037);
INSERT INTO `sys_role_menu` VALUES (2, 1038);
INSERT INTO `sys_role_menu` VALUES (2, 1039);
INSERT INTO `sys_role_menu` VALUES (2, 1040);
INSERT INTO `sys_role_menu` VALUES (2, 1041);
INSERT INTO `sys_role_menu` VALUES (2, 1042);
INSERT INTO `sys_role_menu` VALUES (2, 1043);
INSERT INTO `sys_role_menu` VALUES (2, 1044);
INSERT INTO `sys_role_menu` VALUES (2, 1045);
INSERT INTO `sys_role_menu` VALUES (2, 1046);
INSERT INTO `sys_role_menu` VALUES (2, 1047);
INSERT INTO `sys_role_menu` VALUES (2, 1048);
INSERT INTO `sys_role_menu` VALUES (2, 1049);
INSERT INTO `sys_role_menu` VALUES (2, 1050);
INSERT INTO `sys_role_menu` VALUES (2, 1051);
INSERT INTO `sys_role_menu` VALUES (2, 1052);
INSERT INTO `sys_role_menu` VALUES (2, 1053);
INSERT INTO `sys_role_menu` VALUES (2, 1054);
INSERT INTO `sys_role_menu` VALUES (2, 1055);
INSERT INTO `sys_role_menu` VALUES (2, 1056);
INSERT INTO `sys_role_menu` VALUES (2, 1057);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'user ID',
  `dept_id` bigint(20) DEFAULT NULL COMMENT 'department ID',
  `login_name` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT 'login account',
  `user_name` varchar(30) COLLATE utf8mb4_bin NOT NULL COMMENT 'username',
  `user_type` varchar(2) COLLATE utf8mb4_bin DEFAULT '00' COMMENT 'user type (00 system user)',
  `email` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'user email address',
  `phonenumber` varchar(11) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'user phone number',
  `sex` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT 'user gender (0 male, 1 female, 2 unknown)',
  `avatar` varchar(100) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'avatar path',
  `password` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'password',
  `salt` varchar(20) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'salt encryption',
  `status` char(1) COLLATE utf8mb4_bin DEFAULT '0' COMMENT 'account status (0 normal, 1 disabled)',
  `deleted` bit(1) DEFAULT NULL COMMENT 'delete flag (0 remaining, 1 deleted)',
  `login_ip` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'last login IP',
  `login_date` datetime DEFAULT NULL COMMENT 'last login time',
  `create_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'creator',
  `create_time` datetime DEFAULT NULL COMMENT 'time created',
  `update_by` varchar(64) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'modifier',
  `update_time` datetime DEFAULT NULL COMMENT 'time modified',
  `remark` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'remarks',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='User Information Form';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 105, 'crown', 'Crown', '00', 'crown@qq.com', '15666666666', '1', '', '6bb801aa715c8d25d51b781577f2129e', '9a45e9', '0', b'0', '0:0:0:0:0:0:0:1', '2019-09-10 18:11:22', 'crown', '2018-03-16 11:33:00', 'crown', '2019-09-10 18:11:22', 'administrator');
INSERT INTO `sys_user` VALUES (2, 103, 'admin', 'Admin', '00', 'crown@163.com', '15888888888', '1', '', '29c67a30398638269fe600f73a054934', '111111', '0', b'0', '0:0:0:0:0:0:0:1', '2019-08-05 17:34:11', 'crown', '2018-03-16 11:33:00', 'crown', '2019-08-05 17:34:11', 'test account');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_online
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_online`;
CREATE TABLE `sys_user_online` (
  `sessionId` varchar(50) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT 'user session ID',
  `login_name` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'login account',
  `dept_name` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'department name',
  `ipaddr` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'login IP address',
  `login_location` varchar(255) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'login location',
  `browser` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'browser type',
  `os` varchar(50) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'operating system',
  `status` varchar(10) COLLATE utf8mb4_bin DEFAULT '' COMMENT 'online status: online or offline',
  `start_timestamp` datetime DEFAULT NULL COMMENT 'session creation time',
  `last_access_time` datetime DEFAULT NULL COMMENT 'session last access time',
  `expire_time` int(5) DEFAULT '0' COMMENT 'timeout period, in minutes',
  PRIMARY KEY (`sessionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='online user records';

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post` (
  `user_id` bigint(20) NOT NULL COMMENT 'user ID',
  `post_id` bigint(20) NOT NULL COMMENT 'post ID',
  PRIMARY KEY (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='user and post association table';

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_post` VALUES (1, 1);
INSERT INTO `sys_user_post` VALUES (2, 2);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT 'user ID',
  `role_id` bigint(20) NOT NULL COMMENT 'role ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='user and role association table';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
