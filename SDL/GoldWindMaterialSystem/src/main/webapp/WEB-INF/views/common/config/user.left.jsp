<%@ page pageEncoding="utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<div id="one" class="col-md-2 col-xs-2">
	<ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix" style="top: 100px; z-index: 100;">
		<li id="profile" class="dropdown">
			<a href="<%=basePath%>/user/index.shtml">
				<i class="glyphicon glyphicon-chevron-right"></i>
				个人资料
			</a>
			<ul class="dropdown-menu" aria-labelledby="dLabel" style="margin-left: 160px; margin-top: -40px;">
				<li>
					<a href="<%=basePath%>/user/updateSelf.shtml">资料修改</a>
				</li>
				<li>
					<a href="<%=basePath%>/user/updatePswd.shtml">密码修改</a>
				</li>
			</ul>
		</li>
		<li id="personalpermissions" class="dropdown">
			<a href="<%=basePath%>/role/mypermission.shtml">
				<i class="glyphicon glyphicon-chevron-right"></i>
				我的权限
			</a>
		</li>
		<li id="address" class="dropdown">
			<a href="<%=basePath%>/address/index.shtml">
				<i class="glyphicon glyphicon-chevron-right"></i>
				我的地址
			</a>
		</li>
		<shiro:hasPermission name="/order/index.shtml">
			<li id="orderlist" class="dropdown">
				<a href="<%=basePath%>/order/index.shtml">
					<i class="glyphicon glyphicon-chevron-right"></i>
					订单列表
				</a>
			</li>
		</shiro:hasPermission>
	</ul>
</div>
