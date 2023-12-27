
-- 权限表初始化数据
insert  into `u_permission`(`id`,`url`,`name`) values 
(1,'/permission/index.shtml','权限列表'),
(2,'/permission/addPermission.shtml','权限添加'),
(3,'/permission/deletePermissionById.shtml','权限删除'),
(4,'/permission/allocation.shtml','权限分配页面'),
(5,'/permission/addPermission2Role.shtml','为角色分配权限'),
(6,'/permission/clearPermissionByRoleIds.shtml','清空角色的权限'),
(7,'/permission/searchPermission.shtml','权限搜索'),

(8,'/role/index.shtml','角色列表'),
(9,'/role/addRole.shtml','角色添加'),
(10,'/role/deleteRoleById.shtml','角色删除'),
(11,'/role/allocation.shtml','角色分配页面'),
(12,'/role/addRole2User.shtml','为用户分配角色'),
(13,'/role/clearRoleByUserIds.shtml','清空用户的角色'),
(14,'/role/searchRole.shtml','角色搜索'),


(15,'/member/list.shtml','系统用户列表'),
(16,'/member/searchUser.shtml','系统用户搜索'),
(17,'/member/forbidUserById.shtml','系统用户激活&禁止'),
(18,'/member/deleteUserById.shtml','系统用户删除'),
(19,'/member/resetPasswd.shtml','重置密码'),

(20,'/member/online.shtml','在线用户列表'),
(21,'/member/onlineDetail.shtml','在线用户详情'),
(22,'/member/changeSessionStatus.shtml','在线用户踢出'),


(23,'/terminology/index.shtml','关键词搜索'),
(24,'/terminology/add2cart.shtml','关键词加入到购物清单'),
(25,'/terminology/add2collection.shtml','收藏关键词'),
(26,'/terminology/removeFromCollection.shtml','取消关键词收藏'),
(27,'/order/shoppingCart.shtml','goto购物清单'),
(28,'/terminology/collection.shtml','关键词收藏列表'),
(29,'/terminology/deleteTerm.shtml','删除收藏关键词'),

(30,'/order/deleteItem.shtml','购物清单物资删除'),
(31,'/order/gotoCreateOrder.shtml','去创建订单'),
(32,'/order/createOrder.shtml','提交订单'),
(33,'/order/index.shtml','订单列表'),

(34,'/address/setDefaultAddress.shtml','设置默认地址'),
(35,'/address/createAddress.shtml','添加收货地址'),
(36,'/address/editAddress.shtml','编辑收货地址'),
(37,'/address/deleteAddress.shtml','删除收货地址'),
(38,'/address/addressDetail.shtml','地址详情'),
(39,'/address/index.shtml','地址列表');




-- 角色表初始化数据
insert  into `u_role`(`id`,`name`,`type`) values 
(1,'系统管理员','100001'),
(2,'权限管理员','100002'),
(3,'用户管理员','100003'),
(4,'采购员','100004'),
(5,'普通用户','100005');



-- 角色权限对应表初始化数据
insert  into `u_role_permission`(`rid`,`pid`) values
(1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),
(1,15),(1,16),(1,17),(1,18),(1,19),(1,20),(1,21),(1,22),
(1,23),(1,24),(1,25),(1,26),(1,27),(1,28),(1,29),(1,30),(1,31),(1,32),(1,33),(1,34),(1,35),(1,36),(1,37),(1,38),(1,39),

(2,1),(2,2),(2,3),(2,4),(2,5),(2,6),(2,7),(2,8),(2,9),(2,10),(2,11),(2,12),(2,13),(2,14),
(3,15),(3,16),(3,17),(3,18),(3,19),(3,20),(3,21),(3,22),
(4,33),
(5,23),(5,24),(5,25),(5,26),(5,27),(5,28),(5,29),(5,30),(5,31),(5,32),(5,33),(5,34),(5,35),(5,36),(5,37),(5,38),(5,39);



-- 用户表初始化数据
insert  into `u_user`(`id`,`nickname`,`email`,`pswd`,`create_time`,`last_login_time`,`status`) values 
(1,'系统管理员','admin','57dd03ed397eabaeaa395eb740b770fd','2019-04-01 10:15:33','2016-04-01 11:24:10',1),
(15, 'Alvin', 'sunyonggang@goldwind.com.cn', '2395234ddaaaf2c203ceb29872a6ad3d', '2019-04-28 04:10:06', '2019-04-28 04:26:39', 1),
(16, 'Kevin', 'Kevin@goldwind.com.cn', '38e442f2d220e9f51aaf221f85536dd4', '2019-04-28 04:19:16', '2019-04-28 04:30:16', 1),
(17, 'Tom', 'Tom@goldwind.com.cn', '9652d122c52aebba52879911883edc41', '2019-04-28 04:21:57', '2019-04-28 04:30:09', 1),
(18, 'Rush', 'Rush@goldwind.com.cn', '09feb7550f136856d649b5aa5cd4df21', '2019-04-28 04:23:37', '2019-04-28 04:30:01', 1);


-- 用户角色关系表初始化数据
insert  into `u_user_role`(`uid`,`rid`) values 
(1,1),
(15,5),
(16,4),
(17,2),
(18,3);
