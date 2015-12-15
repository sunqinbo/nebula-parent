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

 Date: 12/15/2015 16:16:55 PM
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
INSERT INTO `acl_permission` VALUES ('1', 'user', '用户管理', '用户管理', 'user:page', 'menu', 'user/list.htm', '0', null, '1'), ('2', 'user:add', '用户添加', '用户添加', 'user:add', 'button', '', '1', null, '1'), ('4', 'user:update', '用户更新', '用户更新', 'user:update', 'button', '', '1', null, '1'), ('5', 'user:delete', '用户删除', '用户删除', 'user:delete', 'button', '', '1', null, '1'), ('6', 'user:view', '用户查询', '用户查询', 'user:view', 'button', '', '1', null, '1'), ('7', 'permission', '资源管理', '资源管理', 'permission:page', 'menu', 'permission/list.htm', '0', null, '1'), ('8', 'permission:add', '资源添加', '资源添加', 'permission:add', 'button', '', '7', null, '1'), ('9', 'permission:update', '资源更新', '资源更新', 'permission:update', 'button', '', '7', null, '1'), ('10', 'permission:delete', '资源删除', '资源删除', 'permission:delete', 'button', '', '7', null, '1'), ('11', 'permission:view', '资源查询', '资源查询', 'permission:view', 'button', '', '7', null, '1'), ('12', 'role', '角色管理', '角色管理', 'role:page', 'menu', 'role/list.htm', '0', null, '1'), ('13', 'role:add', '角色添加', '角色添加', 'role:add', 'button', '', '12', null, '1'), ('14', 'role:delete', '角色删除', '角色删除', 'role:delete', 'button', '', '12', null, '1'), ('15', 'role:update', '角色更新', '角色更新', 'role:update', 'button', '', '12', null, '1'), ('16', 'role:view', '角色查询', '角色查询', 'role:view', 'button', '', '12', null, '1'), ('21', 'publishapply', '发布申请', '发布申请', 'publishapply:page', 'menu', '/publish/event.htm', '0', null, '1'), ('22', 'publishevent', '发布操作', '发布操作', 'publishevent:page', 'menu', '/publish/list.htm', '0', null, '1'), ('24', 'publishapply:commit', '发布提交', '发布提交', 'publishapply:commit', 'button', '', '21', null, '1'), ('25', 'publishevent:view', '发布查看', '发布查看', 'publishevent:view', 'button', '', '22', null, '1'), ('26', 'publishevnt:prePublishMaster', '准备发布', '准备发布', 'publishevnt:prePublishMaster', 'button', '', '22', null, '1'), ('27', 'publishevnt:prePublishMinion', '启动预发布', '启动预发布', 'publishevnt:prePublishMinion', 'button', '', '22', null, '1'), ('28', 'publishevnt:publishReal', '启动正式发布', '启动正式发布', 'publishevnt:publishReal', 'button', '', '22', null, '1'), ('29', 'publishevnt:publishContinue', '发布重试', '发布重试', 'publishevnt:publishContinue', 'button', '', '22', null, '1'), ('30', 'publishevnt:publishSuccessEnd', '确认发布成功', '确认发布成功', 'publishevnt:publishSuccessEnd', 'button', '', '22', null, '1'), ('31', 'publishevnt:publishFailEnd', '回滚', '回滚', 'publishevnt:publishFailEnd', 'button', '', '22', null, '1'), ('32', 'publishevnt:retryPublishRollback', '重新发布', '重新发布', 'publishevnt:retryPublishRollback', 'button', '', '22', null, '1'), ('33', 'publishevnt:updateEtcEnd', 'Etc编辑完成', 'Etc编辑完成', 'publishevnt:updateEtcEnd', 'button', '', '22', null, '1'), ('34', 'publishevnt:addnextpublish', '发布升级', '发布升级', 'publishevnt:addnextpublish', 'button', '', '22', null, '1'), ('35', 'publishevnt:fileEdit', '编辑etc', '编辑etc', 'publishevnt:updateEtc', 'button', '', '22', '', '1'), ('36', 'publishevnt:delete', '发布事件删除', '发布事件删除', 'publishevnt:delete', 'button', '', '22', '', '1'), ('37', 'publishevent:approval', '发布审批', '发布审批', 'publishevent:approval', 'button', '', '22', null, '1'), ('38', 'test', '测试11', '测试11', 'test', 'menu', '', null, null, '1'), ('39', 'user:updatePassword', '修改密码', '修改密码', 'user:updatePassword', 'button', '', '1', null, '1');
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
) ENGINE=InnoDB AUTO_INCREMENT=278 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `acl_role_permission`
-- ----------------------------
BEGIN;
INSERT INTO `acl_role_permission` VALUES ('24', '2', '24'), ('28', '4', '25'), ('114', '3', '22'), ('115', '3', '26'), ('116', '3', '25'), ('117', '3', '21'), ('118', '3', '24'), ('151', '1', '22'), ('152', '1', '37'), ('153', '1', '36'), ('154', '1', '35'), ('155', '1', '34'), ('156', '1', '33'), ('157', '1', '32'), ('158', '1', '31'), ('159', '1', '30'), ('160', '1', '29'), ('161', '1', '28'), ('162', '1', '27'), ('163', '1', '26'), ('164', '1', '25'), ('165', '1', '21'), ('166', '1', '24'), ('167', '1', '12'), ('168', '1', '16'), ('169', '1', '15'), ('170', '1', '14'), ('171', '1', '13'), ('172', '1', '7'), ('173', '1', '11'), ('174', '1', '10'), ('175', '1', '9'), ('176', '1', '8'), ('177', '1', '1'), ('178', '1', '39'), ('179', '1', '6'), ('180', '1', '5'), ('181', '1', '4'), ('182', '1', '2'), ('246', '5', '22'), ('247', '5', '37'), ('248', '5', '36'), ('249', '5', '35'), ('250', '5', '34'), ('251', '5', '33'), ('252', '5', '32'), ('253', '5', '31'), ('254', '5', '30'), ('255', '5', '29'), ('256', '5', '28'), ('257', '5', '27'), ('258', '5', '26'), ('259', '5', '25'), ('260', '5', '21'), ('261', '5', '24'), ('262', '5', '12'), ('263', '5', '16'), ('264', '5', '15'), ('265', '5', '14'), ('266', '5', '13'), ('267', '5', '7'), ('268', '5', '11'), ('269', '5', '10'), ('270', '5', '9'), ('271', '5', '8'), ('272', '5', '1'), ('273', '5', '39'), ('274', '5', '6'), ('275', '5', '5'), ('276', '5', '4'), ('277', '5', '2');
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `acl_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `acl_user_role` VALUES ('2', '1', '5'), ('8', '9578', '2');
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
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `nebula_publish_app`
-- ----------------------------
BEGIN;
INSERT INTO `nebula_publish_app` VALUES ('1', '6', '1', 'priceservice'), ('2', '6', '1', 'schedulerboss'), ('3', '6', '1', 'serviceportal'), ('4', '6', '2', 'ebooking'), ('5', '10', '3', 'arsenal-web'), ('6', '10', '3', 'arsenal-api'), ('7', '12', '4', 'arsenal-web'), ('8', '12', '4', 'arsenal-api'), ('9', '13', '5', 'arsenal-web'), ('10', '13', '5', 'arsenal-api'), ('11', '14', '6', 'arsenal-web'), ('12', '14', '6', 'arsenal-api'), ('13', '15', '7', 'arsenal-web'), ('14', '15', '7', 'arsenal-api'), ('17', '16', '9', 'arsenal-web'), ('18', '16', '9', 'arsenal-api'), ('19', '17', '10', 'arsenal-web'), ('20', '17', '10', 'arsenal-api'), ('21', '18', '11', 'arsenal-web'), ('22', '18', '11', 'arsenal-api'), ('23', '19', '12', 'arsenal-web'), ('24', '19', '12', 'arsenal-api'), ('25', '20', '13', 'arsenal-web'), ('26', '20', '13', 'arsenal-api'), ('27', '21', '14', 'arsenal-web'), ('28', '21', '14', 'arsenal-api'), ('29', '21', '15', 'arsenal-web'), ('30', '21', '15', 'arsenal-api'), ('31', '22', '16', 'arsenal-web'), ('32', '22', '16', 'arsenal-api'), ('33', '23', '17', 'arsenal-web'), ('34', '23', '17', 'arsenal-api'), ('35', '24', '18', 'arsenal-web'), ('36', '24', '18', 'arsenal-api'), ('37', '25', '19', 'arsenal-web'), ('38', '25', '19', 'arsenal-api'), ('39', '26', '20', 'arsenal-web'), ('40', '26', '20', 'arsenal-api'), ('41', '27', '21', 'arsenal-web'), ('42', '27', '21', 'arsenal-api'), ('43', '28', '22', 'arsenal-web'), ('44', '28', '22', 'arsenal-api'), ('45', '29', '23', 'arsenal-web'), ('46', '29', '23', 'arsenal-api');
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `nebula_publish_base`
-- ----------------------------
BEGIN;
INSERT INTO `nebula_publish_base` VALUES ('1', '1', 'test', 'yjt2014', 'ebooking', 'test.yjt2014.ebooking.20151111.1111111'), ('2', '2', 'test', 'yjt2014', 'ebooking', 'test.yjt2015.ebooking.201511222.222222'), ('3', '27', 'test', 'ywpt', 'publish', 'test.ywpt.publish.20151118.150242');
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
  `publish_emp_id` int(10) DEFAULT NULL COMMENT '发布人empid',
  `is_success_publish` int(11) DEFAULT NULL COMMENT '发布是否成功，0/1  失败/成功',
  `is_delete` int(5) DEFAULT NULL COMMENT '是否被删除  1 ：是删除的',
  `publish_status` varchar(50) DEFAULT NULL COMMENT '发布状态',
  `is_approved` int(5) DEFAULT NULL COMMENT '1: 已经审批   默认是0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `nebula_publish_event`
-- ----------------------------
BEGIN;
INSERT INTO `nebula_publish_event` VALUES ('1', '123', 'group', null, 'group_official_web', null, null, null, null, 'null.group_official_web.20151110.212418', null, '2015-11-10 21:24:18', null, null, null, null, null, null), ('2', '123', 'group', null, 'group_official_web', null, null, null, null, 'null.group_official_web.20151110.212547', null, '2015-11-10 21:25:47', null, null, null, null, null, null), ('3', '测试', 'gm', 'GM', 'booking_space', '订舱通', null, null, null, 'null.booking_space.20151111.090841', null, '2015-11-11 09:08:41', null, null, null, null, null, null), ('4', '123', 'gm', 'GM', 'booking_space', '订舱通', null, null, null, 'null.booking_space.20151111.091706', null, '2015-11-11 09:17:06', null, null, null, null, null, null), ('5', '123', 'pm', 'PM', 'cargo', '咖狗网', 'test', 'svn://172.16.137.150/war/', 'svn://172.16.137.150/yjt2014/', 'test.cargo.20151111.091728', null, '2015-11-11 09:17:28', null, null, null, null, null, null), ('6', '新的gm发布事件单', 'gm', 'GM', 'yjt2014', '运价通2014', 'test', 'svn://172.16.137.150/war/', 'svn://172.16.137.150/yjt2014/', 'test.yjt2014.20151111.132208', null, '2015-11-11 13:22:08', null, null, null, '0', 'PENDING_PUBLISH', '1'), ('7', 'test1', null, '请选择', null, '请选择', '请选择', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', null, '请选择.null.20151116.162333', null, '2015-11-16 16:23:33', null, null, null, null, null, null), ('8', 'test1', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', null, 'test.ywpt.20151116.163259', null, '2015-11-16 16:32:59', null, null, null, null, null, null), ('9', '测试', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', null, 'test.ywpt.20151116.165830', null, '2015-11-16 16:58:30', null, null, null, null, null, null), ('10', '123', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', null, 'test.ywpt.20151116.170949', null, '2015-11-16 17:09:49', null, null, null, null, null, null), ('11', '123', 'group', '集团', 'ywpt', '运维平台', 'test', '123', null, 'test.ywpt.20151116.171225', null, '2015-11-16 17:12:26', null, null, null, null, null, null), ('12', '123', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', 'svn://10.162.21.208/group/ywpt', 'test.ywpt.20151116.172153', null, '2015-11-16 17:21:53', null, null, null, null, null, null), ('13', '123', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', 'svn://10.162.21.208/group/ywpt', 'test.ywpt.20151116.175303', null, '2015-11-16 17:53:04', null, null, null, null, null, null), ('14', '123345', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', 'svn://10.162.21.208/group/ywpt', 'test.ywpt.20151116.191301', null, '2015-11-16 19:13:01', null, null, null, null, null, null), ('15', '123214', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', 'svn://10.162.21.208/group/ywpt', 'test.ywpt.20151116.192230', null, '2015-11-16 19:22:30', null, null, null, null, null, null), ('16', '324321', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', 'svn://10.162.21.208/group/ywpt', 'test.ywpt.20151116.201424', null, '2015-11-16 20:14:24', null, null, null, null, null, null), ('17', '3214321', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', 'svn://10.162.21.208/group/ywpt', 'test.ywpt.20151116.202939', null, '2015-11-16 20:29:40', null, null, null, null, null, null), ('18', '测试发布系统', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', 'svn://10.162.21.208/group/ywpt', 'test.ywpt.20151117.153409', null, '2015-11-17 15:34:09', null, null, null, null, null, null), ('19', '测试发布系统', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', 'svn://10.162.21.208/group/ywpt', 'test.ywpt.20151117.154855', null, '2015-11-17 15:48:55', null, null, null, null, null, null), ('20', 'yyp20151117', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', 'svn://10.162.21.208/group/ywpt', 'test.ywpt.20151117.162146', null, '2015-11-17 16:21:47', null, null, null, null, null, null), ('21', '陶善长帅比', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', 'svn://10.162.21.208/group/ywpt', 'test.ywpt.20151117.163302', null, '2015-11-17 16:33:03', null, null, null, null, null, null), ('22', '声导纳', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', 'svn://10.162.21.208/group/ywpt', 'test.ywpt.20151117.163524', null, '2015-11-17 16:35:25', null, null, null, null, null, null), ('23', 'yypt201511172', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', 'svn://10.162.21.208/group/ywpt', 'test.ywpt.20151117.163703', null, '2015-11-17 16:37:04', null, null, null, null, null, null), ('24', 'xjw', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', 'svn://10.162.21.208/group/ywpt', 'test.ywpt.20151117.164154', null, '2015-11-17 16:41:55', null, null, null, null, null, null), ('25', 'asd', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', 'svn://10.162.21.208/group/ywpt', 'test.ywpt.20151117.170141', null, '2015-11-17 17:01:42', null, null, null, null, null, null), ('26', '测试', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', 'svn://10.162.21.208/group/ywpt', 'test.ywpt.20151118.150241', null, '2015-11-18 15:02:42', null, null, '1', null, null, null), ('27', '测试了', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', 'svn://10.162.21.208/group/ywpt', 'test.ywpt.20151118.153316', null, '2015-11-18 15:33:17', null, null, '1', null, null, null), ('28', 'ces', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', 'svn://10.162.21.208/group/ywpt', 'test.ywpt.20151118.154201', null, '2015-11-18 15:42:01', null, null, '0', null, null, null), ('29', 'ddd', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/ywpt/20151116/', 'svn://10.162.21.208/group/ywpt', 'test.ywpt.20151118.155603', null, '2015-11-18 15:56:04', null, null, '0', null, null, null), ('30', '123', 'group', '集团', 'ywpt', '运维平台', 'test', '测试', 'ywpt', 'test.ywpt.20151125.113448', null, '2015-11-25 11:34:48', null, null, null, null, null, null), ('31', '123', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/', 'ywpt', 'test.ywpt.20151125.141323', null, '2015-11-25 14:13:23', null, null, null, '0', null, '0'), ('32', '123', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/', 'ywpt', 'test.ywpt.20151125.143846', null, '2015-11-25 14:38:46', '8888', null, null, '0', null, '0'), ('33', '123', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/', 'ywpt', 'test.ywpt.20151125.151217', null, '2015-11-25 15:12:17', '8888', null, null, '0', 'PENDING_APPROVE', '0'), ('34', '123', 'group', '集团', 'ywpt', '运维平台', 'test', 'svn://svn.olymtech.com/warspace/123123132', 'ywpt', 'test.ywpt.20151126.152338', null, '2015-11-26 15:23:38', '8888', null, null, '0', 'PENDING_APPROVE', '0'), ('35', 'test', 'group', '集团', 'ywpt', '运维平台', 'stage', 'svn://svn.olymtech.com/warspace/123123', 'ywpt', 'stage.ywpt.20151126.153001', null, '2015-11-26 15:30:02', '8888', null, null, '0', 'PENDING_APPROVE', '0'), ('36', 'xxx', 'group', '集团', 'ywpt', '运维平台', 'stage', 'svn://svn.olymtech.com/warspace/123', 'ywpt', 'stage.ywpt.20151126.155351', null, '2015-11-26 15:53:51', '8888', null, null, '0', 'PENDING_APPROVE', '0'), ('37', 'xxx', 'gm', 'GM', 'wx', 'GM微信服务号', 'test', 'svn://svn.olymtech.com/warspace/1111', '/gm/wx', 'test.wx.20151130.113348', null, '2015-11-30 11:33:48', '1', null, null, '0', 'PENDING_APPROVE', '0'), ('38', 'test', 'gm', 'GM', 'yjt2014', '运价通2014', 'test', 'svn://svn.olymtech.com/warspace/111', 'svn://10.162.21.208/gm/yjt2014', 'test.yjt2014.20151201.114715', '2015-12-02 17:46:17', '2015-12-01 11:47:15', '1', null, null, '0', 'PENDING_PUBLISH', '1'), ('39', 'test', 'gm', 'GM', 'yjt2014', '运价通2014', 'test', 'svn://svn.olymtech.com/warspace/11', 'svn://10.162.21.208/gm/yjt2014', 'test.yjt2014.20151201.115219', null, '2015-12-01 11:52:19', '1', null, null, '0', 'PUBLISHING', '1'), ('42', '新的gm发布事件单', 'gm', 'GM', 'yjt2014', '运价通2014', '准生产', 'svn://172.16.137.150/war/', 'svn://172.16.137.150/yjt2014/', '准生产.yjt2014.20151203.134613', null, '2015-12-03 13:46:13', null, null, null, '0', 'PENDING_PUBLISH', '1');
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
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `nebula_publish_host`
-- ----------------------------
BEGIN;
INSERT INTO `nebula_publish_host` VALUES ('1', '6', '1', 'iZ23l8fa2cjZ', '10.161.218.194', null, null, null, null), ('2', '6', '1', 'iZ23t7vmj2yZ', '10.117.20.185', null, null, null, null), ('3', '6', '2', 'iZ23vlct7vgZ', '10.171.212.6', null, null, null, null), ('4', '10', '3', 'YW_saltminion01', '10.160.50.183', null, null, null, null), ('5', '10', '3', 'YW_saltminion02', '10.162.61.152', null, null, null, null), ('6', '12', '4', 'YW_saltminion01', '10.160.50.183', null, null, null, null), ('7', '12', '4', 'YW_saltminion02', '10.162.61.152', null, null, null, null), ('8', '13', '5', 'minion-tiny', '10.160.50.183', null, null, null, null), ('9', '13', '5', 'YW_saltminion02', '10.162.61.152', null, null, null, null), ('10', '14', '6', 'minion-tiny', '10.160.50.183', null, null, null, null), ('11', '14', '6', 'minion-tim', '10.162.61.152', null, null, null, null), ('12', '15', '7', 'minion-tiny', '10.160.50.183', null, null, null, null), ('13', '15', '7', 'minion-tim', '10.162.61.152', null, null, null, null), ('16', '16', '9', 'minion-tiny', '10.160.50.183', null, null, null, null), ('17', '16', '9', 'minion-tim', '10.162.61.152', null, null, null, null), ('18', '17', '10', 'minion-tiny', '10.160.50.183', null, null, null, null), ('19', '17', '10', 'minion-tim', '10.162.61.152', null, null, null, null), ('20', '18', '11', 'minion-tiny', '10.160.50.183', null, null, null, null), ('21', '18', '11', 'minion-tim', '10.162.61.152', 'START_TOMCAT', 'PUBLISH_REAL', '1', 'Tomcat started.'), ('22', '19', '12', 'minion-tiny', '10.160.50.183', 'START_TOMCAT', 'PUBLISH_REAL', '1', 'Tomcat started.'), ('23', '19', '12', 'minion-tim', '10.162.61.152', 'START_TOMCAT', 'PUBLISH_REAL', '1', 'Tomcat started.'), ('24', '20', '13', 'minion-tiny', '10.160.50.183', 'START_TOMCAT', 'PUBLISH_REAL', '1', 'Tomcat started.'), ('25', '20', '13', 'minion-tim', '10.162.61.152', 'START_TOMCAT', 'PUBLISH_REAL', '1', 'Tomcat started.'), ('26', '21', '14', 'minion-tiny', '10.160.50.183', null, null, null, null), ('27', '21', '14', 'minion-tim', '10.162.61.152', null, null, null, null), ('28', '21', '15', 'minion-tiny', '10.160.50.183', null, null, null, null), ('29', '21', '15', 'minion-tim', '10.162.61.152', null, null, null, null), ('30', '22', '16', 'minion-tiny', '10.160.50.183', null, null, null, null), ('31', '22', '16', 'minion-tim', '10.162.61.152', null, null, null, null), ('32', '23', '17', 'minion-tiny', '10.160.50.183', 'START_TOMCAT', 'PUBLISH_REAL', '1', 'Tomcat started.'), ('33', '23', '17', 'minion-tim', '10.162.61.152', 'START_TOMCAT', 'PUBLISH_REAL', '1', 'Tomcat started.'), ('34', '24', '18', 'minion-tiny', '10.160.50.183', null, null, null, null), ('35', '24', '18', 'minion-tim', '10.162.61.152', null, null, null, null), ('36', '25', '19', 'minion-tiny', '10.160.50.183', null, null, null, null), ('37', '25', '19', 'minion-tim', '10.162.61.152', null, null, null, null), ('38', '26', '20', 'minion-tiny', '10.160.50.183', 'CLEAN_HISTORY_DIR', 'CLEAN_END', '1', 'success'), ('39', '26', '20', 'minion-tim', '10.162.61.152', 'CLEAN_HISTORY_DIR', 'CLEAN_END', '1', 'success'), ('40', '27', '21', 'minion-tiny', '10.160.50.183', 'CLEAN_HISTORY_DIR', 'CLEAN_END', '1', 'success'), ('41', '27', '21', 'minion-tim', '10.162.61.152', 'CLEAN_HISTORY_DIR', 'CLEAN_END', '1', 'success'), ('42', '28', '22', 'minion-tiny', '10.160.50.183', 'CHANGE_LN', 'FAIL_END', '1', 'success'), ('43', '28', '22', 'minion-tim', '10.162.61.152', 'CHANGE_LN', 'FAIL_END', '1', 'success'), ('44', '29', '23', 'minion-tiny', '10.160.50.183', 'CHANGE_LN', 'FAIL_END', '1', 'success'), ('45', '29', '23', 'minion-tim', '10.162.61.152', 'CHANGE_LN', 'FAIL_END', '1', 'success');
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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `nebula_publish_module`
-- ----------------------------
BEGIN;
INSERT INTO `nebula_publish_module` VALUES ('1', '6', 'yjt2014', 'yjt2014_m', 'test.yjt2014.yjt2014_m.20151111.132208', '/gm/yjt2014_m'), ('2', '6', 'yjt2014', 'ebooking', 'test.yjt2014.ebooking.20151111.132208', 'ebooking'), ('3', '10', 'ywpt', 'publish', 'test.ywpt.publish.20151116.170949', 'publish'), ('4', '12', 'ywpt', 'publish', 'test.ywpt.publish.20151116.172153', 'publish'), ('5', '13', 'ywpt', 'publish', 'test.ywpt.publish.20151116.175304', 'publish'), ('6', '14', 'ywpt', 'publish', 'test.ywpt.publish.20151116.191301', 'publish'), ('7', '15', 'ywpt', 'publish', 'test.ywpt.publish.20151116.192230', 'publish'), ('9', '16', 'ywpt', 'publish', 'test.ywpt.publish.20151116.201424', 'publish'), ('10', '17', 'ywpt', 'publish', 'test.ywpt.publish.20151116.202940', 'publish'), ('11', '18', 'ywpt', 'publish', 'test.ywpt.publish.20151117.153409', 'publish'), ('12', '19', 'ywpt', 'publish', 'test.ywpt.publish.20151117.154855', 'publish'), ('13', '20', 'ywpt', 'publish', 'test.ywpt.publish.20151117.162147', 'publish'), ('14', '21', 'ywpt', 'publish', 'test.ywpt.publish.20151117.163303', 'publish'), ('15', '21', 'ywpt', 'publish', 'test.ywpt.publish.20151117.163303', 'publish'), ('16', '22', 'ywpt', 'publish', 'test.ywpt.publish.20151117.163525', 'publish'), ('17', '23', 'ywpt', 'publish', 'test.ywpt.publish.20151117.163704', 'publish'), ('18', '24', 'ywpt', 'publish', 'test.ywpt.publish.20151117.164155', 'publish'), ('19', '25', 'ywpt', 'publish', 'test.ywpt.publish.20151117.170142', 'publish'), ('20', '26', 'ywpt', 'publish', 'test.ywpt.publish.20151118.150242', 'publish'), ('21', '27', 'ywpt', 'publish', 'test.ywpt.publish.20151118.153317', 'publish'), ('22', '28', 'ywpt', 'publish', 'test.ywpt.publish.20151118.154201', 'publish'), ('23', '29', 'ywpt', 'publish', 'test.ywpt.publish.20151118.155604', 'publish');
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
) ENGINE=InnoDB AUTO_INCREMENT=307 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `nebula_publish_schedule`
-- ----------------------------
BEGIN;
INSERT INTO `nebula_publish_schedule` VALUES ('1', '6', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('73', '6', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('74', '6', 'ANALYZE_PROJECT', 'PRE_MASTER', '1', ''), ('75', '6', 'UPDATE_SRC_SVN', 'SUCCESS_END', '1', ''), ('76', '6', 'CLEAN_PUBLISH_DIR', 'CLEAN_END', '1', ''), ('77', '7', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('78', '8', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('79', '9', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('80', '9', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('81', '9', 'ANALYZE_PROJECT', 'PRE_MASTER', '0', 'error message'), ('82', '10', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('83', '10', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('84', '10', 'ANALYZE_PROJECT', 'PRE_MASTER', '1', ''), ('85', '10', 'GET_SRC_SVN', 'PRE_MASTER', '0', 'error message'), ('86', '11', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('87', '12', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('88', '12', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('89', '12', 'ANALYZE_PROJECT', 'PRE_MASTER', '1', ''), ('90', '12', 'GET_SRC_SVN', 'PRE_MASTER', '1', ''), ('91', '12', 'UPDATE_ETC', 'PRE_MASTER', '1', ''), ('92', '12', 'CREATE_PUBLISH_DIR', 'PRE_MINION', null, ''), ('93', '13', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('94', '13', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('95', '13', 'ANALYZE_PROJECT', 'PRE_MASTER', '1', ''), ('96', '13', 'GET_SRC_SVN', 'PRE_MASTER', '1', ''), ('97', '13', 'UPDATE_ETC', 'PRE_MASTER', '1', ''), ('98', '13', 'CREATE_PUBLISH_DIR', 'PRE_MINION', '1', ''), ('99', '13', 'COPY_PUBLISH_OLD_ETC', 'PRE_MINION', '1', ''), ('100', '13', 'COPY_PUBLISH_OLD_WAR', 'PRE_MINION', '1', ''), ('101', '13', 'PUBLISH_NEW_ETC', 'PRE_MINION', '1', ''), ('102', '13', 'PUBLISH_NEW_WAR', 'PRE_MINION', '1', ''), ('103', '13', 'STOP_TOMCAT', 'PUBLISH_REAL', '1', ''), ('104', '13', 'CHANGE_LN', 'PUBLISH_REAL', '1', ''), ('105', '13', 'START_TOMCAT', 'PUBLISH_REAL', '1', ''), ('106', '14', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('107', '14', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('108', '14', 'ANALYZE_PROJECT', 'PRE_MASTER', '1', ''), ('109', '14', 'GET_SRC_SVN', 'PRE_MASTER', '1', ''), ('110', '14', 'UPDATE_ETC', 'PRE_MASTER', null, ''), ('111', '15', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('112', '15', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('113', '15', 'ANALYZE_PROJECT', 'PRE_MASTER', '1', ''), ('114', '15', 'GET_SRC_SVN', 'PRE_MASTER', '1', ''), ('115', '15', 'UPDATE_ETC', 'PRE_MASTER', '1', ''), ('116', '15', 'CREATE_PUBLISH_DIR', 'PRE_MINION', '1', ''), ('117', '15', 'COPY_PUBLISH_OLD_ETC', 'PRE_MINION', '1', ''), ('118', '15', 'COPY_PUBLISH_OLD_WAR', 'PRE_MINION', '1', ''), ('119', '15', 'PUBLISH_NEW_ETC', 'PRE_MINION', '1', ''), ('120', '15', 'PUBLISH_NEW_WAR', 'PRE_MINION', '1', ''), ('121', '15', 'STOP_TOMCAT', 'PUBLISH_REAL', '1', ''), ('122', '15', 'CHANGE_LN', 'PUBLISH_REAL', '1', ''), ('123', '15', 'START_TOMCAT', 'PUBLISH_REAL', '1', ''), ('124', '16', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('125', '16', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('126', '16', 'ANALYZE_PROJECT', 'PRE_MASTER', '1', ''), ('129', '16', 'GET_SRC_SVN', 'PRE_MASTER', '1', ''), ('130', '16', 'UPDATE_ETC', 'PRE_MASTER', '1', ''), ('131', '16', 'CREATE_PUBLISH_DIR', 'PRE_MINION', '1', ''), ('132', '16', 'COPY_PUBLISH_OLD_ETC', 'PRE_MINION', '1', ''), ('133', '16', 'COPY_PUBLISH_OLD_WAR', 'PRE_MINION', '1', ''), ('134', '16', 'PUBLISH_NEW_ETC', 'PRE_MINION', '1', ''), ('135', '16', 'PUBLISH_NEW_WAR', 'PRE_MINION', '1', ''), ('136', '16', 'STOP_TOMCAT', 'PUBLISH_REAL', '1', ''), ('137', '16', 'CHANGE_LN', 'PUBLISH_REAL', '1', ''), ('138', '16', 'START_TOMCAT', 'PUBLISH_REAL', '1', ''), ('139', '17', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('140', '17', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('141', '17', 'ANALYZE_PROJECT', 'PRE_MASTER', '1', ''), ('142', '17', 'GET_SRC_SVN', 'PRE_MASTER', '1', ''), ('143', '17', 'UPDATE_ETC', 'PRE_MASTER', '1', ''), ('144', '17', 'CREATE_PUBLISH_DIR', 'PRE_MINION', '1', ''), ('145', '17', 'COPY_PUBLISH_OLD_ETC', 'PRE_MINION', '1', ''), ('146', '17', 'COPY_PUBLISH_OLD_WAR', 'PRE_MINION', '1', ''), ('147', '17', 'PUBLISH_NEW_ETC', 'PRE_MINION', '1', ''), ('148', '17', 'PUBLISH_NEW_WAR', 'PRE_MINION', '1', ''), ('149', '18', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('150', '18', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('151', '18', 'ANALYZE_PROJECT', 'PRE_MASTER', '1', ''), ('152', '18', 'GET_SRC_SVN', 'PRE_MASTER', '1', ''), ('153', '18', 'UPDATE_ETC', 'PRE_MASTER', '1', ''), ('154', '18', 'CREATE_PUBLISH_DIR', 'PRE_MINION', '1', ''), ('155', '18', 'COPY_PUBLISH_OLD_ETC', 'PRE_MINION', '1', ''), ('156', '18', 'COPY_PUBLISH_OLD_WAR', 'PRE_MINION', '1', ''), ('157', '18', 'PUBLISH_NEW_ETC', 'PRE_MINION', '1', ''), ('158', '18', 'PUBLISH_NEW_WAR', 'PRE_MINION', '1', ''), ('159', '18', 'STOP_TOMCAT', 'PUBLISH_REAL', '1', ''), ('160', '18', 'CHANGE_LN', 'PUBLISH_REAL', '1', ''), ('161', '18', 'START_TOMCAT', 'PUBLISH_REAL', '1', ''), ('162', '19', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('163', '19', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('164', '19', 'ANALYZE_PROJECT', 'PRE_MASTER', '1', ''), ('165', '19', 'GET_SRC_SVN', 'PRE_MASTER', '1', ''), ('166', '19', 'UPDATE_ETC', 'PRE_MASTER', '1', ''), ('167', '19', 'CREATE_PUBLISH_DIR', 'PRE_MINION', '1', ''), ('168', '19', 'COPY_PUBLISH_OLD_ETC', 'PRE_MINION', '1', ''), ('169', '19', 'COPY_PUBLISH_OLD_WAR', 'PRE_MINION', '1', ''), ('170', '19', 'PUBLISH_NEW_ETC', 'PRE_MINION', '1', ''), ('171', '19', 'PUBLISH_NEW_WAR', 'PRE_MINION', '1', ''), ('172', '19', 'STOP_TOMCAT', 'PUBLISH_REAL', '1', ''), ('173', '19', 'CHANGE_LN', 'PUBLISH_REAL', '1', ''), ('174', '19', 'START_TOMCAT', 'PUBLISH_REAL', '1', ''), ('175', '20', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('176', '20', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('177', '20', 'ANALYZE_PROJECT', 'PRE_MASTER', '1', ''), ('178', '20', 'GET_SRC_SVN', 'PRE_MASTER', '1', ''), ('179', '20', 'UPDATE_ETC', 'PRE_MASTER', '1', ''), ('180', '20', 'CREATE_PUBLISH_DIR', 'PRE_MINION', '1', ''), ('181', '20', 'COPY_PUBLISH_OLD_ETC', 'PRE_MINION', '1', ''), ('182', '20', 'COPY_PUBLISH_OLD_WAR', 'PRE_MINION', '1', ''), ('183', '20', 'PUBLISH_NEW_ETC', 'PRE_MINION', '1', ''), ('184', '20', 'PUBLISH_NEW_WAR', 'PRE_MINION', '1', ''), ('185', '20', 'STOP_TOMCAT', 'PUBLISH_REAL', '1', ''), ('186', '20', 'CHANGE_LN', 'PUBLISH_REAL', '1', ''), ('187', '20', 'START_TOMCAT', 'PUBLISH_REAL', '1', ''), ('188', '21', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('189', '21', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('190', '21', 'ANALYZE_PROJECT', 'PRE_MASTER', '1', ''), ('191', '21', 'GET_SRC_SVN', 'PRE_MASTER', '1', ''), ('192', '21', 'UPDATE_ETC', 'PRE_MASTER', null, ''), ('193', '22', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('194', '22', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('195', '22', 'ANALYZE_PROJECT', 'PRE_MASTER', '1', ''), ('196', '22', 'GET_SRC_SVN', 'PRE_MASTER', '1', ''), ('197', '22', 'UPDATE_ETC', 'PRE_MASTER', null, ''), ('198', '23', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('199', '23', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('200', '23', 'ANALYZE_PROJECT', 'PRE_MASTER', '1', ''), ('201', '23', 'GET_SRC_SVN', 'PRE_MASTER', '1', ''), ('202', '23', 'UPDATE_ETC', 'PRE_MASTER', '1', ''), ('203', '23', 'CREATE_PUBLISH_DIR', 'PRE_MINION', '1', ''), ('204', '23', 'COPY_PUBLISH_OLD_ETC', 'PRE_MINION', '1', ''), ('205', '23', 'COPY_PUBLISH_OLD_WAR', 'PRE_MINION', '1', ''), ('206', '23', 'PUBLISH_NEW_ETC', 'PRE_MINION', '1', ''), ('207', '23', 'PUBLISH_NEW_WAR', 'PRE_MINION', '1', ''), ('208', '23', 'STOP_TOMCAT', 'PUBLISH_REAL', '1', ''), ('209', '23', 'CHANGE_LN', 'PUBLISH_REAL', '1', ''), ('210', '23', 'START_TOMCAT', 'PUBLISH_REAL', '1', ''), ('211', '24', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('212', '24', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('213', '24', 'ANALYZE_PROJECT', 'PRE_MASTER', '1', ''), ('214', '24', 'GET_SRC_SVN', 'PRE_MASTER', '1', ''), ('215', '24', 'UPDATE_ETC', 'PRE_MASTER', null, ''), ('216', '25', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('217', '25', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('218', '25', 'ANALYZE_PROJECT', 'PRE_MASTER', '1', ''), ('219', '25', 'GET_SRC_SVN', 'PRE_MASTER', '1', ''), ('220', '25', 'UPDATE_ETC', 'PRE_MASTER', null, ''), ('221', '26', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('222', '26', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('223', '26', 'ANALYZE_PROJECT', 'PRE_MASTER', '1', ''), ('224', '26', 'GET_SRC_SVN', 'PRE_MASTER', '1', ''), ('225', '26', 'UPDATE_ETC', 'PRE_MASTER', '1', ''), ('226', '26', 'CREATE_PUBLISH_DIR', 'PRE_MINION', '1', ''), ('227', '26', 'COPY_PUBLISH_OLD_ETC', 'PRE_MINION', '1', ''), ('228', '26', 'COPY_PUBLISH_OLD_WAR', 'PRE_MINION', '1', ''), ('229', '26', 'PUBLISH_NEW_ETC', 'PRE_MINION', '1', ''), ('230', '26', 'PUBLISH_NEW_WAR', 'PRE_MINION', '1', ''), ('231', '26', 'STOP_TOMCAT', 'PUBLISH_REAL', '1', ''), ('232', '26', 'CHANGE_LN', 'PUBLISH_REAL', '1', ''), ('233', '26', 'START_TOMCAT', 'PUBLISH_REAL', '1', ''), ('235', '26', 'CLEAN_HISTORY_DIR', 'CLEAN_END', null, ''), ('236', '26', 'CLEAN_HISTORY_DIR', 'PUBLISH_REAL', '1', ''), ('237', '26', 'UPDATE_SRC_SVN', 'SUCCESS_END', '1', 'r3 by \'superman\' at Wed Nov 18 15:18:51 CST 2015'), ('238', '26', 'CLEAN_PUBLISH_DIR', 'CLEAN_END', '0', '清除发布目录失败：/home/saas/deploy_tmp/test.ywpt.20151118.150241'), ('239', '27', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('240', '27', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('241', '27', 'ANALYZE_PROJECT', 'PRE_MASTER', '1', ''), ('242', '27', 'GET_SRC_SVN', 'PRE_MASTER', '1', ''), ('243', '27', 'UPDATE_ETC', 'PRE_MASTER', '1', ''), ('244', '27', 'CREATE_PUBLISH_DIR', 'PRE_MINION', '1', ''), ('245', '27', 'COPY_PUBLISH_OLD_ETC', 'PRE_MINION', '1', ''), ('246', '27', 'COPY_PUBLISH_OLD_WAR', 'PRE_MINION', '1', ''), ('247', '27', 'PUBLISH_NEW_ETC', 'PRE_MINION', '1', ''), ('248', '27', 'PUBLISH_NEW_WAR', 'PRE_MINION', '1', ''), ('249', '27', 'STOP_TOMCAT', 'PUBLISH_REAL', '1', ''), ('250', '27', 'CHANGE_LN', 'PUBLISH_REAL', '1', ''), ('251', '27', 'START_TOMCAT', 'PUBLISH_REAL', '1', ''), ('252', '27', 'CLEAN_HISTORY_DIR', 'SUCCESS_END', '1', ''), ('253', '27', 'UPDATE_SRC_SVN', 'SUCCESS_END', '1', 'r4 by \'superman\' at Wed Nov 18 15:35:00 CST 2015'), ('254', '27', 'CLEAN_PUBLISH_DIR', 'CLEAN_END', '1', '清除发布目录成功'), ('255', '28', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('256', '28', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('257', '28', 'ANALYZE_PROJECT', 'PRE_MASTER', '1', ''), ('258', '28', 'GET_SRC_SVN', 'PRE_MASTER', '1', ''), ('259', '28', 'UPDATE_ETC', 'PRE_MASTER', '1', ''), ('260', '28', 'CREATE_PUBLISH_DIR', 'PRE_MINION', '1', ''), ('261', '28', 'COPY_PUBLISH_OLD_ETC', 'PRE_MINION', '1', ''), ('262', '28', 'COPY_PUBLISH_OLD_WAR', 'PRE_MINION', '1', ''), ('263', '28', 'PUBLISH_NEW_ETC', 'PRE_MINION', '1', ''), ('264', '28', 'PUBLISH_NEW_WAR', 'PRE_MINION', '1', ''), ('265', '28', 'STOP_TOMCAT', 'PUBLISH_REAL', '1', ''), ('266', '28', 'CHANGE_LN', 'PUBLISH_REAL', '1', ''), ('267', '28', 'START_TOMCAT', 'PUBLISH_REAL', '1', ''), ('268', '28', 'CHANGE_LN', 'FAIL_END', '1', ''), ('269', '28', 'CLEAN_PUBLISH_DIR', 'CLEAN_END', '1', '清除发布目录成功'), ('270', '29', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('271', '29', 'GET_PUBLISH_SVN', 'PRE_MASTER', '1', ''), ('272', '29', 'ANALYZE_PROJECT', 'PRE_MASTER', '1', ''), ('273', '29', 'GET_SRC_SVN', 'PRE_MASTER', '1', ''), ('274', '29', 'UPDATE_ETC', 'PRE_MASTER', '1', ''), ('275', '29', 'CREATE_PUBLISH_DIR', 'PRE_MINION', '1', ''), ('276', '29', 'COPY_PUBLISH_OLD_ETC', 'PRE_MINION', '1', ''), ('277', '29', 'COPY_PUBLISH_OLD_WAR', 'PRE_MINION', '1', ''), ('278', '29', 'PUBLISH_NEW_ETC', 'PRE_MINION', '1', ''), ('279', '29', 'PUBLISH_NEW_WAR', 'PRE_MINION', '1', ''), ('280', '29', 'STOP_TOMCAT', 'PUBLISH_REAL', '1', ''), ('281', '29', 'CHANGE_LN', 'PUBLISH_REAL', '1', ''), ('282', '29', 'START_TOMCAT', 'PUBLISH_REAL', '1', ''), ('283', '29', 'CHANGE_LN', 'FAIL_END', '1', ''), ('284', '29', 'CLEAN_FAIL_DIR', 'FAIL_END', '1', ''), ('285', '29', 'CLEAN_PUBLISH_DIR', 'CLEAN_END', '0', '清除发布目录失败：/home/saas/deploy_tmp/test.ywpt.20151118.155603'), ('286', '30', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('287', '31', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('288', '32', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('289', '33', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('290', '34', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('291', '35', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('292', '36', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('293', '37', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('294', '38', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('295', '39', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('298', '38', 'UPDATE_ETC', 'PRE_MASTER', '1', 'error message'), ('304', '40', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('305', '41', 'CREATE_PUBLISH_EVENT', 'PRE_MASTER', '1', ''), ('306', '42', 'GET_SRC_SVN', 'PRE_MASTER', null, '');
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
INSERT INTO `nebula_publish_sequence` VALUES ('1', 'CREATE_PUBLISH_EVENT', null, null, '1', 'PRE_MASTER'), ('2', 'GET_PUBLISH_SVN', null, 'GetPublishSvnAction', '2', 'PRE_MASTER'), ('3', 'ANALYZE_PROJECT', null, 'PublishRelationAction', '3', 'PRE_MASTER'), ('4', 'GET_SRC_SVN', null, 'GetSrcSvnAction', '4', 'PRE_MASTER'), ('5', 'UPDATE_ETC', null, null, '5', 'PRE_MASTER'), ('6', 'CREATE_PUBLISH_DIR', null, 'CreateDirAciton', '11', 'PRE_MINION'), ('7', 'COPY_PUBLISH_OLD_ETC', null, 'CpEtcAction', '12', 'PRE_MINION'), ('8', 'COPY_PUBLISH_OLD_WAR', null, 'CpWarAction', '13', 'PRE_MINION'), ('9', 'PUBLISH_NEW_ETC', null, 'PublishEtcAction', '14', 'PRE_MINION'), ('10', 'PUBLISH_NEW_WAR', null, 'PublishWarAction', '15', 'PRE_MINION'), ('11', 'STOP_TOMCAT', null, 'StopTomcatAction', '21', 'PUBLISH_REAL'), ('12', 'CHANGE_LN', null, 'ChangeLnAction', '22', 'PUBLISH_REAL'), ('13', 'START_TOMCAT', null, 'StartTomcatAction', '23', 'PUBLISH_REAL'), ('14', 'CLEAN_HISTORY_DIR', null, 'CleanHistoryDirAction', '31', 'SUCCESS_END'), ('15', 'UPDATE_SRC_SVN', null, 'UpdateSrcSvnAction', '32', 'SUCCESS_END'), ('17', 'STOP_TOMCAT', null, 'StopTomcatAction', '41', 'FAIL_END'), ('18', 'CHANGE_LN', null, 'ChangeFailLnAction', '42', 'FAIL_END'), ('19', 'START_TOMCAT', null, 'StartTomcatAction', '43', 'FAIL_END'), ('20', 'CLEAN_PUBLISH_DIR', null, 'CleanPublishDirAction', '51', 'CLEAN_END'), ('21', 'STOP_TOMCAT', null, 'StopTomcatAction', '61', 'RESTART_TOMCAT'), ('22', 'START_TOMCAT', null, 'StartTomcatAction', '62', 'RESTART_TOMCAT'), ('23', 'CLEAN_FAIL_DIR', null, 'CleanFailDirAction', '71', 'CANCEL_END'), ('25', 'CLEAN_FAIL_DIR', null, 'CleanFailDirAction', '44', 'FAIL_END');
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `nebula_publish_svn_baseline`
-- ----------------------------
BEGIN;
INSERT INTO `nebula_publish_svn_baseline` VALUES ('1', 'ywpt', 'test', 'svn://10.162.21.208/group/ywpt', '26', 'r3 by \'superman\' at Wed Nov 18 15:18:51 CST 2015'), ('2', 'ywpt', 'test', 'svn://10.162.21.208/group/ywpt', '27', 'r4 by \'superman\' at Wed Nov 18 15:35:00 CST 2015');
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
--  Records of `nebula_user_info`
-- ----------------------------
BEGIN;
INSERT INTO `nebula_user_info` VALUES ('1', 'admin', '18066265836', 'tsc19930816', '1074021573', 'taoshanchang@foxmail.com', 'babydance', 'yunwei', 'yunwei', '8888', '1', '1', 'd3c59d25033dbf980d29554025c23a75', '8d78869f470951332959580424d4bf4f'), ('2', 'xujiawei', '18601981736', 'xxx', 'xxx', 'xxx', '伟哥', '运维', '运维临时工', '9578', '1', '1', 'f33c239610f2cbebdcaaf9163ca3389a', 'b32b169f082722ab99d7f6ea58ed568f'), ('3', 'tim', 'xxx', 'xxx', 'xxx', 'xxx', '大Boss', '运维', '运维部经理', '1', '8', '1', '48f713dff5a23412140e7d5928ab3306', '9809b4f0458274f02ccc74f1d5cfa35f');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
