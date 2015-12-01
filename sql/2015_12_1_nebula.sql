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

 Date: 12/01/2015 11:31:07 AM
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
  `is_enable` varchar(5) DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `acl_permission`
-- ----------------------------
BEGIN;
INSERT INTO `acl_permission` VALUES ('1', 'user', '用户管理', '用户管理', 'user:page', 'menu', 'user/list.htm', '0', null, '1'), ('2', 'user:add', '用户添加', '用户添加', 'user:add', 'button', '', '1', null, '1'), ('4', 'user:update', '用户更新', '用户更新', 'user:update', 'button', '', '1', null, '1'), ('5', 'user:delete', '用户删除', '用户删除', 'user:delete', 'button', '', '1', null, '1'), ('6', 'user:view', '用户查询', '用户查询', 'user:view', 'button', '', '1', null, '1'), ('7', 'permission', '资源管理', '资源管理', 'permission:page', 'menu', 'permission/list.htm', '0', null, '1'), ('8', 'permission:add', '资源添加', '资源添加', 'permission:add', 'button', '', '7', null, '1'), ('9', 'permission:update', '资源更新', '资源更新', 'permission:update', 'button', '', '7', null, '1'), ('10', 'permission:delete', '资源删除', '资源删除', 'permission:delete', 'button', '', '7', null, '1'), ('11', 'permission:view', '资源查询', '资源查询', 'permission:view', 'button', '', '7', null, '1'), ('12', 'role', '角色管理', '角色管理', 'role:page', 'menu', 'role/list.htm', '0', null, '1'), ('13', 'role:add', '角色添加', '角色添加', 'role:add', 'button', '', '12', null, '1'), ('14', 'role:delete', '角色删除', '角色删除', 'role:delete', 'button', '', '12', null, '1'), ('15', 'role:update', '角色更新', '角色更新', 'role:update', 'button', '', '12', null, '1'), ('16', 'role:view', '角色查询', '角色查询', 'role:view', 'button', '', '12', null, '1'), ('21', 'publishapply', '发布申请', '发布申请', 'publishapply:page', 'menu', '/publish/event.htm', '0', null, '1'), ('22', 'publishevent', '发布操作', '发布操作', 'publishevent:page', 'menu', '/publish/list.htm', '0', null, '1'), ('24', 'publishapply:commit', '发布提交', '发布提交', 'publishapply:commit', 'button', '', '21', null, '1'), ('25', 'publishevent:view', '发布查看', '发布查看', 'publishevent:view', 'button', '', '22', null, '1'), ('26', 'publishevnt:prePublishMaster', '准备发布', '准备发布', 'publishevnt:prePublishMaster', 'button', '', '22', null, '1'), ('27', 'publishevnt:prePublishMinion', '启动预发布', '启动预发布', 'publishevnt:prePublishMinion', 'button', '', '22', null, '1'), ('28', 'publishevnt:publishReal', '启动正式发布', '启动正式发布', 'publishevnt:publishReal', 'button', '', '22', null, '1'), ('29', 'publishevnt:publishContinue', '发布重试', '发布重试', 'publishevnt:publishContinue', 'button', '', '22', null, '1'), ('30', 'publishevnt:publishSuccessEnd', '确认发布成功', '确认发布成功', 'publishevnt:publishSuccessEnd', 'button', '', '22', null, '1'), ('31', 'publishevnt:publishFailEnd', '回滚', '回滚', 'publishevnt:publishFailEnd', 'button', '', '22', null, '1'), ('32', 'publishevnt:retryPublishRollback', '重新发布', '重新发布', 'publishevnt:retryPublishRollback', 'button', '', '22', null, '1'), ('33', 'publishevnt:updateEtcEnd', 'Etc编辑完成', 'Etc编辑完成', 'publishevnt:updateEtcEnd', 'button', '', '22', null, '1'), ('34', 'publishevnt:addnextpublish', '发布升级', '发布升级', 'publishevnt:addnextpublish', 'button', '', '22', null, '1'), ('35', 'publishevnt:fileEdit', '编辑etc', '编辑etc', 'publishevnt:updateEtc', 'button', '', '22', '', '1'), ('36', 'publishevnt:delete', '发布事件删除', '发布事件删除', 'publishevnt:delete', 'button', '', '22', '', '1'), ('37', 'publishevent:approval', '发布审批', '发布审批', 'publishevent:approval', 'button', '', '22', null, '1'), ('38', 'test', '测试11', '测试11', 'test', 'menu', '', null, null, '1');
COMMIT;

-- ----------------------------
--  Table structure for `acl_role`
-- ----------------------------
DROP TABLE IF EXISTS `acl_role`;
CREATE TABLE `acl_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `role_cname` varchar(100) DEFAULT NULL,
  `role_desc` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `is_enable` int(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `acl_role`
-- ----------------------------
BEGIN;
INSERT INTO `acl_role` VALUES ('1', 'root', '超级管理员', '超级管理员', '1'), ('2', 'committer', '提交人', '提交人', '1'), ('3', 'publisher', '发布人', '发布人', '1'), ('4', 'observer', '观察者', '观察者', '1'), ('5', 'admin', '管理员', '管理员', '1');
COMMIT;

-- ----------------------------
--  Table structure for `acl_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `acl_role_permission`;
CREATE TABLE `acl_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `acl_role_permission`
-- ----------------------------
BEGIN;
INSERT INTO `acl_role_permission` VALUES ('24', '2', '24'), ('25', '3', '26'), ('26', '3', '25'), ('27', '3', '24'), ('28', '4', '25'), ('52', '5', '22'), ('53', '5', '37'), ('54', '5', '36'), ('55', '5', '35'), ('56', '5', '34'), ('57', '5', '33'), ('58', '5', '32'), ('59', '5', '31'), ('60', '5', '30'), ('61', '5', '29'), ('62', '5', '28'), ('63', '5', '27'), ('64', '5', '26'), ('65', '5', '25'), ('66', '5', '21'), ('67', '5', '24'), ('68', '5', '12'), ('69', '5', '16'), ('70', '5', '15'), ('71', '5', '14'), ('72', '5', '13'), ('73', '5', '7'), ('74', '5', '11'), ('75', '5', '10'), ('76', '5', '9'), ('77', '5', '8'), ('78', '5', '1'), ('79', '5', '6'), ('80', '5', '5'), ('81', '5', '4'), ('82', '5', '2'), ('83', '1', '22'), ('84', '1', '37'), ('85', '1', '36'), ('86', '1', '35'), ('87', '1', '34'), ('88', '1', '33'), ('89', '1', '32'), ('90', '1', '31'), ('91', '1', '30'), ('92', '1', '29'), ('93', '1', '28'), ('94', '1', '27'), ('95', '1', '26'), ('96', '1', '25'), ('97', '1', '21'), ('98', '1', '24'), ('99', '1', '12'), ('100', '1', '16'), ('101', '1', '15'), ('102', '1', '14'), ('103', '1', '13'), ('104', '1', '7'), ('105', '1', '11'), ('106', '1', '10'), ('107', '1', '9'), ('108', '1', '8'), ('109', '1', '1'), ('110', '1', '6'), ('111', '1', '5'), ('112', '1', '4'), ('113', '1', '2');
COMMIT;

-- ----------------------------
--  Table structure for `acl_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `acl_user_role`;
CREATE TABLE `acl_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `emp_id` int(11) DEFAULT NULL COMMENT '工号',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `acl_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `acl_user_role` VALUES ('1', '9578', '3'), ('2', '1', '5');
COMMIT;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

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
  `publish_emp_id` int(10) DEFAULT NULL COMMENT '发布人empid',
  `is_success_publish` int(11) DEFAULT NULL COMMENT '发布是否成功，0/1  失败/成功',
  `is_delete` int(5) DEFAULT NULL COMMENT '是否被删除  1 ：是删除的',
  `publish_status` varchar(50) DEFAULT NULL COMMENT '发布状态',
  `is_approved` int(5) DEFAULT NULL COMMENT '1: 已经审批   默认是0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `nebula_publish_sequence`
-- ----------------------------
BEGIN;
INSERT INTO `nebula_publish_sequence` VALUES ('1', 'CREATE_PUBLISH_EVENT', null, null, '1', 'PRE_MASTER'), ('2', 'GET_PUBLISH_SVN', null, 'GetPublishSvnAction', '2', 'PRE_MASTER'), ('3', 'ANALYZE_PROJECT', null, 'PublishRelationAction', '3', 'PRE_MASTER'), ('4', 'GET_SRC_SVN', null, 'GetSrcSvnAction', '4', 'PRE_MASTER'), ('5', 'UPDATE_ETC', null, null, '5', 'PRE_MASTER'), ('6', 'CREATE_PUBLISH_DIR', null, 'CreateDirAciton', '11', 'PRE_MINION'), ('7', 'COPY_PUBLISH_OLD_ETC', null, 'CpEtcAction', '12', 'PRE_MINION'), ('8', 'COPY_PUBLISH_OLD_WAR', null, 'CpWarAction', '13', 'PRE_MINION'), ('9', 'PUBLISH_NEW_ETC', null, 'PublishEtcAction', '14', 'PRE_MINION'), ('10', 'PUBLISH_NEW_WAR', null, 'PublishWarAction', '15', 'PRE_MINION'), ('11', 'STOP_TOMCAT', null, 'StopTomcatAction', '21', 'PUBLISH_REAL'), ('12', 'CHANGE_LN', null, 'ChangeLnAction', '22', 'PUBLISH_REAL'), ('13', 'START_TOMCAT', null, 'StartTomcatAction', '23', 'PUBLISH_REAL'), ('14', 'CLEAN_HISTORY_DIR', null, 'CleanHistoryDirAction', '31', 'SUCCESS_END'), ('15', 'UPDATE_SRC_SVN', null, 'UpdateSrcSvnAction', '32', 'SUCCESS_END'), ('17', 'STOP_TOMCAT', null, 'StopTomcatAction', '41', 'FAIL_END'), ('18', 'CHANGE_LN', null, 'ChangeFailLnAction', '42', 'FAIL_END'), ('19', 'START_TOMCAT', null, 'StartTomcatAction', '43', 'FAIL_END'), ('20', 'CLEAN_PUBLISH_DIR', null, 'CleanPublishDirAction', '51', 'CLEAN_END');
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
  UNIQUE KEY `unique_emp_id` (`emp_id`),
  UNIQUE KEY `unique_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `nebula_user_info`
-- ----------------------------
BEGIN;
INSERT INTO `nebula_user_info` VALUES ('1', 'admin', '18066265836', 'tsc19930816', '1074021573', 'taoshanchang@foxmail.com', 'babydance', 'yunwei', 'yunwei', '8888', '1', '1', 'd3c59d25033dbf980d29554025c23a75', '8d78869f470951332959580424d4bf4f'), ('2', 'xujiawei', '18601981736', 'xxx', 'xxx', 'xxx', '伟哥', '运维', '运维临时工', '9578', '1', '1', '4af89ca9bc420c8881917124dc111bf3', 'd25736766762efbe9fe0a75edaf581d5'), ('3', 'tim', 'xxx', 'xxx', 'xxx', 'xxx', '大Boss', '运维', '运维部经理', '1', '8', '1', '1e2bddbd5e75c5a9e6038f704ee66389', '687805369609feed3ee6f43cd7069b2d');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
