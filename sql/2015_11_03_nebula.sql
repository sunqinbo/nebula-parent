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

 Date: 11/03/2015 16:32:36 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `nebula_publish_app`
-- ----------------------------
DROP TABLE IF EXISTS `nebula_publish_app`;
CREATE TABLE `nebula_publish_app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `publish_event_id` int(11) DEFAULT NULL COMMENT '发布事件id',
  `publish_module_id` int(11) DEFAULT NULL COMMENT '发布模块id',
  `publish_app_name` varchar(50) DEFAULT NULL COMMENT '发布工程',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `nebula_publish_base`
-- ----------------------------
DROP TABLE IF EXISTS `nebula_publish_base`;
CREATE TABLE `nebula_publish_base` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `publish_event_id` int(11) DEFAULT NULL COMMENT '发布事件id',
  `publish_product_name` varchar(100) DEFAULT NULL COMMENT '发布产品',
  `publish_module_name` varchar(100) DEFAULT NULL COMMENT '发布模块',
  `publish_module_key` varchar(255) DEFAULT NULL COMMENT '发布模块key',
  `is_success_publish` int(5) DEFAULT NULL COMMENT '发布是否成功',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `nebula_publish_event`
-- ----------------------------
DROP TABLE IF EXISTS `nebula_publish_event`;
CREATE TABLE `nebula_publish_event` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '发布事件id',
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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_module_id_project` (`publish_module_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `nebula_publish_schedule`
-- ----------------------------
DROP TABLE IF EXISTS `nebula_publish_schedule`;
CREATE TABLE `nebula_publish_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `publish_event_id` int(11) DEFAULT NULL COMMENT '发布事件id',
  `publish_action` varchar(20) DEFAULT NULL COMMENT '发布动作',
  `is_success_action` int(255) DEFAULT NULL COMMENT '动作是否成功，0/1  失败/成功',
  `error_msg` longtext COMMENT '错误信息，如果不成功，返回不成功的msg',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `nebula_publish_sequence`
-- ----------------------------
DROP TABLE IF EXISTS `nebula_publish_sequence`;
CREATE TABLE `nebula_publish_sequence` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action_name` varchar(50) NOT NULL COMMENT '发布动作名称',
  `action_desc` varchar(50) DEFAULT NULL COMMENT '发布动作描述',
  `action_seq_id` int(5) DEFAULT NULL COMMENT '发布顺序',
  `action_group` varchar(50) DEFAULT NULL COMMENT '发布过程分组',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_action_seq_id` (`action_seq_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `nebula_publish_svn_baseline`
-- ----------------------------
DROP TABLE IF EXISTS `nebula_publish_svn_baseline`;
CREATE TABLE `nebula_publish_svn_baseline` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `product_name` varchar(255) DEFAULT NULL COMMENT '产品英文名称',
  `product_src_svn` varchar(255) DEFAULT NULL COMMENT '产品源svn地址',
  `publish_event_id` int(11) DEFAULT NULL COMMENT '发布事件id',
  `src_svn_version` varchar(255) DEFAULT NULL COMMENT '源svn版本号',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `nebula_script_group`
-- ----------------------------
DROP TABLE IF EXISTS `nebula_script_group`;
CREATE TABLE `nebula_script_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '脚本组id',
  `script_group_name` varchar(100) DEFAULT NULL COMMENT '脚本组名称',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
