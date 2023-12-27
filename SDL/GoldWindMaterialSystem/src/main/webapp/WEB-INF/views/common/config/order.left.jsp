<%@ page pageEncoding="utf-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<shiro:hasAnyRoles name="100001,110004">
	<div id="one" class="col-md-2 col-xs-2">
		<ul data-spy="affix" class="nav nav-list nav-tabs nav-stacked bs-docs-sidenav dropdown affix" style="top: 100px; z-index: 100;">
			<shiro:hasPermission name="/order/index.shtml">
				<li id="orderlist" class="dropdown">
					<a href="<%=basePath%>/order/index.shtml">
						<i class="glyphicon glyphicon-chevron-right"></i>
						订单列表
					</a>
				</li>
			</shiro:hasPermission>
			<shiro:hasPermission name="/order/shopping_cart.shtml">
				<li id="orderitems" class="dropdown">
					<a href="<%=basePath%>/order/shopping_cart.shtml">
						<i class="glyphicon glyphicon-chevron-right"></i>
						购物清单
					</a>
				</li>
			</shiro:hasPermission>
		</ul>
	</div>
</shiro:hasAnyRoles>