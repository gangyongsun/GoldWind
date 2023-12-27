1、在/usr/local目录下创建三个文件夹，包括bin，etc，db三个目录，如果已有就直接用吧
　　sudo mkdir /usr/local/bin
　　sudo mkdir /usr/local/etc
　　sudo mkdir /usr/local/db
2、进入/usr/local/etc/文件夹，创建redis文件夹，并创建redis.conf配置文件，也可以把安装目录下（即之前解压缩的/usr/local/redis-3.2.4/redis.conf）复制过来，再进行修改；
3、修改redis.conf配置文件，主要注意的是配置一下ip地址（如果配成127.0.0.1，那么默认只能本机访问redis服务器，如果需要其他局域网内机器或外网机器进行访问时，请配置成当前机器的ip地址），超时时间，日志文件位置等等。具体内容可以在网上搜索redis配置文件每一个含义，这里就不一一解释了。
4、保存文件退出，进入/usr/local/bin/目录下，启动redis
　　sudo ./redis-server /usr/local/etc/redis/redis.conf
5、启动之后，实时查看启动日志
　　tail -f /usr/local/etc/redis/log-redis.log
　　至此，redis完成搭建，默认端口是6379
　　如果想操作redis，进入bin目录之后，使用redis客户端工具进行查看
　　sudo ./redis-cli -h xxx.xxx.xx.xxx -p 6379 -a password
　如果想停止redis服务：
　　1、杀掉进程；
　　2、在redis-cli中使用shutdown命令
==================

本项目详细介绍请看：http://www.sojson.com/shiro （强烈推荐）
Demo已经部署到线上，地址是http://shiro.itboy.net，
管理员帐号：admin，密码：admin或者sojson.com;
PS：你可以注册自己的帐号，然后用管理员赋权限给你自己的帐号，但是，每20分钟会把数据初始化一次。
新版本说明：http://www.sojson.com/blog/165.html
===========

本项目：
使用过程：
1.创建数据库:tables.sql
2.插入初始化数据:init.data.sql
3.运行

管理员帐号/密码：admin/admin
ps:定时任务的sql会把密码改变为sojson.com 