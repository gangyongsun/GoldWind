/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.225
Source Server Version : 50173
Source Host           : 192.168.0.225:3306
Source Database       : ldemo

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2017-04-19 13:36:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_area
-- ----------------------------
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE `sys_area` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `parent_name` varchar(100) DEFAULT NULL COMMENT '父名称',
  `full_name` varchar(2000) DEFAULT NULL COMMENT '全名称',
  `parent_id` varchar(64) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',
  `type` char(1) DEFAULT NULL COMMENT '区域类型',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_area_parent_id` (`parent_id`) USING BTREE,
  KEY `sys_area_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区域表';

-- ----------------------------
-- Records of sys_area
-- ----------------------------
INSERT INTO `sys_area` VALUES ('1', null, null, '0', '0,', '中国', '10', '100000', '1', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_area` VALUES ('2', null, null, '1', '0,1,', '北京', '20', '110000', '2', '1', '2013-05-27 08:00:00', '1', '2016-09-24 11:41:28', '', '0');
INSERT INTO `sys_area` VALUES ('3', null, null, '2', '0,1,2,', '海淀', '30', '110101', '3', '1', '2013-05-27 08:00:00', '1', '2016-09-24 11:41:37', '', '0');
INSERT INTO `sys_area` VALUES ('4', null, null, '3', '0,1,2,3,', '紫竹院', '40', '110102', '4', '1', '2013-05-27 08:00:00', '1', '2016-09-24 11:41:54', '', '0');
INSERT INTO `sys_area` VALUES ('5', null, null, '3', '0,1,2,3,', '车道沟', '50', '110104', '4', '1', '2013-05-27 08:00:00', '1', '2016-09-24 11:42:02', '', '0');
INSERT INTO `sys_area` VALUES ('6', null, null, '3', '0,1,2,3,', '中关村', '60', '110105', '4', '1', '2013-05-27 08:00:00', '1', '2016-09-24 11:42:11', '', '0');
INSERT INTO `sys_area` VALUES ('700e4849-747a-4cef-939e-3c04b4283fcb', '中关村', 'null_111111', '6', '0,1,2,3,,6', '111111', '1', '', '', 'admin', '2017-04-01 15:27:46', 'admin', '2017-04-01 15:27:46', '', '0');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `value` varchar(100) NOT NULL COMMENT '数据值',
  `label` varchar(100) NOT NULL COMMENT '标签名',
  `type` varchar(100) NOT NULL COMMENT '类型',
  `description` varchar(100) NOT NULL COMMENT '描述',
  `sort` decimal(10,0) NOT NULL COMMENT '排序（升序）',
  `parent_id` varchar(64) DEFAULT '0' COMMENT '父级编号',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`value`,`id`),
  KEY `sys_dict_value` (`value`) USING BTREE,
  KEY `sys_dict_label` (`label`) USING BTREE,
  KEY `sys_dict_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('no', '0', '否', 'YesOrNo', '是与否', '0', '0', 'dev', '2016-03-18 20:02:34', 'admin', '2017-03-21 13:50:13', '', '0');
INSERT INTO `sys_dict` VALUES ('yes', '1', '是', 'YesOrNo', '是与否', '0', '0', 'dev', '2016-03-18 20:02:34', 'dev', '2016-03-18 20:02:34', null, '0');
INSERT INTO `sys_dict` VALUES ('a7c6c310-6b26-401a-ad7e-74b187702d08', '2112', '21', '2121', '212', '12', null, 'admin', '2017-03-29 14:55:25', 'admin', '2017-03-29 14:55:48', '21', '1');
INSERT INTO `sys_dict` VALUES ('1e251f5c-94bb-4d4d-8ec2-892e7e33e056', 'abolish', '作废', 'theme_status', '模板状态', '3', null, 'admin', '2017-04-06 15:07:08', 'admin', '2017-04-06 15:07:08', '', '0');
INSERT INTO `sys_dict` VALUES ('73f8657a-829f-4f1d-b30a-dbc0921885c9', 'bcb', '包虫病', 'epidemic_name', '疫情', '1', null, 'admin', '2017-03-24 14:45:18', 'admin', '2017-03-24 14:45:18', '', '0');
INSERT INTO `sys_dict` VALUES ('c135b257-84ba-480a-9c02-ad364a2601b2', 'bf', '暴发', 'epidemic_type', '疫情种类', '2', null, 'admin', '2017-03-24 15:26:04', 'admin', '2017-03-24 15:26:04', '', '0');
INSERT INTO `sys_dict` VALUES ('b418a0e8-69ee-4e73-b1d2-98b5efbb55ed', 'blsjb', '布鲁氏菌病', 'epidemic_name', '疫情', '5', null, 'admin', '2017-03-24 14:57:57', 'admin', '2017-04-19 09:46:35', '', '0');
INSERT INTO `sys_dict` VALUES ('a7254b58-5884-4f21-bab2-8f0f16c62035', 'confirmed', '审核通过', 'review_status', '审核状态', '20', null, 'admin', '2017-03-30 09:35:33', 'admin', '2017-04-06 15:11:38', '', '0');
INSERT INTO `sys_dict` VALUES ('52f05b00-82e2-4b74-98c3-68e36c68fc03', 'draft', '草稿', 'review_status', '审核状态', '5', null, 'admin', '2017-04-06 11:06:04', 'admin', '2017-04-06 11:06:04', '', '0');
INSERT INTO `sys_dict` VALUES ('8a46bf79-b841-4a05-aca5-0125c951a655', 'dsh', '待审核', 'q_type', '1', '1', null, 'admin', '2017-03-30 17:30:53', 'admin', '2017-03-30 17:31:25', '', '1');
INSERT INTO `sys_dict` VALUES ('fb356291-717f-4489-8fec-2c1637aeb41f', 'dwbg', '单位报告', 'event_source', '事件来源', '2', null, 'admin', '2017-03-28 11:26:33', 'admin', '2017-03-28 11:26:33', '', '0');
INSERT INTO `sys_dict` VALUES ('208951dd-ce83-42f9-9f37-bd265cb31deb', 'dyqw', '大疫情网', 'event_source', '事件来源', '1', null, 'admin', '2017-03-28 11:24:57', 'admin', '2017-03-28 11:24:57', '', '0');
INSERT INTO `sys_dict` VALUES ('e0f4b116-d7bd-4362-ab74-15c054d5765e', 'dzb', '对照表', 'theme_type', '问卷类型', '2', null, 'admin', '2017-04-01 15:26:08', 'admin', '2017-04-19 09:45:08', '', '0');
INSERT INTO `sys_dict` VALUES ('6c321664-07a5-4442-9dd0-398336603272', 'editing', '草稿', 'theme_status', '模板状态', '1', null, 'admin', '2017-04-06 11:08:24', 'admin', '2017-04-06 11:08:24', '', '0');
INSERT INTO `sys_dict` VALUES ('6adda6f0-b75a-457d-8b0c-387766938651', 'fxb', '其他感染性腹泻病', 'epidemic_name', '疫情', '8', null, 'admin', '2017-03-24 14:52:44', 'admin', '2017-04-19 09:47:09', '', '0');
INSERT INTO `sys_dict` VALUES ('fe35c38e-39b3-4a49-acf5-c23ebaed6278', 'gab', '个案表', 'theme_type', '问卷类型', '1', null, 'admin', '2017-04-01 15:24:16', 'admin', '2017-04-01 15:24:16', '', '0');
INSERT INTO `sys_dict` VALUES ('eadedc50-9db0-4642-bb88-682c351e9736', 'gdlxtb', '钩端螺旋体病', 'epidemic_name', '疫情', '7', null, 'admin', '2017-03-24 14:59:07', 'admin', '2017-04-19 09:47:01', '', '0');
INSERT INTO `sys_dict` VALUES ('22c86a04-9466-4741-8868-70cdc670bd2e', 'jcqkb', '基础情况表', 'theme_type', '问卷类型', '5', null, 'admin', '2017-04-01 15:25:34', 'admin', '2017-04-19 09:45:40', '', '0');
INSERT INTO `sys_dict` VALUES ('3f663f71-2f64-440b-8d06-0d9eee37428d', 'jj', '聚集', 'epidemic_type', '疫情种类', '3', null, 'admin', '2017-03-24 15:26:23', 'admin', '2017-03-24 15:26:23', '', '0');
INSERT INTO `sys_dict` VALUES ('775d5277-974c-4626-80e5-ccf07ffbcbe6', 'other', '其它', 'epidemic_name', '疫情', '9', null, 'admin', '2017-04-19 09:36:28', 'admin', '2017-04-19 09:36:28', '', '0');
INSERT INTO `sys_dict` VALUES ('dd725df1-439c-4c81-8399-505eec269602', 'publish', '发布', 'theme_status', '模板状态', '2', null, 'admin', '2017-04-06 11:09:20', 'admin', '2017-04-06 11:09:20', '', '0');
INSERT INTO `sys_dict` VALUES ('153a72f6-7007-4229-a262-5d2a59c3dbb5', 'qt', '其它', 'theme_type', '问卷类型', '6', null, 'admin', '2017-04-01 15:28:17', 'admin', '2017-04-01 15:28:17', '', '0');
INSERT INTO `sys_dict` VALUES ('41281dcb-8d06-4d15-95ac-805b8efd4240', 'rollback', '退回', 'review_status', '审核状态', '30', null, 'admin', '2017-03-30 09:35:50', 'admin', '2017-03-30 09:35:50', '', '0');
INSERT INTO `sys_dict` VALUES ('991ad24d-84bd-4bc7-8b07-ee51ebce997e', 'sf', '散发', 'epidemic_type', '疫情种类', '4', null, 'admin', '2017-03-28 10:38:09', 'admin', '2017-03-28 13:25:39', '', '1');
INSERT INTO `sys_dict` VALUES ('cf3018d9-2a40-4000-9491-62b7fb1a51c9', 'sh', '伤寒和副伤寒', 'epidemic_name', '疫情', '6', null, 'admin', '2017-03-24 14:56:45', 'admin', '2017-04-19 09:46:53', '', '0');
INSERT INTO `sys_dict` VALUES ('17df5569-02bc-437a-87b5-6c1f558ce2da', 'sjtz', '上级通知', 'event_source', '事件来源', '3', null, 'admin', '2017-03-28 11:27:39', 'admin', '2017-03-28 11:27:39', '', '0');
INSERT INTO `sys_dict` VALUES ('sys_admin', 'sys_admin', '系统管理', 'user_type', '用户类型', '10', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('sys_admin_stall', 'sys_admin_stall', '科员', 'user_type', '用户类型', '30', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('sys_user_se', 'sys_user_se', '科长', 'user_type', '用户类型', '20', '0', '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_dict` VALUES ('sys_user_se', 'sys_user_use', '用户', 'user_type', '用户类别', '0', '0', 'dev', '2016-03-23 15:41:52', 'dev', '2016-03-23 15:41:57', null, '0');
INSERT INTO `sys_dict` VALUES ('a5ba71a7-b933-4514-aee9-ea37e2862f07', 'szk', '手足口', 'epidemic_name', '疫情', '3', null, 'admin', '2017-03-24 14:53:06', 'admin', '2017-04-19 09:46:12', '', '0');
INSERT INTO `sys_dict` VALUES ('4362f553-8379-455d-9754-d13d32cd1a23', 'tf', '突发', 'epidemic_type', '疫情种类', '1', null, 'admin', '2017-03-24 15:25:32', 'admin', '2017-03-24 15:25:32', '', '0');
INSERT INTO `sys_dict` VALUES ('62348876-197d-4273-b0b3-f19cbc47e1f3', 'uncheck', '待审核', 'review_status', '审核状态', '10', null, 'admin', '2017-03-30 09:35:09', 'admin', '2017-03-30 09:35:09', '', '0');
INSERT INTO `sys_dict` VALUES ('dc01b134-2687-46ab-8ab9-2e473759ba6d', 'xcb', '线虫病', 'epidemic_name', '疫情', '2', null, 'admin', '2017-03-24 14:51:24', 'admin', '2017-03-24 14:51:24', '', '0');
INSERT INTO `sys_dict` VALUES ('f33fe3e1-569a-47af-aecf-e4c818724372', 'xhr', '猩红热', 'epidemic_name', '疫情', '4', null, 'admin', '2017-03-24 14:57:06', 'admin', '2017-04-19 09:46:21', '', '0');
INSERT INTO `sys_dict` VALUES ('3990ac3f-c59b-457f-b6d3-2597021daa91', 'ybcy', '样本采样', 'theme_type', '问卷类型', '4', null, 'admin', '2017-04-01 15:27:50', 'admin', '2017-04-19 09:45:32', '', '0');
INSERT INTO `sys_dict` VALUES ('e6a5caa1-bf18-419a-a9ac-97b0150b6ce4', 'ylb', '一览表', 'theme_type', '问卷类型', '3', null, 'admin', '2017-04-01 15:26:51', 'admin', '2017-04-19 09:45:20', '', '0');
INSERT INTO `sys_dict` VALUES ('21560d42-3a48-491e-97fb-ba4cf8642c11', '测试', '12', '12', '21我去', '21', null, 'admin', '2017-04-06 09:12:48', 'admin', '2017-04-06 09:13:02', '21', '1');

-- ----------------------------
-- Table structure for sys_hello
-- ----------------------------
DROP TABLE IF EXISTS `sys_hello`;
CREATE TABLE `sys_hello` (
  `id` varchar(64) NOT NULL COMMENT 'id',
  `name` varchar(64) NOT NULL COMMENT '用户名',
  `sex` varchar(100) DEFAULT NULL COMMENT '性别',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` int(2) DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试';

-- ----------------------------
-- Records of sys_hello
-- ----------------------------
INSERT INTO `sys_hello` VALUES ('12a', '王五', ' 男', '1', '2017-01-18 17:06:23', '1', '2017-01-18 17:06:27', '1', '0');
INSERT INTO `sys_hello` VALUES ('12q', 'qa', '1', '2', '2017-01-17 08:31:54', '1', '2017-01-17 08:31:58', '1', '0');
INSERT INTO `sys_hello` VALUES ('1fbcffef-1f31-464c-ba7c-87d3f7ec20f1', '1', '1', 'admin', '2017-02-13 18:07:48', 'admin', '2017-02-13 18:07:48', null, '0');
INSERT INTO `sys_hello` VALUES ('402880a959af58d20159af59540f0000', '数据源管理', '女', 'admin', '2017-01-18 10:13:04', 'admin', '2017-01-18 10:13:04', null, '0');
INSERT INTO `sys_hello` VALUES ('402880a959af58d20159af5aca650001', '我', '女31', null, '2017-01-18 17:05:40', 'admin', '2017-01-18 17:05:40', null, '0');
INSERT INTO `sys_hello` VALUES ('402880a959af58d20159af5b5e2c0002', '数据源管理', '女31', 'admin', '2017-01-18 10:15:17', 'admin', '2017-01-18 10:15:17', null, '0');
INSERT INTO `sys_hello` VALUES ('402880c559b0c7750159b0d3427b0000', '1', '22', 'admin', '2017-01-18 17:05:52', 'admin', '2017-01-18 17:05:52', null, '0');
INSERT INTO `sys_hello` VALUES ('402880c559b0c7750159b0d4f9d60001', '1', '1', 'admin', '2017-01-18 17:07:44', 'admin', '2017-01-18 17:07:44', null, '0');
INSERT INTO `sys_hello` VALUES ('402880c559b11ca80159b11d9f920000', '数据源管理', '11', 'admin', '2017-01-18 18:27:05', 'admin', '2017-01-18 18:27:05', null, '0');
INSERT INTO `sys_hello` VALUES ('d679dfc6-aaab-4194-a491-7198e210c0dc', '1', '1', 'admin', '2017-02-14 08:53:24', 'admin', '2017-02-14 08:53:24', null, '0');
INSERT INTO `sys_hello` VALUES ('f399d015-f4aa-4eec-af0b-a2c6fe2f0321', '1', '1', 'admin', '2017-02-13 18:04:54', 'admin', '2017-02-13 18:04:54', null, '0');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `type` char(1) DEFAULT '1' COMMENT '日志类型',
  `title` varchar(255) DEFAULT '' COMMENT '日志标题',
  `status` varchar(10) DEFAULT NULL COMMENT '状态码',
  `message` varchar(200) DEFAULT NULL COMMENT '信息',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `remote_addr` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `user_agent` varchar(255) DEFAULT NULL COMMENT '用户代理',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `method` varchar(5) DEFAULT NULL COMMENT '操作方式',
  `params` text COMMENT '操作提交的数据',
  `starttime` double DEFAULT NULL COMMENT '开始时间',
  `endtiime` double DEFAULT NULL COMMENT '结束时间',
  `protime` double DEFAULT NULL COMMENT '耗时',
  `del_flag` int(11) DEFAULT NULL,
  `exception` text COMMENT '异常信息',
  `remarks` varchar(200) DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sys_log_create_by` (`create_by`) USING BTREE,
  KEY `sys_log_request_uri` (`request_uri`) USING BTREE,
  KEY `sys_log_type` (`type`) USING BTREE,
  KEY `sys_log_create_date` (`create_date`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('15df9617-01bb-4772-9545-68be492ce0bd', '2', '功能菜单_系统管理_系统管理_日志管理', '0', '正常', 'admin', '2017-04-19 13:08:53', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0', '/ldemo/sysLog/getPage.do', 'GET', 'menuId=sys_log_mg', '1492578533872', '1492578533897', '0.025', '0', null, null, 'admin', '2017-04-19 13:08:53');
INSERT INTO `sys_log` VALUES ('20f90c31-509c-49f1-b938-e6cf25c275ba', '2', '功能菜单_系统管理_系统管理_字典管理', '0', '正常', 'admin', '2017-04-19 13:08:52', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0', '/ldemo/sysDict/getPage', 'GET', 'menuId=sys_dict_mg', '1492578532679', '1492578532728', '0.049', '0', null, null, 'admin', '2017-04-19 13:08:52');
INSERT INTO `sys_log` VALUES ('2b1deb59-9dfd-4dd2-a049-b6a062c8d420', '2', '登录', '0', '正常', null, '2017-04-19 13:31:29', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0', '/ldemo/base/site/css/H-ui.login.css', 'GET', '', '1492579888586', '1492579888737', '0.151', '0', null, null, null, '2017-04-19 13:31:29');
INSERT INTO `sys_log` VALUES ('5b6ffff3-fe5a-44c7-aa44-7f71b380d931', '2', '登录', '0', '正常', null, '2017-04-19 13:31:30', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0', '/ldemo/base/site/images/admin-login-bg.jpg', 'GET', '', '1492579890122', '1492579890177', '0.055', '0', null, null, null, '2017-04-19 13:31:30');
INSERT INTO `sys_log` VALUES ('6da9b695-5cc5-45a0-a5d9-a12144aebd30', '2', '功能菜单_系统管理_系统管理_日志管理', '0', '正常', 'admin', '2017-04-19 13:08:55', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0', '/ldemo/sysLog/getPage.do', 'GET', 'menuId=sys_log_mg', '1492578535739', '1492578535761', '0.022', '0', null, null, 'admin', '2017-04-19 13:08:55');
INSERT INTO `sys_log` VALUES ('7b2d0d12-afd2-499c-a64d-d34a7ee75457', '2', '登录', '0', '正常', null, '2017-04-19 13:31:30', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0', '/ldemo/base/site/images/admin-loginform-bg.png', 'GET', '', '1492579890105', '1492579890110', '0.005', '0', null, null, null, '2017-04-19 13:31:30');
INSERT INTO `sys_log` VALUES ('dc6993ca-b8ed-4c42-90ce-4fae5ed2c614', '2', '登录', '0', '正常', 'admin', '2017-04-19 13:31:33', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0', '/ldemo/loginsuc', 'GET', '', '1492579892601', '1492579893023', '0.422', '0', null, null, 'admin', '2017-04-19 13:31:33');
INSERT INTO `sys_log` VALUES ('e25c28e2-91ad-4b25-ac3b-7bd96e607b52', '2', '登录', '0', '正常', null, '2017-04-19 13:31:30', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0', '/ldemo/base/site/css/H-ui.login.css', 'GET', '', '1492579890118', '1492579890122', '0.004', '0', null, null, null, '2017-04-19 13:31:30');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `parent_name` varchar(100) DEFAULT NULL COMMENT '父名称',
  `full_name` varchar(2000) DEFAULT NULL COMMENT '全名称',
  `parent_id` varchar(64) NOT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) NOT NULL COMMENT '所有父级编号',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `sort` decimal(10,0) NOT NULL COMMENT '排序',
  `href` varchar(2000) DEFAULT NULL COMMENT '链接',
  `target` varchar(20) DEFAULT NULL COMMENT '目标',
  `iconimg` varchar(100) DEFAULT NULL COMMENT '图标',
  `is_show` char(1) NOT NULL COMMENT '是否在菜单中显示',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_menu_parent_id` (`parent_id`) USING BTREE,
  KEY `sys_menu_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1397e031-af6c-485c-a3f2-f2189b8ac308', '功能菜单', '功能菜单_系统管理', 'root', 'root', '系统管理', '100009', '', '', '', '0', 'ld:index:sys', 'admin', '2017-04-17 14:18:16', 'admin', '2017-04-17 15:35:16', null, '0');
INSERT INTO `sys_menu` VALUES ('402880f653ac86fd0153ac8947810000', '系统管理', '功能菜单_系统管理_系统管理', '1397e031-af6c-485c-a3f2-f2189b8ac308', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308', '系统管理', '100', '', '', 'base/images/tubiao/xitong.png', '1', '', '陈友华', '2016-03-25 14:49:51', 'admin', '2017-04-17 14:18:22', null, '0');
INSERT INTO `sys_menu` VALUES ('402880f653ac86fd0153ac8bc35f0001', '系统管理', '功能菜单_系统管理_字典信息', '1397e031-af6c-485c-a3f2-f2189b8ac308', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308', '字典信息', '200019', '', '', 'base/images/tubiao/zidian.png', '1', '', '陈友华', '2016-03-25 14:52:33', 'admin', '2017-04-17 14:18:22', null, '0');
INSERT INTO `sys_menu` VALUES ('48a89ed1-5dec-4c3d-b869-f0ff6244cc5f', '系统管理', '功能菜单_系统管理_系统管理_预警配置', '402880f653ac86fd0153ac8947810000', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000', '修改预警配置', '300049', 'lcWarnConfig/openLcWarnUpdate.do', '', '', '0', 'lcWarn:lcWarn:update', 'admin', '2017-03-29 10:01:51', 'admin', '2017-04-18 15:42:55', null, '0');
INSERT INTO `sys_menu` VALUES ('80387a68-6b2d-4f60-8cfe-055069093fd4', '行政区域', '功能菜单_系统管理_系统管理_行政区域_修改行政区域', 'tree_area_viewtree', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000,tree_area_viewtree', '修改行政区域', '300083', '', '', '', '0', 'sys:sysArea:update', 'admin', '2017-04-01 14:55:25', 'admin', '2017-04-18 15:54:58', null, '0');
INSERT INTO `sys_menu` VALUES ('844d8b8c-90a2-46aa-af01-4c796954f093', '系统管理', '功能菜单_系统管理_系统管理_预警配置', '402880f653ac86fd0153ac8947810000', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000', '添加预警配置', '300055', 'lcWarnConfig/openLcWarnSave.do', '', '', '0', 'lcWarn:lcWarn:save', 'admin', '2017-03-28 11:27:07', 'admin', '2017-04-18 15:42:46', null, '0');
INSERT INTO `sys_menu` VALUES ('b0bd65c8-d02c-4ec1-85eb-918a139b1923', '系统管理', '功能菜单_系统管理_系统管理_预警配置', '402880f653ac86fd0153ac8947810000', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000', '删除预警配置', '300050', 'lcWarnConfig/lcWarnDeleteByIds.do', '', '', '0', 'lcWarn:lcWarn:delete', 'admin', '2017-03-29 15:50:53', 'admin', '2017-04-18 15:43:04', null, '0');
INSERT INTO `sys_menu` VALUES ('d2ad3061-9609-42ed-8856-563c94bc1ffe', '行政区域', '功能菜单_系统管理_系统管理_行政区域_删除行政区域', 'tree_area_viewtree', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000,tree_area_viewtree', '删除行政区域', '300007', '', '', '', '0', 'sys:sysArea:delete', 'admin', '2017-03-05 19:37:56', 'admin', '2017-04-18 15:56:10', null, '0');
INSERT INTO `sys_menu` VALUES ('e7d4d882-4090-4789-ae89-37efd7073c36', '行政区域', '功能菜单_系统管理_系统管理_行政区域_添加行政区域', 'tree_area_viewtree', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000,tree_area_viewtree,tree_area_viewtree', '添加行政区域', '300005', null, null, null, '0', 'sys:sysArea:save', 'admin', '2017-03-05 11:11:06', 'admin', '2017-04-18 15:54:58', null, '0');
INSERT INTO `sys_menu` VALUES ('root', '功能菜单', '功能菜单', 'root', 'root', '功能菜单', '0', null, null, null, '0', null, '1', '2013-05-27 08:00:00', '1', '2013-05-27 08:00:00', null, '0');
INSERT INTO `sys_menu` VALUES ('sys_dict_delete', '字典管理', '功能菜单_系统管理_系统管理_字典管理_字典管理_删除字典', 'sys_dict_mg', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000,sys_dict_mg', '删除字典', '100', '', null, 'icon-pencil', '0', 'sys:sysDict:delete', '陈友华', '2016-03-24 00:00:00', 'admin', '2017-04-17 14:18:22', null, '0');
INSERT INTO `sys_menu` VALUES ('sys_dict_mg', '系统管理', '功能菜单_系统管理_系统管理_字典管理', '402880f653ac86fd0153ac8947810000', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000', '字典管理', '7', 'sysDict/getPage?menuId=sys_dict_mg', '', 'icon-pencil', '1', 'sys:sysDict:view', '陈友华', '2016-03-24 00:00:00', 'admin', '2017-04-18 14:42:05', null, '0');
INSERT INTO `sys_menu` VALUES ('sys_dict_save', '字典管理', '功能菜单_系统管理_系统管理_字典管理_添加字典', 'sys_dict_mg', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000,sys_dict_mg', '添加字典', '100', 'sys/openSysDictSave.do?menuId=sys_dict_save', '', 'icon-pencil', '0', 'sys:sysDict:save', '陈友华', '2016-03-24 00:00:00', 'admin', '2017-04-17 14:18:22', null, '0');
INSERT INTO `sys_menu` VALUES ('sys_dict_update', '字典管理', '功能菜单_系统管理_系统管理_字典管理_字典管理_修改字典', 'sys_dict_mg', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000,sys_dict_mg', '修改字典', '100', 'sysDict/openUpdate.do?menuId=sys_dict_update', null, 'icon-pencil', '0', 'sys:sysDict:update', '陈友华', '2016-03-24 00:00:00', 'admin', '2017-04-17 14:18:22', null, '0');
INSERT INTO `sys_menu` VALUES ('sys_log_delete', '日志管理', '功能菜单_系统管理_系统管理_日志管理_删除日志', 'sys_log_mg', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000,sys_log_mg', '删除日志', '4', '', '', 'icon-pencil', '0', 'sys:sysLog:delete', '陈友华', '2016-04-09 00:00:00', 'admin', '2017-04-17 14:18:22', null, '0');
INSERT INTO `sys_menu` VALUES ('sys_log_mg', '系统管理', '功能菜单_系统管理_系统管理_日志管理', '402880f653ac86fd0153ac8947810000', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000', '日志管理', '1', 'sysLog/getPage.do?menuId=sys_log_mg', '', 'base/images/tubiao/xitong.png', '1', 'sys:sysLog:view', '陈友华', '2016-04-09 00:00:00', 'admin', '2017-04-18 14:31:55', null, '0');
INSERT INTO `sys_menu` VALUES ('sys_menu_delete', '菜单管理', '功能菜单_系统管理_系统管理_菜单管理_菜单管理_删除菜单', 'sys_menu_mg', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000,sys_menu_mg', '删除菜单', '100', '', null, 'icon-pencil', '0', 'sys:sysMenu:delete', '陈友华', '2016-03-24 00:00:00', 'admin', '2017-04-17 14:18:22', null, '0');
INSERT INTO `sys_menu` VALUES ('sys_menu_mg', '系统管理', '功能菜单_系统管理_系统管理_菜单管理', '402880f653ac86fd0153ac8947810000', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000', '菜单管理', '100', 'sys/sysMenuListTree?menuId=sys_menu_mg', '', 'icon-pencil', '1', 'sys:sysMenu:view', '陈友华', '2016-03-24 00:00:00', 'admin', '2017-04-18 14:44:07', null, '0');
INSERT INTO `sys_menu` VALUES ('sys_menu_save', '菜单管理', '功能菜单_系统管理_系统管理_菜单管理_菜单管理_添加菜单', 'sys_menu_mg', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000,sys_menu_mg', '添加菜单', '100', 'sys/openSysMenuSave?menuId=sys_menu_save', null, 'icon-pencil', '0', 'sys:sysMenu:save', '陈友华', '2016-03-24 00:00:00', 'admin', '2017-04-17 14:18:22', null, '0');
INSERT INTO `sys_menu` VALUES ('sys_menu_update', '菜单管理', '功能菜单_系统管理_系统管理_菜单管理_菜单管理_修改菜单', 'sys_menu_mg', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000,sys_menu_mg', '修改菜单', '100', 'sys/openSysMenuUpdate?menuId=sys_menu_update', null, 'icon-pencil', '0', 'sys:sysMenu:update', '陈友华', '2016-03-24 00:00:00', 'admin', '2017-04-17 14:18:22', null, '0');
INSERT INTO `sys_menu` VALUES ('sys_office_delete', '机构管理', '功能菜单_系统管理_系统管理_机构管理_机构管理_删除单位', 'sys_office_mg', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000,sys_office_mg', '删除单位', '100', '', null, 'icon-pencil', '0', 'sys:sysOffice:delete', '陈友华', '2016-03-24 00:00:00', 'admin', '2017-04-17 14:18:22', null, '0');
INSERT INTO `sys_menu` VALUES ('sys_office_mg', '系统管理', '功能菜单_系统管理_系统管理_机构管理', '402880f653ac86fd0153ac8947810000', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000', '机构管理', '100', 'sys/sysOfficeListTree.do?menuId=sys_office_viewtree', '', 'icon-pencil', '1', 'sys:sysOffice:view', '陈友华', '2016-03-24 00:00:00', 'admin', '2017-04-17 14:18:22', null, '0');
INSERT INTO `sys_menu` VALUES ('sys_office_save', '机构管理', '功能菜单_系统管理_系统管理_机构管理_添加单位', 'sys_office_mg', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000,sys_office_mg', '添加单位', '100', 'sys/openSysOfficeSave.do?menuId=sys_office_save', '', 'icon-pencil', '0', 'sys:sysOffice:save', '陈友华', '2016-03-24 00:00:00', 'admin', '2017-04-17 14:18:22', null, '0');
INSERT INTO `sys_menu` VALUES ('sys_office_update', '机构管理', '功能菜单_系统管理_系统管理_机构管理_修改单位', 'sys_office_mg', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000,sys_office_mg', '修改单位', '100', 'sys/openSysOfficeUpdate.do?menuId=sys_office_update', '', 'icon-pencil', '0', 'sys:sysOffice:update', '陈友华', '2016-03-24 00:00:00', 'admin', '2017-04-17 14:18:22', null, '0');
INSERT INTO `sys_menu` VALUES ('sys_user_delete', '用户管理', '功能菜单_系统管理_系统管理_用户管理_用户管理_删除用户', 'sys_user_mg', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000,sys_user_mg', '删除用户', '100', '', null, 'icon-pencil', '0', 'sys:sysUser:delete', '陈友华', '2016-03-24 00:00:00', 'admin', '2017-04-17 14:18:22', null, '0');
INSERT INTO `sys_menu` VALUES ('sys_user_mg', '系统管理', '功能菜单_系统管理_系统管理_用户管理', '402880f653ac86fd0153ac8947810000', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000', '用户管理', '100', 'sysUser/getPage?menuId=sys_user_mg', '', 'icon-pencil', '1', 'sys:sysUser:view', '陈友华', '2016-03-24 00:00:00', 'admin', '2017-04-18 14:49:51', null, '0');
INSERT INTO `sys_menu` VALUES ('sys_user_save', '用户管理', '功能菜单_系统管理_系统管理_用户管理_用户管理_添加用户', 'sys_user_mg', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000,sys_user_mg', '添加用户', '100', 'sysUser/openSave.do?menuId=sys_user_save', null, 'icon-pencil', '0', 'sys:sysUser:save', '陈友华', '2016-03-24 00:00:00', 'admin', '2017-04-17 14:18:22', null, '0');
INSERT INTO `sys_menu` VALUES ('sys_user_update', '用户管理', '功能菜单_系统管理_系统管理_用户管理_用户管理_修改用户', 'sys_user_mg', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000,sys_user_mg', '修改用户', '100', 'sysUser/openUpdate.do?menuId=sys_user_update', null, 'icon-pencil', '0', 'sys:sysUser:update', '陈友华', '2016-03-24 00:00:00', 'admin', '2017-04-17 14:18:22', null, '0');
INSERT INTO `sys_menu` VALUES ('tree_area_viewtree', '系统管理', '功能菜单_系统管理_系统管理_行政区域', '402880f653ac86fd0153ac8947810000', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8947810000', '行政区域', '10', 'sys/sysAreaListTree?menuId=tree_area_viewtree', '', 'icon-pencil', '1', 'sys:sysArea:view', '陈友华', '2016-03-24 00:00:00', 'admin', '2017-04-18 15:55:42', null, '0');
INSERT INTO `sys_menu` VALUES ('tree_placetype_viewtree', '字典信息', '功能菜单_系统管理_字典信息_场所类型', '402880f653ac86fd0153ac8bc35f0001', 'root,1397e031-af6c-485c-a3f2-f2189b8ac308,402880f653ac86fd0153ac8bc35f0001', '场所类型', '300014', 'treePlacetype/getPageTree.do?menuId=tree_placetype_viewtree', '', 'icon-pencil', '0', 'tree:treePlacetype:view', '陈友华', '2016-03-24 00:00:00', 'admin', '2017-04-19 13:07:20', null, '0');

-- ----------------------------
-- Table structure for sys_office
-- ----------------------------
DROP TABLE IF EXISTS `sys_office`;
CREATE TABLE `sys_office` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `parent_name` varchar(100) DEFAULT NULL COMMENT '父名称',
  `full_name` varchar(2000) DEFAULT NULL COMMENT '全名称',
  `parent_id` varchar(64) DEFAULT NULL COMMENT '父级编号',
  `parent_ids` varchar(2000) DEFAULT NULL COMMENT '所有父级编号',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `sort` decimal(10,0) DEFAULT NULL COMMENT '排序',
  `tree_area_id` varchar(64) DEFAULT NULL COMMENT '归属区域',
  `code` varchar(100) DEFAULT NULL COMMENT '区域编码',
  `type` varchar(20) DEFAULT NULL COMMENT '机构类型',
  `grade` varchar(20) DEFAULT NULL COMMENT '机构等级',
  `address` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `zip_code` varchar(100) DEFAULT NULL COMMENT '邮政编码',
  `master` varchar(100) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
  `fax` varchar(200) DEFAULT NULL COMMENT '传真',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `USEABLE` varchar(64) DEFAULT NULL COMMENT '是否启用',
  `PRIMARY_PERSON` varchar(64) DEFAULT NULL COMMENT '主负责人',
  `DEPUTY_PERSON` varchar(64) DEFAULT NULL COMMENT '副负责人',
  `epidemic_ids` varchar(1000) DEFAULT NULL COMMENT '疫情id',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_office_parent_id` (`parent_id`) USING BTREE,
  KEY `sys_office_del_flag` (`del_flag`) USING BTREE,
  KEY `sys_office_type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构表';

-- ----------------------------
-- Records of sys_office
-- ----------------------------
INSERT INTO `sys_office` VALUES ('15257e35-5d03-4cb2-b45c-67ff27848340', '社区3', '组织机构_西城区疾控_西城区社区_社区3_新节点1', '88e8619c-aa40-4505-83b3-5f4706b10755', 'root,area,community,88e8619c-aa40-4505-83b3-5f4706b10755', '新节点1', '100002', '0', null, null, null, null, null, null, null, null, null, null, null, null, 'bcb,sh,szk', 'ypn', '2017-04-13 10:32:23', 'admin', '2017-04-13 15:45:17', null, '0');
INSERT INTO `sys_office` VALUES ('18e1fbbd-102c-4e9f-a015-cb9a2c7d3d1b', '科室1', '组织机构_西城区疾控_科室1_科室3', 'area', 'root,area,1be9affc-a3e0-4107-a073-35a29c801067', '科室3', '200011', '0', null, null, null, null, null, null, null, null, null, null, null, null, null, 'admin', '2017-04-11 16:13:29', 'admin', '2017-04-13 14:03:30', null, '0');
INSERT INTO `sys_office` VALUES ('1be9affc-a3e0-4107-a073-35a29c801067', '西城区疾控', '组织机构_西城区疾控_科室1', 'area', 'root,area', '科室1', '100003', '0', null, null, null, null, null, null, null, null, null, null, null, null, null, 'admin', '2017-04-11 16:13:25', 'admin', '2017-04-11 16:13:48', null, '0');
INSERT INTO `sys_office` VALUES ('777de16c-57a7-4a85-9ada-ffe6505375d3', '西城区疾控', '组织机构_西城区疾控_科室4', 'area', 'root,area', '科室4', '100006', '0', null, null, null, null, null, null, null, null, null, null, null, null, null, 'admin', '2017-04-13 10:18:45', 'admin', '2017-04-13 10:19:03', null, '0');
INSERT INTO `sys_office` VALUES ('88e8619c-aa40-4505-83b3-5f4706b10755', '西城区社区', '组织机构_西城区疾控_西城区社区_社区3', 'community', 'root,area,community', '社区3', '200003', '0', null, null, null, null, null, null, null, null, null, null, null, null, 'bcb,szk', 'admin', '2017-04-11 16:13:32', 'admin', '2017-04-13 17:20:38', null, '0');
INSERT INTO `sys_office` VALUES ('ae696f11-3473-4513-b615-68836f17fa5b', '科室3', '组织机构_西城区疾控_科室3_新节点5', '18e1fbbd-102c-4e9f-a015-cb9a2c7d3d1b', 'root,area,18e1fbbd-102c-4e9f-a015-cb9a2c7d3d1b', '新节点5', '200008', '0', null, null, null, null, null, null, null, null, null, null, null, null, null, 'admin', '2017-04-13 10:26:18', 'admin', '2017-04-13 10:26:18', null, '0');
INSERT INTO `sys_office` VALUES ('area', '组织机构', '组织机构_西城区疾控', 'root', 'root', '西城区疾控', '1', '2', null, null, null, null, null, null, null, null, null, null, null, null, null, 'admin', '2017-03-08 17:06:05', 'admin', '2017-03-08 17:06:24', null, '0');
INSERT INTO `sys_office` VALUES ('b4fbdfcd-89f8-42e6-b8a2-e35cc8d6d449', '科室3', '组织机构_西城区疾控_科室3_新节点4', '18e1fbbd-102c-4e9f-a015-cb9a2c7d3d1b', 'root,area,18e1fbbd-102c-4e9f-a015-cb9a2c7d3d1b', '新节点4', '200008', '0', null, null, null, null, null, null, null, null, null, null, null, null, null, 'admin', '2017-04-13 10:26:17', 'admin', '2017-04-13 10:26:17', null, '0');
INSERT INTO `sys_office` VALUES ('b95b5ecc-51c1-4184-884f-be099cec0bf7', '西城区社区', '组织机构_西城区疾控_西城区社区_社区1', 'community', 'root,area,community', '社区1', '200003', '0', null, null, null, null, null, null, null, null, null, null, null, null, null, 'admin', '2017-04-11 16:13:30', 'admin', '2017-04-11 16:13:54', null, '0');
INSERT INTO `sys_office` VALUES ('bc2a5bf2-c57c-46c2-ae09-773cb1348c6b', '西城区疾控', '组织机构_西城区疾控_科室2', 'area', 'root,area', '科室2', '100004', '0', null, null, null, null, null, null, null, null, null, null, null, null, null, 'admin', '2017-04-11 16:13:28', 'admin', '2017-04-11 16:13:43', null, '0');
INSERT INTO `sys_office` VALUES ('community', '西城区疾控', '组织机构_西城区疾控_西城区社区', 'area', 'root,area', '西城区社区', '20', '2', null, null, null, null, null, null, null, null, null, null, null, null, null, 'admin', '2017-03-09 11:24:27', 'admin', '2017-03-09 13:07:01', null, '0');
INSERT INTO `sys_office` VALUES ('dd4eb52d-123d-4123-b5c2-c5b7a9164772', '西城区社区', '组织机构_西城区疾控_西城区社区_社区2', 'community', 'root,area,community', '社区2', '200003', '0', null, null, null, null, null, null, null, null, null, null, null, null, null, 'admin', '2017-04-11 16:13:31', 'admin', '2017-04-11 16:13:58', null, '0');
INSERT INTO `sys_office` VALUES ('eef4cb8d-d4f0-469c-be43-1772439458e9', '科室1', '组织机构_西城区疾控_科室1_1小组', '1be9affc-a3e0-4107-a073-35a29c801067', 'root,area,1be9affc-a3e0-4107-a073-35a29c801067', '1小组', '200008', '0', null, null, null, null, null, null, null, null, null, null, null, null, null, 'admin', '2017-04-14 10:07:37', 'admin', '2017-04-14 10:07:57', null, '0');

-- ----------------------------
-- Table structure for sys_office_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_office_sys_menu`;
CREATE TABLE `sys_office_sys_menu` (
  `id` varchar(64) NOT NULL COMMENT 'id',
  `sys_office_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '单位id',
  `sys_menu_id` varchar(64) NOT NULL COMMENT '功能id',
  `create_by` varchar(64) DEFAULT NULL COMMENT 'create_by',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT 'update_by',
  `update_date` datetime DEFAULT NULL COMMENT 'update_date',
  `remarks` varchar(225) DEFAULT NULL COMMENT '备注信息',
  `del_flag` int(11) DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户权限表';

-- ----------------------------
-- Records of sys_office_sys_menu
-- ----------------------------
INSERT INTO `sys_office_sys_menu` VALUES ('00a57ccb-cf2c-4512-b0b0-e2df13ceb7f0', '88e8619c-aa40-4505-83b3-5f4706b10755', 'tree_placetype_viewtree', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('0355c3ab-3cc7-4961-b994-8d7316d6a394', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_user_mg', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('07243ef0-5eb6-43e8-975b-d524747efab7', '88e8619c-aa40-4505-83b3-5f4706b10755', '6cfdc6a1-0778-4ef7-940f-64cbbe178162', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('07f96655-d49a-4c29-8fb0-ed7e6e03fb19', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_dict_view', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('0c0c99d1-e6e8-4519-a1fe-f9094277f7e3', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_log_view', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('0d900540-db99-42e7-a62a-1aef88170944', '88e8619c-aa40-4505-83b3-5f4706b10755', '727adcba-a596-463c-a3bb-ed8959af857f', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('0f59cbbb-90a0-4520-8f93-1421435e3c7b', '88e8619c-aa40-4505-83b3-5f4706b10755', '083f6ffb-9b50-4955-8c6a-3ee973875f47', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('0f6fe2ac-2a22-4482-bc97-09d0ebf53a1a', '88e8619c-aa40-4505-83b3-5f4706b10755', 'tree_area_view', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('148c50c2-00dd-4592-8d2a-a9aa686c0f9d', '88e8619c-aa40-4505-83b3-5f4706b10755', '9c69eb61-9942-44a4-aaad-b3b6bdfc926c', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('1b31d25f-b3d2-4834-9abb-b2da9147a013', '88e8619c-aa40-4505-83b3-5f4706b10755', '402880ee572115f50157211677180000', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('1e1dee7c-29ae-41ba-925b-bb205c94cb70', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_menu_update', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('1f275faf-3f13-460c-a4cc-bde3cf185757', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_user_save', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('1ff45e81-b23d-4efa-b544-2b7e00cc28f7', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_log_output', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('21eddb4b-391a-4103-92f3-b507ed7b4ad3', '88e8619c-aa40-4505-83b3-5f4706b10755', '08e0a723-3dca-4e99-ba62-082e0e63b9ce', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('23584e40-1b1f-4913-ab51-83d8854202bb', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_office_output', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('240a7f3c-091f-42aa-8d0d-7798f3be4392', '88e8619c-aa40-4505-83b3-5f4706b10755', 'e7d4d882-4090-4789-ae89-37efd7073c36', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('2459d200-1698-40cc-ac1a-d60feb8b1035', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_dict_save', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('2765314c-b7f7-44c3-bb07-d785020b7bba', '88e8619c-aa40-4505-83b3-5f4706b10755', 'a37cbcd0-6949-4890-a105-1e101bf46ddf', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('292ab134-9dd2-438e-b15e-135a1efcb2f2', '88e8619c-aa40-4505-83b3-5f4706b10755', 'f3c08c0f-6fd2-4b00-8f93-381a237b7883', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('2942cbad-8fd7-4837-b5ed-144329455208', '88e8619c-aa40-4505-83b3-5f4706b10755', '2b79f6b9-58e1-4856-99b4-368c9e8ef967', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('2a88979a-a73e-482e-8ea7-8490af068a4e', '88e8619c-aa40-4505-83b3-5f4706b10755', '575f', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('34a5d693-03a4-471e-b251-40066491d511', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_log_input', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('3778b3f0-4c3c-4ceb-a16a-75f432998c30', '88e8619c-aa40-4505-83b3-5f4706b10755', '51bf984b-3998-4599-8b66-db85239e453f', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('3abd1631-d6d8-422b-b302-eba844da5dc5', '88e8619c-aa40-4505-83b3-5f4706b10755', '06d64d2a-bc5e-48e3-8f30-096d2db76ead', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('3b42960c-983c-41b6-b5cf-b0a1bc6992d2', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_log_delete', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('43046672-5dbc-4691-bfb1-c011bfb9fcb5', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_log_save', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('4433e14c-bb97-48c2-99ae-4fa3e62eb4a0', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_menu_output', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('488842c5-11c7-4db8-aefd-05545d97ee68', '88e8619c-aa40-4505-83b3-5f4706b10755', '575fb7sfa', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('4cf64175-5f21-491d-a967-900568bb933c', '88e8619c-aa40-4505-83b3-5f4706b10755', '53fbb192-3fa0-46d1-97c7-de4ba60dde3e', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('5ace55a7-fc03-4ff3-957d-6bf77036041c', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_menu_input', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('5c541faa-d703-4530-b349-7b35a433d2cb', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_dict_update', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('5e2c67ef-71cc-494f-a136-94488265c97c', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_dict_input', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('6190b034-642a-4602-81bf-0abdfdc4cd42', '88e8619c-aa40-4505-83b3-5f4706b10755', '402880f653ac86fd0153ac8bc35f0001', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('63ed857f-7b21-4b8f-90ac-d25d49af5324', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_office_save', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('665df497-3a15-4510-aac5-20261f3ea7eb', '88e8619c-aa40-4505-83b3-5f4706b10755', 'tree_area_viewtree', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('68e2b9e7-7c64-42bb-b151-e231e5e35f3a', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_user_update', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('6a7f0434-2465-48c9-98e3-5115ae00cca1', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_menu_save', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('6ca837c5-89eb-4744-a3bc-ce79e80c66e3', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_log_update', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('6e00360a-be8a-4cb5-b8bd-db817d8a6358', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_office_view', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('7b4b0a26-764c-4adc-87b7-d0f94cec25d8', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_office_viewtree', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('7c4b9fb5-8cdd-4c25-8e3f-9d334be5397c', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_office_input', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('7ca9ddc7-5057-41dd-8968-f0b4ade6eca8', '88e8619c-aa40-4505-83b3-5f4706b10755', '402880f653abc66f0153abca584d0000', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('85097d3f-52ea-4b10-800c-94c924710a7c', '88e8619c-aa40-4505-83b3-5f4706b10755', '575fb3e6-6285-4ff5-8498-d9ebd767c7fa', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('950b9da4-43ae-4fd9-8084-4f989b9011e8', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_menu_mg', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('963f0ab9-c99d-480a-bcf6-e062c158c2ff', '88e8619c-aa40-4505-83b3-5f4706b10755', '48a89ed1-5dec-4c3d-b869-f0ff6244cc5f', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('9a5ce70c-3b9a-4694-9792-0725ba48bbdc', '88e8619c-aa40-4505-83b3-5f4706b10755', '489749bf-c693-41f0-b7a6-4300ee464060', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('9b5335f6-da89-4479-9ff6-cabcabefcad1', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_office_update', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('9c6e79c2-e644-400f-9b61-4c280e5a274f', '88e8619c-aa40-4505-83b3-5f4706b10755', 'ce6f645e-2df9-42c6-9ce3-37c08445fb79', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('9fdab262-e809-4031-985d-5c90d06edd78', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_dict_delete', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('a5c497a3-5cf5-4e4c-908c-cf37563ee044', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_user_input', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('a8de9db9-1250-467b-87ee-b6288683f979', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_office_mg', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('ab4de2d1-cca5-4fea-9fc2-86a50f8dc225', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_menu_view', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('abb1de95-254a-4289-95cc-7312cbe169a5', '88e8619c-aa40-4505-83b3-5f4706b10755', 'dd961040-d534-4122-8441-cd957d6848bc', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('abcef088-8fac-40f5-93ff-83d5357d43de', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_office_delete', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('b32284ff-7026-4cfe-84df-428134bc00d2', '88e8619c-aa40-4505-83b3-5f4706b10755', '402880f653ac86fd0153ac8947810000', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('ba63ded2-1f62-4959-b6d2-ace71101511d', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_log_mg', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('bdfd269d-410c-4a5c-b240-549ba9424eca', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_user_delete', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('ca903435-dcb4-4341-9fd4-fb8f9998e47f', '88e8619c-aa40-4505-83b3-5f4706b10755', 'b0bd65c8-d02c-4ec1-85eb-918a139b1923', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('ccd78c05-3057-46d2-af88-73d4b5f53fec', '88e8619c-aa40-4505-83b3-5f4706b10755', '819ea63e-e4c7-4166-9ece-7e72f48ff8fb', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('cd390d75-19f3-49d8-88e1-031e20d6b96a', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_user_view', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('ceb00b7e-47b7-4805-b649-ae0ed95c598a', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_dict_mg', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('d00fdf86-3606-40b5-91bb-14160df70db9', '88e8619c-aa40-4505-83b3-5f4706b10755', '82441402-0e23-4f51-ab81-e29c5d2cb321', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('d079345e-b236-4e85-8dfd-a229066c8ad4', '88e8619c-aa40-4505-83b3-5f4706b10755', '575fb7fa', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('d22237cb-f670-42b6-afb5-a33b2ccbcd5e', '88e8619c-aa40-4505-83b3-5f4706b10755', '04edf080-e8f1-40d5-9ad2-14f156ad9d13', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('d494ba57-2007-424d-abb6-aafe2601b695', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_user_output', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('d6f2c769-f7d0-4a59-9a28-b5845424d7b1', '88e8619c-aa40-4505-83b3-5f4706b10755', '39f8328f-4a44-4a21-9f4a-53b2a95356f2', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('df386ff1-541f-4478-ac57-782b5b25c9e8', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_menu_delete', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('e628e306-8f11-4f74-b356-1f9fd75286cb', '88e8619c-aa40-4505-83b3-5f4706b10755', '139fa6a6-dcc9-473a-94ef-9d67549996f5', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('e730a3c8-ecb0-4022-ac8c-b0fcc8e1227f', '88e8619c-aa40-4505-83b3-5f4706b10755', '80387a68-6b2d-4f60-8cfe-055069093fd4', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('f2ef574c-4fa4-4482-976a-f041c915959d', '88e8619c-aa40-4505-83b3-5f4706b10755', 'aefc778d-620c-4e0d-ab62-60771e885a44', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('f41416f6-97a8-4c34-9f40-4d32ade735e8', '88e8619c-aa40-4505-83b3-5f4706b10755', 'd2ad3061-9609-42ed-8856-563c94bc1ffe', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('f5cb2fe5-1948-4858-8736-e40f4393893b', '88e8619c-aa40-4505-83b3-5f4706b10755', '844d8b8c-90a2-46aa-af01-4c796954f093', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('f9ef60b5-73eb-4467-95b6-f46158554450', '88e8619c-aa40-4505-83b3-5f4706b10755', '575f1', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');
INSERT INTO `sys_office_sys_menu` VALUES ('fcdf9fff-d49f-461a-9e91-da9cc4065e85', '88e8619c-aa40-4505-83b3-5f4706b10755', 'sys_dict_output', 'admin', '2017-04-14 18:39:27', 'admin', '2017-04-14 18:39:27', null, '0');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(64) NOT NULL COMMENT 'id',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `role_type` varchar(64) DEFAULT NULL COMMENT '权限类型',
  `sys_office_type` varchar(64) NOT NULL COMMENT '归属公司',
  `sys_office_id` varchar(64) NOT NULL COMMENT '归属机构',
  `data_scope` varchar(100) DEFAULT NULL COMMENT '数据范围',
  `use_able` varchar(20) DEFAULT NULL COMMENT '是否是可用',
  `if_admin` varchar(64) DEFAULT NULL COMMENT '是否是部门管理员',
  `create_by` varchar(64) DEFAULT NULL COMMENT 'create_by',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT 'update_by',
  `update_date` datetime DEFAULT NULL COMMENT 'update_date',
  `remarks` varchar(225) DEFAULT NULL COMMENT '备注信息',
  `del_flag` int(11) DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_sys_menu`;
CREATE TABLE `sys_role_sys_menu` (
  `id` varchar(64) NOT NULL COMMENT 'id',
  `sys_role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '角色名称',
  `sys_menu_id` varchar(64) NOT NULL COMMENT '权限类型',
  `create_by` varchar(64) DEFAULT NULL COMMENT 'create_by',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT 'update_by',
  `update_date` datetime DEFAULT NULL COMMENT 'update_date',
  `remarks` varchar(225) DEFAULT NULL COMMENT '备注信息',
  `del_flag` int(11) DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
-- Records of sys_role_sys_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_updownfile
-- ----------------------------
DROP TABLE IF EXISTS `sys_updownfile`;
CREATE TABLE `sys_updownfile` (
  `id` varchar(64) NOT NULL,
  `cont` varchar(100) DEFAULT NULL,
  `filesize` bigint(20) DEFAULT NULL,
  `filety` varchar(20) DEFAULT NULL,
  `loginfo` varchar(64) DEFAULT NULL,
  `nname` varchar(300) DEFAULT NULL,
  `oname` varchar(50) DEFAULT NULL,
  `sid` varchar(50) DEFAULT NULL,
  `uploadpath` varchar(500) DEFAULT NULL,
  `yn` bit(1) DEFAULT NULL,
  `tableid` varchar(64) DEFAULT NULL,
  `tablename` varchar(64) DEFAULT NULL,
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统附件';

-- ----------------------------
-- Records of sys_updownfile
-- ----------------------------
INSERT INTO `sys_updownfile` VALUES ('16be5ae2-2679-4843-bde6-4ea43dc84bfe', 'photo', '18143', 'class', null, 'f4d75ce3904f4183b50cf3830d3ac935DiseaseDetailAction.class', 'DiseaseDetailAction.class', 'sysUser', 'D:\\\\TEMPP\\sysUser\\f4d75ce3904f4183b50cf3830d3ac935DiseaseDetailAction.class', '\0', null, null, 'admin', '2017-03-07 14:15:48', 'admin', '2017-03-07 14:15:48', null, '0');
INSERT INTO `sys_updownfile` VALUES ('9c56e1a6-572c-46ea-8d1f-e2f221acf837', 'file', '561276', 'jpg', null, '8ea786cf166248a78ac367c5638d4d75Lighthouse.jpg', 'Lighthouse.jpg', 'sysUser', 'D:\\\\TEMPP\\sysUser\\8ea786cf166248a78ac367c5638d4d75Lighthouse.jpg', '', 'c2449968-b317-4346-9d14-aed3a666e429', 'sysUser', 'admin', '2017-03-07 14:01:30', 'admin', '2017-03-07 14:01:30', null, '0');
INSERT INTO `sys_updownfile` VALUES ('b3e9d23a-b46f-4c33-a340-eacec1c6b151', 'photo', '780831', 'jpg', null, '733209e0fe774d0087a7bae2501bff97Koala.jpg', 'Koala.jpg', 'sysUser', 'D:\\\\TEMPP\\sysUser\\733209e0fe774d0087a7bae2501bff97Koala.jpg', '\0', null, null, 'admin', '2017-03-07 14:31:51', 'admin', '2017-03-07 14:31:51', null, '0');
INSERT INTO `sys_updownfile` VALUES ('e0d0f608-d340-41d7-81c9-00605ac83d32', 'file', '879394', 'jpg', null, '6265d7ce0fa444229a8b4f13d9a93575Chrysanthemum.jpg', 'Chrysanthemum.jpg', 'sysUser', 'D:\\\\TEMPP\\sysUser\\6265d7ce0fa444229a8b4f13d9a93575Chrysanthemum.jpg', '', '3d715f17-e0fe-439e-847d-ac0950030817', 'sysUser', 'admin', '2017-03-07 14:29:19', 'admin', '2017-03-07 14:29:19', null, '0');
INSERT INTO `sys_updownfile` VALUES ('ede0efc5-1f56-4de1-a056-f5f2533e6ead', 'file', '845941', 'jpg', null, '30eda08bd5b7461599c1316672519209Desert.jpg', 'Desert.jpg', 'sysUser', 'D:\\\\TEMPP\\sysUser\\30eda08bd5b7461599c1316672519209Desert.jpg', '', 'f905f27d-abd9-4af0-aaa6-76b296c287a1', 'sysUser', 'admin', '2017-03-07 13:11:35', 'admin', '2017-03-07 13:11:35', null, '0');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `sys_office_type` varchar(64) NOT NULL COMMENT '归属公司',
  `sys_office_id` varchar(2000) NOT NULL COMMENT '归属部门',
  `login_name` varchar(100) NOT NULL COMMENT '登录名',
  `password` varchar(256) NOT NULL COMMENT '密码',
  `no` varchar(100) DEFAULT NULL COMMENT '工号',
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `email` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(200) DEFAULT NULL COMMENT '电话',
  `mobile` varchar(200) DEFAULT NULL COMMENT '手机',
  `user_duty` varchar(200) DEFAULT NULL COMMENT '用户职务',
  `user_type` varchar(200) DEFAULT NULL COMMENT '用户类型',
  `userphoto` varchar(1000) DEFAULT NULL COMMENT '用户头像',
  `login_ip` varchar(100) DEFAULT NULL COMMENT '最后登陆IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `accredit_flag` varchar(255) DEFAULT NULL COMMENT '是否可授权',
  `login_flag` varchar(64) DEFAULT NULL COMMENT '是否可登录',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `errorcount` int(11) DEFAULT '0' COMMENT '登录失败次数',
  PRIMARY KEY (`id`),
  KEY `sys_user_office_id` (`sys_office_id`(255)) USING BTREE,
  KEY `sys_user_login_name` (`login_name`) USING BTREE,
  KEY `sys_user_company_id` (`sys_office_type`) USING BTREE,
  KEY `sys_user_update_date` (`update_date`) USING BTREE,
  KEY `sys_user_del_flag` (`del_flag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('2011f556-ed6b-444a-9669-caee5b7c5d83', 'community', '88e8619c-aa40-4505-83b3-5f4706b10755', 'ypn', 'ef7662012a3204c92aeebbc6727c2aaf6010bdaed3c9840024ee5b93', null, 'ypn', '', '', '', '科员', null, null, '0:0:0:0:0:0:0:1', '2017-04-14 00:00:00', '1', '1', 'admin', '2017-04-11 16:14:32', 'ypn', '2017-04-14 18:40:41', '', '0',0);
INSERT INTO `sys_user` VALUES ('3c7d1963-ce1b-42a3-9272-da0244aaca7d', 'community', 'community', 'sheqv', '99306637e41522592c0f6d20186402c51f1f42344c1494b9027413ce', null, '社区', '', '', '', null, null, null, '0:0:0:0:0:0:0:1', '2017-04-12 00:00:00', '0', '1', 'admin', '2017-04-12 10:52:04', 'sheqv', '2017-04-12 11:17:18', '', '0',0);
INSERT INTO `sys_user` VALUES ('5fcdbeea-2217-4a3c-9933-711cab1a100b', 'community', '88e8619c-aa40-4505-83b3-5f4706b10755', 'qwe', '7771ffbbf0705bf5c99e1e1ede09b480feacae718412ef7761dbb69f', null, 'qwe', '', '', '', null, null, null, '0:0:0:0:0:0:0:1', '2017-04-14 00:00:00', '0', '1', 'ypn', '2017-04-13 10:21:55', 'admin', '2017-04-14 14:37:17', '', '0',0);
INSERT INTO `sys_user` VALUES ('a7a56a21-d41a-4a36-9716-b6cc973cca83', 'area', '1be9affc-a3e0-4107-a073-35a29c801067', 'hww', '420baac2b007349b824fdfcb2916258050eca60006aa327d5529e49c', null, 'hww', '', '', '', null, null, null, '0:0:0:0:0:0:0:1', '2017-04-14 00:00:00', '0', '1', 'admin', '2017-04-11 16:14:51', 'hww', '2017-04-14 13:38:00', '', '0',0);
INSERT INTO `sys_user` VALUES ('admin', 'root', 'area', 'admin', 'b78a63f043854bc28e6164a43a4747886b223186374c1b1d7f522bd4', '0001', '系统管理员', '1185111298@qq.com', '8675', '8675', null, 'sys_admin', '402880fb5405046e0154051b0ace0001', '127.0.0.1', '2017-04-19 00:00:00', '1', '1', '1', '2013-05-27 08:00:00', 'admin', '2017-04-19 13:31:32', '最高管理员', '0',0);

-- ----------------------------
-- Table structure for sys_user_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_sys_menu`;
CREATE TABLE `sys_user_sys_menu` (
  `id` varchar(64) NOT NULL COMMENT 'id',
  `sys_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户id',
  `sys_menu_id` varchar(64) NOT NULL COMMENT '功能id',
  `create_by` varchar(64) DEFAULT NULL COMMENT 'create_by',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT 'update_by',
  `update_date` datetime DEFAULT NULL COMMENT 'update_date',
  `remarks` varchar(225) DEFAULT NULL COMMENT '备注信息',
  `del_flag` int(11) DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户权限表';

-- ----------------------------
-- Records of sys_user_sys_menu
-- ----------------------------
INSERT INTO `sys_user_sys_menu` VALUES ('009ce09c-95f3-4719-8cbb-19c5fe6aac77', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_menu_view', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('0338a8c7-05a1-4a63-90a1-d8a865a60431', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_office_update', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('038335ec-c6aa-4428-a5d2-6e34bf799e54', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_menu_output', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('0653cfae-61eb-46ce-b4bb-dd404cda6a57', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_dict_delete', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('0ad8ad03-341c-40b6-a5f3-15c2fcc29998', '2011f556-ed6b-444a-9669-caee5b7c5d83', '6cfdc6a1-0778-4ef7-940f-64cbbe178162', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('13e8c5c8-58e6-4e5d-b432-b41f82706c0f', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_user_output', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('145830de-0eb8-4f56-b3e7-99987008ae14', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_dict_update', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('1b3a7f5c-dc83-4930-b4aa-82be92a0126f', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_dict_view', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('1b884b74-75b2-41cd-b8d6-c9c49a869310', '2011f556-ed6b-444a-9669-caee5b7c5d83', '402880ee572115f50157211677180000', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('1dbacb08-b3f8-4c6d-9a3f-255e34d0c652', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_office_input', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('1fc36836-40c4-46bf-a87b-54f7838c0771', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_office_output', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('21200222-0a54-4d86-8176-14d02c2a71ad', '2011f556-ed6b-444a-9669-caee5b7c5d83', '9c69eb61-9942-44a4-aaad-b3b6bdfc926c', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('246ccaaf-87d1-4033-b991-2f994e6d80b7', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_log_delete', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('29da890a-b5cc-49bc-9940-068a40895a65', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_menu_mg', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('33856c51-e1cd-4b99-b39c-f5ceb2fd6a8c', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_menu_delete', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('373220fe-d74c-4123-92ec-af44a0af99d7', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'a37cbcd0-6949-4890-a105-1e101bf46ddf', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('3796ce0a-fb33-4875-8c2e-fedc4b796ca6', '2011f556-ed6b-444a-9669-caee5b7c5d83', '04edf080-e8f1-40d5-9ad2-14f156ad9d13', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('386659de-fa3e-45b4-a2a2-4193f8ce28a2', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_log_update', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('44bb8626-436d-4a74-a1d4-719657e3c711', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_user_input', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('499a2075-e5c6-4e17-be1e-c9dd8da96408', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'aefc778d-620c-4e0d-ab62-60771e885a44', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('49f3686d-b97e-46e4-9346-eccb8900ac14', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_office_save', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('4b5d7804-481b-4e11-8bad-19b77f0fdaec', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_dict_save', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('4d518e37-3aca-4943-a7a4-c0cb87834b2a', '2011f556-ed6b-444a-9669-caee5b7c5d83', '402880f653abc66f0153abca584d0000', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('4e3c9dd6-510a-486f-ae96-b24c9002cdfd', '2011f556-ed6b-444a-9669-caee5b7c5d83', '575f1', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('52ce8ba9-4755-41dc-b667-209f2eb4e0f2', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_office_delete', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('5c6379ee-4bcf-4bb7-862c-fa2d3aba6aa0', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_log_input', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('6b8dbb19-f845-48b0-ac9e-6b110563da17', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_dict_output', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('6d19a87e-7397-4513-8f1a-9218c1708c60', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'ce6f645e-2df9-42c6-9ce3-37c08445fb79', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('7076c14d-713b-4fe2-b4ac-ec6f2f589247', '2011f556-ed6b-444a-9669-caee5b7c5d83', '82441402-0e23-4f51-ab81-e29c5d2cb321', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('73cd289f-b8d4-46dd-8f4d-7375c13639cb', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_user_delete', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('7947de29-f76f-4156-8340-0a83fd85e5ab', '2011f556-ed6b-444a-9669-caee5b7c5d83', '844d8b8c-90a2-46aa-af01-4c796954f093', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('7df4fb4c-7bd6-44e5-98c7-d50ac809d6c9', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_menu_save', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('80fbd463-b1e7-47d2-9987-eed114203a19', '2011f556-ed6b-444a-9669-caee5b7c5d83', '48a89ed1-5dec-4c3d-b869-f0ff6244cc5f', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('869acddd-0d98-46ef-8510-827317ee8c94', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_menu_update', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('88ffd410-a861-40f5-a3b1-42ea84952e5b', '2011f556-ed6b-444a-9669-caee5b7c5d83', '402880f653ac86fd0153ac8947810000', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('8ea148f4-7a13-414a-bec5-f6a56bec603c', '2011f556-ed6b-444a-9669-caee5b7c5d83', '575fb3e6-6285-4ff5-8498-d9ebd767c7fa', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('943718be-caf5-4cbc-ae18-0c4fa182d286', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'f3c08c0f-6fd2-4b00-8f93-381a237b7883', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('9864f259-756b-4270-b981-e2845cc35e7e', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_log_output', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('9cbf95f0-d9d2-4295-a873-a9977e8f180a', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_dict_input', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('a9229e27-9df8-4580-a902-eb1bef161f74', '2011f556-ed6b-444a-9669-caee5b7c5d83', '39f8328f-4a44-4a21-9f4a-53b2a95356f2', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('aaebbe52-134a-4170-886b-2a38de970f3d', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_user_view', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('b0e747eb-7328-4d65-ad74-ca3455458888', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_menu_input', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('be5a7cf1-f746-4eb2-bb17-248426b39826', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_log_save', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('c629bf38-3a59-45c6-8834-bdfe336108a7', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_dict_mg', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('cb1b7f29-c5b6-4602-a068-bdb1be40fa9c', '2011f556-ed6b-444a-9669-caee5b7c5d83', '489749bf-c693-41f0-b7a6-4300ee464060', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('ce972075-27c0-487b-a25c-1c4521a3a9cb', '2011f556-ed6b-444a-9669-caee5b7c5d83', '575fb7sfa', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('cfdd92b0-5dff-4f31-81e6-13b365369473', '2011f556-ed6b-444a-9669-caee5b7c5d83', '575fb7fa', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('d0ac35f9-0781-4770-912c-56ed4e2ddc10', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_office_view', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('d9a2b5e0-4c89-4ee8-b55d-818e0a45851b', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_user_update', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('dadf666e-d26d-4ce1-a1ed-b5fe7bc2a5c6', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_office_viewtree', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('dbfec6d1-0eb0-409f-b662-8736329755c3', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_log_mg', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('dc9e1a4a-3980-420c-96f5-45eb47de3c05', '2011f556-ed6b-444a-9669-caee5b7c5d83', '575f', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('dee5e527-e213-44f8-aac4-d988001907cc', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_office_mg', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('df2fca94-3493-44fb-a0f6-5947157095e0', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_user_mg', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('df7ed0fc-7709-4a50-8639-7525faa57de6', '2011f556-ed6b-444a-9669-caee5b7c5d83', '06d64d2a-bc5e-48e3-8f30-096d2db76ead', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('e28b093e-ca63-4520-97e3-f38afdf197ee', '2011f556-ed6b-444a-9669-caee5b7c5d83', '2b79f6b9-58e1-4856-99b4-368c9e8ef967', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('e35f327d-bb3e-4386-abd8-cbb6f5fa66cc', '2011f556-ed6b-444a-9669-caee5b7c5d83', '139fa6a6-dcc9-473a-94ef-9d67549996f5', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('ea4a9222-ff78-4fc4-b558-28b0915c1248', '2011f556-ed6b-444a-9669-caee5b7c5d83', '51bf984b-3998-4599-8b66-db85239e453f', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('ed1dd933-64bb-407d-a279-027240c56461', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_user_save', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('eeb6af88-1b64-49eb-a0a0-0e4d6ba4c7ab', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'b0bd65c8-d02c-4ec1-85eb-918a139b1923', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('f6ab96c5-d8a2-420a-9f58-6abc167f5ec5', '2011f556-ed6b-444a-9669-caee5b7c5d83', '727adcba-a596-463c-a3bb-ed8959af857f', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');
INSERT INTO `sys_user_sys_menu` VALUES ('fa57ff7c-0284-40ee-8255-ef8d28ae8140', '2011f556-ed6b-444a-9669-caee5b7c5d83', 'sys_log_view', 'admin', '2017-04-14 18:39:41', 'admin', '2017-04-14 18:39:41', null, '0');

-- ----------------------------
-- Table structure for sys_user_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_sys_role`;
CREATE TABLE `sys_user_sys_role` (
  `id` varchar(64) NOT NULL COMMENT 'id',
  `sys_user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名称',
  `sys_role_id` varchar(64) NOT NULL COMMENT '角色名称',
  `create_by` varchar(64) DEFAULT NULL COMMENT 'create_by',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT 'update_by',
  `update_date` datetime DEFAULT NULL COMMENT 'update_date',
  `remarks` varchar(225) DEFAULT NULL COMMENT '备注信息',
  `del_flag` int(11) DEFAULT NULL COMMENT '删除标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of sys_user_sys_role
-- ----------------------------
