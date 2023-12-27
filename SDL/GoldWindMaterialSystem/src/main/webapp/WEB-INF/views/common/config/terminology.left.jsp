<%@ page pageEncoding="utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<shiro:hasAnyRoles name="100001,110000,110004">
	<div id="one" class="col-md-2 col-xs-2">
		<ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix" style="top: 100px; z-index: 100;">
			<shiro:hasPermission name="/terminology/index.shtml">
				<li id="termsearch" class="dropdown">
					<a href="<%=basePath%>/terminology/index.shtml">
						<i class="glyphicon glyphicon-chevron-right"></i>
						关键词搜索
					</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="/terminology/collection.shtml">
				<li id="termcollection" class="dropdown">
					<a href="<%=basePath%>/terminology/collection.shtml">
						<i class="glyphicon glyphicon-chevron-right"></i>
						关键词收藏
					</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="/terminology/termcustom.shtml">
				<li id="termcustom" class="dropdown">
					<a href="<%=basePath%>/terminology/termcustom.shtml">
						<i class="glyphicon glyphicon-chevron-right"></i>
						关键词自定义
					</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
</shiro:hasAnyRoles>