<%@ page pageEncoding="utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<script baseUrl="<%=basePath%>" src="<%=basePath%>/js/user.login.js"></script>
<div class="navbar navbar-inverse navbar-fixed-top animated fadeInDown" style="z-index: 101; height: 41px;">
	<div class="container" style="padding-left: 0px; padding-right: 0px;">
		<div class="navbar-header">
			<button data-target=".navbar-collapse" data-toggle="collapse" type="button" class="navbar-toggle collapsed">
				<span class="sr-only">导航</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
		</div>
		<div role="navigation" class="navbar-collapse collapse">
			<a id="_logo" href="<%=basePath%>" style="color: #fff; font-size: 24px;" class="navbar-brand hidden-sm">GoldWind Search Engine</a>
			<ul class="nav navbar-nav" id="topMenu">
				<%--拥有 角色100001(管理员)||100003(用户中心)--%>
				<shiro:hasAnyRoles name='100001,100003'>
					<li id="usersCenter" class="dropdown ">
						<a aria-expanded="false" aria-haspopup="true" role="button" data-toggle="dropdown" class="dropdown-toggle" href="<%=basePath%>/member/list.shtml">
							<span class="glyphicon glyphicon-user"> 用户中心</span>
						</a>
						<ul class="dropdown-menu">
							<shiro:hasPermission name="/member/list.shtml">
								<li><a href="<%=basePath%>/member/list.shtml">用户列表</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="/member/online.shtml">
								<li><a href="<%=basePath%>/member/online.shtml">在线用户</a></li>
							</shiro:hasPermission>
						</ul>
					</li>
				</shiro:hasAnyRoles>
				<%--拥有 100001（管理员）|| 100002（权限频道）--%>
				<shiro:hasAnyRoles name='100001,100002'>
					<li id="rolesPermissionsCenter" class="dropdown">
						<a aria-expanded="false" aria-haspopup="true" role="button" data-toggle="dropdown" class="dropdown-toggle" href="/permission/index.shtml">
							<span class="glyphicon glyphicon-filter"> 权限管理</span>
						</a>
						<ul class="dropdown-menu">
							<li><a href="<%=basePath%>/role/index.shtml">角色列表</a></li>
							<li><a href="<%=basePath%>/role/allocation.shtml">角色分配</a></li>
							<li><a href="<%=basePath%>/permission/index.shtml">权限列表</a></li>
							<li><a href="<%=basePath%>/permission/allocation.shtml">权限分配</a></li>
						</ul>
					</li>
				</shiro:hasAnyRoles>
				<shiro:hasAnyRoles name='100001,100005'>
					<li id="terminologyCenter" class="dropdown">
						<a aria-expanded="false" aria-haspopup="true" role="button" data-toggle="dropdown" class="dropdown-toggle" href="/terminology/index.shtml">
							<span class="glyphicon glyphicon-search"> 关键词查询</span>
						</a>
						<ul class="dropdown-menu">
							<li><a href="<%=basePath%>/terminology/index.shtml">关键词搜索</a></li>
							<li><a href="<%=basePath%>/terminology/collection.shtml">关键词收藏</a></li>
							<li><a href="<%=basePath%>/terminology/termcustom.shtml">关键词自定义</a></li>
						</ul>
					</li>
				</shiro:hasAnyRoles>
				<shiro:hasAnyRoles name='100001,100005'>
					<li id="orderCenter" class="">
						<a aria-expanded="false" aria-haspopup="true" role="button" href="<%=basePath%>/order/shopping_cart.shtml">
							<span class="glyphicon glyphicon-shopping-cart"> 购物清单</span>
						</a>
					</li>
				</shiro:hasAnyRoles>
				<shiro:hasAnyRoles name='100004'>
					<li id="orderCenter" class="">
						<a aria-expanded="false" aria-haspopup="true" role="button" href="<%=basePath%>/order/index.shtml">
							<span class="glyphicon glyphicon-shopping-cart"> 订单列表</span>
						</a>
					</li>
				</shiro:hasAnyRoles>
			</ul>
			<ul class="nav navbar-nav  pull-right">
				<li class="dropdown" style="color: #fff;">
					<%--已经登录（包括记住我的）--%> 
					<shiro:user>
						<a aria-expanded="false" aria-haspopup="true" role="button" data-toggle="dropdown" onclick="location.href='<%=basePath%>/user/index.shtml'" href="<%=basePath%>/user/index.shtml"
							class="dropdown-toggle qqlogin">
							<span class="glyphicon glyphicon-user"><shiro:principal property="nickname" /></span>
						</a>
						<ul class="dropdown-menu">
							<li><a href="<%=basePath%>/user/index.shtml">个人资料</a></li>
							<li><a href="<%=basePath%>/user/updateSelf.shtml">资料修改</a></li>
							<li><a href="<%=basePath%>/user/updatePswd.shtml">密码修改</a></li>
							<li><a href="<%=basePath%>/role/mypermission.shtml">我的权限</a></li>
							<shiro:hasPermission name="/address/index.shtml">
								<li><a href="<%=basePath%>/address/index.shtml">我的地址</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="/order/index.shtml">
								<shiro:hasAnyRoles name='100001,100004'>
									<li><a href="<%=basePath%>/order/index.shtml">用户订单</a></li>
								</shiro:hasAnyRoles>
								<shiro:hasAnyRoles name='100005'>
									<li><a href="<%=basePath%>/order/index.shtml">用户订单</a></li>
								</shiro:hasAnyRoles>
							</shiro:hasPermission>
							<li><a href="javascript:void(0);" onclick="logout();">退出登录</a></li>
						</ul>
					</shiro:user>
					
					<%--没有登录(游客)--%> 
					<shiro:guest>
						<a aria-expanded="false" aria-haspopup="true" role="button" data-toggle="dropdown" href="javascript:void(0);" class="dropdown-toggle qqlogin">
							<img src="//qzonestyle.gtimg.cn/qzone/vas/opensns/res/img/Connect_logo_1.png">&nbsp;登录
						</a>
					</shiro:guest>
				</li>
			</ul>
		</div>
	</div>
</div>
