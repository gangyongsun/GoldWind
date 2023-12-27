/*表结构插入*/
DROP TABLE IF EXISTS `u_permission`;

CREATE TABLE `u_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(256) DEFAULT NULL COMMENT 'url地址',
  `name` varchar(64) DEFAULT NULL COMMENT 'url描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Table structure for table `u_role` */

DROP TABLE IF EXISTS `u_role`;

CREATE TABLE `u_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `type` varchar(10) DEFAULT NULL COMMENT '角色类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Table structure for table `u_role_permission` */

DROP TABLE IF EXISTS `u_role_permission`;

CREATE TABLE `u_role_permission` (
  `rid` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `pid` bigint(20) DEFAULT NULL COMMENT '权限ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `u_user` */

DROP TABLE IF EXISTS `u_user`;

CREATE TABLE `u_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(20) DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱|登录帐号',
  `pswd` varchar(32) DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `status` bigint(1) DEFAULT '1' COMMENT '1:有效，0:禁止登录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Table structure for table `u_user_role` */

DROP TABLE IF EXISTS `u_user_role`;

CREATE TABLE `u_user_role` (
  `uid` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `rid` bigint(20) DEFAULT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_collectedmaterial`;

CREATE TABLE `tb_collectedmaterial` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL,
  `material_id` bigint(20) NOT NULL,
  `material_name` varchar(100) DEFAULT NULL,
  `collected_time` datetime DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

drop table if exists `tb_order`;
create table tb_order(
	order_id varchar(50) primary key  COMMENT '订单ID',
    payment decimal COMMENT '实付金额',
    payment_type int(2) COMMENT '支付类型',
    post_fee decimal COMMENT '邮费',
    order_status int(10) COMMENT '订单状态',
    create_time datetime COMMENT '订单创建时间',
	update_time datetime COMMENT '订单更新时间',
    payment_time datetime COMMENT '付款时间',
    consign_time datetime COMMENT '发货时间',
    end_time datetime COMMENT '交易完成时间',
    close_time datetime COMMENT '交易关闭时间',
    shipping_name varchar(20) COMMENT '物流名称',
    shipping_code varchar(20) COMMENT '物流单号',
    user_id bigint(20) COMMENT '用户ID',
	purchaser_id bigint(20) COMMENT '采购用户ID',
    buyer_message varchar(100) COMMENT '买家留言',
	buyer_nickname varchar(50) COMMENT '买家昵称',
	buyer_rate tinyint(1) COMMENT '买家是否已评价',
    viewable tinyint(1) COMMENT '订单是否可见'
);

drop table if exists `tb_order_item`;
create table tb_order_item(
	id bigint(20) primary key auto_increment,
    user_id bigint(20) COMMENT '用户ID',
    item_id varchar(50) COMMENT '商品ID',
    order_id varchar(50) COMMENT '订单ID',
    num int(10) COMMENT '商品数量',
    item_name varchar(200) COMMENT '商品名称',
    price decimal COMMENT '单价',
    total_fee decimal COMMENT '商品总金额',
    pic_path varchar(200) COMMENT '商品图片地址'
);

drop table if exists `tb_address`;
create table tb_address(
	id bigint(20) primary key auto_increment,
    receiver_name varchar(20) COMMENT '收货人姓名',
	receiver_phone varchar(20) COMMENT '固定电话',
    receiver_mobile varchar(30) COMMENT '移动电话',
    receiver_state varchar(20) COMMENT '省份',
    receiver_city varchar(20) COMMENT '城市',
    receiver_district varchar(20) COMMENT '区县',
    receiver_address varchar(200) COMMENT '收货地址',
    receiver_zip varchar(20) COMMENT '邮编',
	create_time datetime COMMENT '创建时间',
    update_time datetime COMMENT '更新时间',
    user_id bigint(20) COMMENT '用户ID',
    viewable tinyint(1) COMMENT '是否可见',
    default_address tinyint(1) COMMENT '是否是默认地址'
);

drop table if exists `tb_order_address`;
create table tb_order_address(
	id bigint(20) primary key auto_increment,
	order_id varchar(50) COMMENT '订单ID',
    address_id bigint(20) COMMENT '收货地址ID'
)
