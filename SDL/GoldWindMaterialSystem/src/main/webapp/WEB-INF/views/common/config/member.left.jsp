<%@ page pageEncoding="utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<shiro:hasAnyRoles name='100001,100003'>
	<div id="one" class="col-md-2 col-xs-2">
		<ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix" style="top: 100px; z-index: 100;">
			<li id="userlist" class="">
				<a href="<%=basePath%>/member/list.shtml">
					<i class="glyphicon glyphicon-chevron-right"></i>
					用户列表
				</a>
			</li>
			<li id="onlineuserlist" class="">
				<a href="<%=basePath%>/member/online.shtml">
					<i class="glyphicon glyphicon-chevron-right"></i>
					在线用户
				</a>
			</li>
		</ul>
	</div>
</shiro:hasAnyRoles>