<%@ page pageEncoding="utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<shiro:hasAnyRoles name="100001,100002">
	<div id="one" class="col-md-2 col-xs-2">
		<ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix" style="top: 100px; z-index: 100;">
			<shiro:hasPermission name="/role/index.shtml">
				<li id="rolelist" class="dropdown">
					<a href="<%=basePath%>/role/index.shtml">
						<i class="glyphicon glyphicon-chevron-right"></i>
						角色列表
					</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="/role/allocation.shtml">
				<li id="roleallocation" class="dropdown">
					<a href="<%=basePath%>/role/allocation.shtml">
						<i class="glyphicon glyphicon-chevron-right"></i>
						角色分配
					</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="/permission/index.shtml">
				<li id="permissionlist" class="dropdown">
					<a href="<%=basePath%>/permission/index.shtml">
						<i class="glyphicon glyphicon-chevron-right"></i>
						权限列表
					</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="/permission/allocation.shtml">
				<li id="permissionallocation" class="dropdown">
					<a href="<%=basePath%>/permission/allocation.shtml">
						<i class="glyphicon glyphicon-chevron-right"></i>
						权限分配
					</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
</shiro:hasAnyRoles>