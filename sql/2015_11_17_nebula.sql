/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50173
 Source Host           : localhost
 Source Database       : nebula

 Target Server Type    : MySQL
 Target Server Version : 50173
 File Encoding         : utf-8

 Date: 11/17/2015 16:08:31 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `acl_permission`
-- ----------------------------
DROP TABLE IF EXISTS `acl_permission`;
CREATE TABLE `acl_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(100) DEFAULT NULL COMMENT '权限名称',
  `permission_cname` varchar(100) DEFAULT NULL,
  `permission_desc` varchar(255) DEFAULT NULL COMMENT '权限描述',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限',
  `permission_type` varchar(50) DEFAULT NULL COMMENT '类型',
  `url` varchar(255) DEFAULT NULL COMMENT 'url',
  `pid` int(11) DEFAULT NULL COMMENT '父id',
  `pids` varchar(100) DEFAULT NULL COMMENT '层级关系',
  `is_enable` int(5) DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `acl_role`
-- ----------------------------
DROP TABLE IF EXISTS `acl_role`;
CREATE TABLE `acl_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `role_cname` varchar(100) DEFAULT NULL COMMENT '角色中文名',
  `role_desc` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `is_enable` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `acl_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `acl_role_permission`;
CREATE TABLE `acl_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `acl_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `acl_user_role`;
CREATE TABLE `acl_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_id` int(11) DEFAULT NULL COMMENT '工号',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `nebula_publish_app`
-- ----------------------------
DROP TABLE IF EXISTS `nebula_publish_app`;
CREATE TABLE `nebula_publish_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `publish_event_id` int(11) DEFAULT NULL COMMENT '发布事件id',
  `publish_module_id` int(11) DEFAULT NULL COMMENT '发布模块id',
  `publish_app_name` varchar(50) DEFAULT NULL COMMENT '发布工程',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_module_id_app_name` (`publish_module_id`,`publish_app_name`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `nebula_publish_app`
-- ----------------------------
BEGIN;
INSERT INTO `nebula_publish_app` VALUES ('1', '6', '1', 'priceservice'), ('2', '6', '1', 'schedulerboss'), ('3', '6', '1', 'serviceportal'), ('4', '6', '2', 'ebooking'), ('5', '10', '3', 'arsenal-web'), ('6', '10', '3', 'arsenal-api');
COMMIT;

-- ----------------------------
--  Table structure for `nebula_publish_base`
-- ----------------------------
DROP TABLE IF EXISTS `nebula_publish_base`;
CREATE TABLE `nebula_publish_base` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `publish_event_id` int(11) DEFAULT NULL COMMENT '发布事件id',
  `publish_product_env` varchar(50) DEFAULT NULL COMMENT '发布环境',
  `publish_product_name` varchar(100) DEFAULT NULL COMMENT '发布产品',
  `publish_module_name` varchar(100) DEFAULT NULL COMMENT '发布模块',
  `publish_module_key` varchar(255) DEFAULT NULL COMMENT '发布模块key',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `nebula_publish_base`
-- ----------------------------
BEGIN;
INSERT INTO `nebula_publish_base` VALUES ('1', '1', 'test', 'yjt2014', 'ebooking', 'test.yjt2014.ebooking.20151111.1111111'), ('2', '2', 'test', 'yjt2014', 'ebooking', 'test.yjt2015.ebooking.201511222.222222');
COMMIT;

-- ----------------------------
--  Table structure for `nebula_publish_event`
-- ----------------------------
DROP TABLE IF EXISTS `nebula_publish_event`;
CREATE TABLE `nebula_publish_event` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '发布事件id',
  `publish_subject` varchar(100) DEFAULT NULL COMMENT '标题',
  `publish_bu_name` varchar(50) DEFAULT NULL COMMENT '事业部名称',
  `publish_bu_cname` varchar(50) DEFAULT NULL COMMENT '事业部中文名称',
  `publish_product_name` varchar(50) DEFAULT NULL COMMENT '发布产品英文名称',
  `publish_product_cname` varchar(50) DEFAULT NULL COMMENT '发布产品中文名称',
  `publish_env` varchar(50) DEFAULT NULL COMMENT '发布环境，test/stage/product',
  `publish_svn` varchar(255) DEFAULT NULL COMMENT '发布提交的svn地址',
  `product_src_svn` varchar(255) DEFAULT NULL COMMENT '产品源svn地址',
  `publish_product_key` varchar(255) DEFAULT NULL COMMENT '发布产品key，test.产品.20151012.130305',
  `publish_datetime` datetime DEFAULT NULL COMMENT '发布时间',
  `submit_datetime` datetime DEFAULT NULL COMMENT '提交的时间',
  `submit_emp_id` int(10) DEFAULT NULL COMMENT '发布事件提交人',
  `is_success_publish` int(11) DEFAULT NULL COMMENT '发布是否成功，0/1  失败/成功',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `nebula_publish_event`
-- ----------------------------
BEGIN;
INSERT INTO `nebula_publish_event` VALUES ('1', '123', 'group', null, 'group_official_web', null, null, null, null, 'null.group_official_web.20151110.212418', null, '2015-11-10 21:24:18', null, null), ('2', '123', 'group', null, 'group_official_web', null, null, null, null, 'null.group_official_web.20151110.212547', null, '2015-11-10 21:25:47', null, null), ('3', '测试', 'gm', 'GM', 'booking_space', '订舱通', null, null, null, 'null.booking_space.20151111.090841', null, '2015-11-11 09:08:41', null, null), ('4', '123', 'gm', 'GM', 'booking_space', '订舱通', null, null, null, 'null.booking_space.20151111.091706', null, '2015-11-11 09:17:06', null, null), ('5', '123', 'pm', 'PM', 'cargo', '咖狗网', 'test', 'svn://172.16.137.150/war/', 'svn://172.16.137.150/yjt2014/', 'test.cargo.20151111.091728', null, '2015-11-11 09:17:28', null, null), ('6', '新的gm发布事件单', 'gm', 'GM', 'yjt2014', '运价通2014', 'test', 'svn://172.16.137.150/war/', 'svn://172.16.137.150/yjt2014/', 'test.yjt2014.20151111.132208', null, '2015-11-11 13:22:08', null, null), ('7', 'test1', null, '请选择', null, '请选择', '请选择', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', null, '请选择.null.20151116.162333', null, '2015-11-16 16:23:33', null, null), ('8', 'test1', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', null, 'test.ywpt.20151116.163259', null, '2015-11-16 16:32:59', null, null), ('9', '测试', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', null, 'test.ywpt.20151116.165830', null, '2015-11-16 16:58:30', null, null), ('10', '123', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', null, 'test.ywpt.20151116.170949', null, '2015-11-16 17:09:49', null, null), ('11', '123', 'group', '集团', 'ywpt', '运维平台', 'test', '123', null, 'test.ywpt.20151116.171225', null, '2015-11-16 17:12:26', null, null), ('12', '123', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', 'svn://10.162.21.208/group/ywpt', 'test.ywpt.20151116.172153', null, '2015-11-16 17:21:53', null, null);
COMMIT;

-- ----------------------------
--  Table structure for `nebula_publish_event_log`
-- ----------------------------
DROP TABLE IF EXISTS `nebula_publish_event_log`;
CREATE TABLE `nebula_publish_event_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `publish_event_id` int(11) DEFAULT NULL COMMENT '发布事件id',
  `log_action` varchar(20) DEFAULT NULL COMMENT '动作，create/delete/update',
  `log_info` varchar(255) DEFAULT NULL COMMENT '日志信息',
  `log_datetime` datetime DEFAULT NULL COMMENT '日志时间',
  `opt_emp_id` int(10) DEFAULT NULL COMMENT '操作者工号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `nebula_publish_host`
-- ----------------------------
DROP TABLE IF EXISTS `nebula_publish_host`;
CREATE TABLE `nebula_publish_host` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `publish_event_id` int(11) DEFAULT NULL COMMENT '发布事件id',
  `publish_module_id` int(11) DEFAULT NULL COMMENT '发布模块id',
  `pass_publish_host_name` varchar(100) DEFAULT NULL COMMENT '被发布机名称',
  `pass_publish_host_ip` varchar(50) DEFAULT NULL COMMENT '被发布机ip',
  `action_name` varchar(100) DEFAULT NULL COMMENT '动作名称',
  `action_group` varchar(100) DEFAULT NULL COMMENT '动作组',
  `is_success_action` int(5) DEFAULT NULL COMMENT '是否成功',
  `action_result` text COMMENT '动作执行结果',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_module_id_ip` (`publish_module_id`,`pass_publish_host_ip`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `nebula_publish_host`
-- ----------------------------
BEGIN;
INSERT INTO `nebula_publish_host` VALUES ('1', '6', '1', 'iZ23l8fa2cjZ', '10.161.218.194', null, null, null, null), ('2', '6', '1', 'iZ23t7vmj2yZ', '10.117.20.185', null, null, null, null), ('3', '6', '2', 'iZ23vlct7vgZ', '10.171.212.6', null, null, null, null), ('4', '10', '3', 'YW_saltminion01', '10.160.50.183', null, null, null, null), ('5', '10', '3', 'YW_saltminion02', '10.162.61.152', null, null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `nebula_publish_module`
-- ----------------------------
DROP TABLE IF EXISTS `nebula_publish_module`;
CREATE TABLE `nebula_publish_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `publish_event_id` int(11) DEFAULT NULL COMMENT '发布事件id',
  `publish_product_name` varchar(100) DEFAULT NULL COMMENT '发布产品',
  `publish_module_name` varchar(100) DEFAULT NULL COMMENT '发布模块',
  `publish_module_key` varchar(255) DEFAULT NULL COMMENT '发布模块key，test.产品.模块.20151012.130305，系统生成，此key作为发布目录',
  `module_src_svn` varchar(255) DEFAULT NULL COMMENT '模块 源svn地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `nebula_publish_module`
-- ----------------------------
BEGIN;
INSERT INTO `nebula_publish_module` VALUES ('1', '6', 'yjt2014', 'yjt2014_m', 'test.yjt2014.yjt2014_m.20151111.132208', '/gm/yjt2014_m'), ('2', '6', 'yjt2014', 'ebooking', 'test.yjt2014.ebooking.20151111.132208', 'ebooking'), ('3', '10', 'ywpt', 'publish', 'test.ywpt.publish.20151116.170949', 'publish');
COMMIT;

-- ----------------------------
--  Table structure for `nebula_publish_schedule`
-- ----------------------------
DROP TABLE IF EXISTS `nebula_publish_schedule`;
CREATE TABLE `nebula_publish_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `publish_event_id` int(11) DEFAULT NULL COMMENT '发布事件id',
  `publish_action` varchar(100) DEFAULT NULL COMMENT '发布动作',
  `publish_action_group` varchar(50) DEFAULT NULL COMMENT 'action组',
  `is_success_action` int(255) DEFAULT NULL COMMENT '动作是否成功，0/1  失败/成功',
  `error_msg` longtext COMMENT '错误信息，如果不成功，返回不成功的msg',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `nebula_publish_schedule`
-- ----------------------------
BEGIN;
INSERT INTO `nebula_publish_schedule` VALUES ('1', '6', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('73', '6', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('74', '6', 'ANALYZE_PROJECT', 'PRE_MASTER', '1', ''), ('75', '6', 'GET_SRC_SVN', 'PRE_MASTER', '1', ''), ('76', '6', 'UPDATE_ETC', 'PRE_MASTER', null, ''), ('77', '7', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('78', '8', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('79', '9', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('80', '9', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('81', '9', 'ANALYZE_PROJECT', 'PRE_MASTER', '0', 'error message'), ('82', '10', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('83', '10', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('84', '10', 'ANALYZE_PROJECT', 'PRE_MASTER', '1', ''), ('85', '10', 'GET_SRC_SVN', 'PRE_MASTER', '0', 'error message'), ('86', '11', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('87', '12', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('96', '12', 'GET_PUBLISH_SVN', 'PRE_MASTER', '0', 'error message');
COMMIT;

-- ----------------------------
--  Table structure for `nebula_publish_sequence`
-- ----------------------------
DROP TABLE IF EXISTS `nebula_publish_sequence`;
CREATE TABLE `nebula_publish_sequence` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action_name` varchar(50) NOT NULL COMMENT '发布动作名称',
  `action_desc` varchar(50) DEFAULT NULL COMMENT '发布动作描述',
  `action_class` varchar(255) DEFAULT NULL COMMENT '发布action类名',
  `action_seq_id` int(5) DEFAULT NULL COMMENT '发布顺序',
  `action_group` varchar(50) DEFAULT NULL COMMENT '发布过程分组',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_action_seq_id` (`action_seq_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `nebula_publish_sequence`
-- ----------------------------
BEGIN;
INSERT INTO `nebula_publish_sequence` VALUES ('1', 'CREATE_PUBLISH_EVENT', null, null, '1', 'PRE_MASTER'), ('2', 'GET_PUBLISH_SVN', null, 'GetPublishSvnAction', '2', 'PRE_MASTER'), ('3', 'ANALYZE_PROJECT', null, 'PublishRelationAction', '3', 'PRE_MASTER'), ('4', 'GET_SRC_SVN', null, 'GetSrcSvnAction', '4', 'PRE_MASTER'), ('5', 'UPDATE_ETC', null, null, '5', 'PRE_MASTER'), ('6', 'CREATE_PUBLISH_DIR', null, 'CreateDirAciton', '11', 'PRE_MINION'), ('7', 'COPY_PUBLISH_OLD_ETC', null, 'CpEtcAction', '12', 'PRE_MINION'), ('8', 'COPY_PUBLISH_OLD_WAR', null, 'CpWarAction', '13', 'PRE_MINION'), ('9', 'PUBLISH_NEW_ETC', null, 'PublishEtcAction', '14', 'PRE_MINION'), ('10', 'PUBLISH_NEW_WAR', null, 'PublishWarAction', '15', 'PRE_MINION'), ('11', 'STOP_TOMCAT', null, 'StopTomcatAction', '21', 'PUBLISH_REAL'), ('12', 'CHANGE_LN', null, 'ChangeLnAction', '22', 'PUBLISH_REAL'), ('13', 'START_TOMCAT', null, 'StartTomcatAction', '23', 'PUBLISH_REAL'), ('14', 'CLEAN_HISTORY_DIR', null, null, '31', 'SUCCESS_END'), ('15', 'UPDATE_SRC_SVN', null, null, '32', 'SUCCESS_END'), ('17', 'STOP_TOMCAT', null, 'StopTomcatAction', '41', 'FAIL_END'), ('18', 'CHANGE_LN', null, 'ChangeLnAction', '42', 'FAIL_END'), ('19', 'START_TOMCAT', null, 'StartTomcatAction', '43', 'FAIL_END'), ('20', 'CLEAN_FAIL_DIR', null, null, '44', 'FAIL_END'), ('25', 'CLEAN_PUBLISH_DIR', null, null, '50', 'CLEAN_END');
COMMIT;

-- ----------------------------
--  Table structure for `nebula_publish_svn_baseline`
-- ----------------------------
DROP TABLE IF EXISTS `nebula_publish_svn_baseline`;
CREATE TABLE `nebula_publish_svn_baseline` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `product_name` varchar(255) DEFAULT NULL COMMENT '产品英文名称',
  `product_env` varchar(50) DEFAULT NULL COMMENT '产品环境',
  `product_src_svn` varchar(255) DEFAULT NULL COMMENT '产品源svn地址',
  `publish_event_id` int(11) DEFAULT NULL COMMENT '发布事件id',
  `src_svn_version` varchar(255) DEFAULT NULL COMMENT '源svn版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `nebula_script`
-- ----------------------------
DROP TABLE IF EXISTS `nebula_script`;
CREATE TABLE `nebula_script` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '脚本id',
  `script_name` varchar(100) DEFAULT NULL COMMENT '脚本名称',
  `script_path` varchar(100) DEFAULT NULL COMMENT '脚本路径',
  `script_context` text COMMENT '脚本内容',
  `script_type` varchar(50) DEFAULT NULL COMMENT '脚本类型，publish/check',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `nebula_script_edit_log`
-- ----------------------------
DROP TABLE IF EXISTS `nebula_script_edit_log`;
CREATE TABLE `nebula_script_edit_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `script_id` int(11) DEFAULT NULL COMMENT '脚本id',
  `log_action` varchar(255) DEFAULT NULL COMMENT '动作，create/delete/update',
  `log_info` varchar(255) DEFAULT NULL COMMENT '日志信息',
  `log_datetime` datetime DEFAULT NULL COMMENT '日志时间',
  `opt_emp_id` int(11) DEFAULT NULL COMMENT '操作者工号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `nebula_script_group`
-- ----------------------------
DROP TABLE IF EXISTS `nebula_script_group`;
CREATE TABLE `nebula_script_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '脚本组id',
  `script_group_name` varchar(100) DEFAULT NULL COMMENT '脚本组名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `nebula_script_group_relation`
-- ----------------------------
DROP TABLE IF EXISTS `nebula_script_group_relation`;
CREATE TABLE `nebula_script_group_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `script_group_id` int(11) DEFAULT NULL COMMENT '脚本组id',
  `script_id` int(11) DEFAULT NULL COMMENT '脚本id',
  `script_seq` varchar(255) DEFAULT NULL COMMENT '脚本序列，记录脚本执行顺序，在一个组内递增',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `nebula_script_history`
-- ----------------------------
DROP TABLE IF EXISTS `nebula_script_history`;
CREATE TABLE `nebula_script_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `script_id` int(11) DEFAULT NULL COMMENT '脚本id，nebula_script.id',
  `script_name_h` varchar(255) DEFAULT NULL COMMENT '脚本名称',
  `script_path_h` varchar(255) DEFAULT NULL COMMENT '脚本路径',
  `script_context_h` text COMMENT '脚本内容',
  `script_type_h` varchar(255) DEFAULT NULL COMMENT '脚本类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Table structure for `nebula_user_info`
-- ----------------------------
DROP TABLE IF EXISTS `nebula_user_info`;
CREATE TABLE `nebula_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `mobile_phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `weixin_acc` varchar(50) DEFAULT NULL COMMENT '微信账号',
  `qq_acc` varchar(50) DEFAULT NULL COMMENT 'QQ账号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `dept_name` varchar(255) DEFAULT NULL COMMENT '部门名称',
  `job_title` varchar(50) DEFAULT NULL COMMENT '职位',
  `emp_id` int(10) NOT NULL COMMENT '工号',
  `supervisor_emp_id` int(10) DEFAULT NULL COMMENT '主管工号',
  `is_enable` int(5) DEFAULT NULL COMMENT '是否启用',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `security_key` varchar(100) DEFAULT NULL COMMENT '安全key',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_emp_id` (`emp_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `nebula_user_info`
-- ----------------------------
BEGIN;
INSERT INTO `nebula_user_info` VALUES ('1', 'admin', '18066265836', 'tsc19930816', '1074021573', 'taoshanchang@foxmail.com', 'babydance', 'yunwei', 'yunwei', '1', '1', '1', 'd3c59d25033dbf980d29554025c23a75', '8d78869f470951332959580424d4bf4f');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
