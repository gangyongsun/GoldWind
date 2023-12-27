<%@ include file="../common/header.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8" />
<title>订单列表</title>
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
<link rel="icon" href="<%=basePath%>/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/favicon.ico" />
<link href="<%=basePath%>/js/common/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet" />
<link href="<%=basePath%>/css/common/base.css" rel="stylesheet" />
<script src="<%=basePath%>/js/common/jquery/jquery1.8.3.min.js"></script>
<script src="<%=basePath%>/js/common/layer/layer.js"></script>
<script src="<%=basePath%>/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="<%=basePath%>/js/shiro.demo.js"></script>
<script src="<%=basePath%>/js/order.index.js"></script>
</head>
<body data-target="#one" data-spy="scroll" id="content">
	<div  id="terminology_index_page">
		<%--引入头部--%>
		<jsp:include page="../common/config/top.jsp"></jsp:include>
		<div class="container" style="padding-bottom: 15px; min-height: 300px; margin-top: 40px;">
			<div class="row">
				<%--引入左侧菜单--%>
				<div class="col-md-12 col-xs-12">
					<h2 align="center">订单或清单列表</h2>
					<hr>
					<form method="post" action="" id="formId" class="form-inline">
						<div class="form-group well">
							<input type="text" class="form-control" style="width: 300px;" value="${findContent}" name="findContent" id="findContent" placeholder="输入订单号">
							<button type="submit" class="btn btn-info btn-md"><span class="glyphicon glyphicon-search"></span> 查询</button>
							<div class="btn-group">
								<button class="btn btn-success">订单状态</button>
								<button class="btn btn-success dropdown-toggle" data-toggle="dropdown">
							    	<span class="caret"></span>
							  	</button>
								<ul class="dropdown-menu" role="menu">
									<li id="li_3"><a href="javascript:orderStatusFilter(3);">待采购</a></li>
									<li id="li_4"><a href="javascript:orderStatusFilter(4);">采购中</a></li>
									<li id="li_5"><a href="javascript:orderStatusFilter(5);">采购完成</a></li>
									<li id="li_6"><a href="javascript:orderStatusFilter(6);">待发货</a></li>
									<li id="li_7"><a href="javascript:orderStatusFilter(7);">已发货</a></li>
									<li id="li_8"><a href="javascript:orderStatusFilter(8);">交易成功</a></li>
									<li id="li_9"><a href="javascript:orderStatusFilter(9);">交易关闭</a></li>
									<li class="divider"></li>
									<li id="li_0"><a href="javascript:orderStatusFilter();">所有状态</a></li>
								</ul>
							</div>
						</div>
						<hr>
						<table class="table table-bordered">
							<tr>
								<th width="30%">订单号</th>
								<th width="30%">下单时间</th>
								<th width="10%">订单状态</th>
								<th width="30%">操作</th>
							</tr>
							<c:choose>
								<c:when test="${page != null && fn:length(page.list) gt 0}">
									<c:forEach items="${page.list}" var="order">
										<tr>
											<td>${order.order_id}</td>
											<td><fmt:formatDate value="${order.create_time}" pattern="yyyy年MM月dd日HH点mm分ss秒" /></td>
											<c:if test="${order.order_status==3}">
												<td>待采购</td>
											</c:if>
											<c:if test="${order.order_status==4}">
												<td>采购中</td>
											</c:if>
											<c:if test="${order.order_status==5}">
												<td>采购完成</td>
											</c:if>
											<c:if test="${order.order_status==6}">
												<td>待发货</td>
											</c:if>
											<c:if test="${order.order_status==7}">
												<td>已发货</td>
											</c:if>
											<c:if test="${order.order_status==8}">
												<td>交易成功</td>
											</c:if>
											<c:if test="${order.order_status==9}">
												<td>交易关闭</td>
											</c:if>
											<td>
												<a class="btn btn-success btn-sm" href="javascript:orderDetail('${order.order_id}');"><span class="glyphicon glyphicon-info-sign"></span> 订单详情</a>
												<c:if test="${order.order_status==3}">
													&nbsp;<a class="btn btn-danger btn-sm" href="javascript:cancelOrder('${order.order_id}');"><span class="glyphicon glyphicon-trash"></span> 取消订单</a>
												</c:if>
												<c:if test="${order.order_status==7||order.order_status==8}">
													&nbsp;<a class="btn btn-info btn-sm" href="javascript:shippingDetail('${order.order_id}');"><span class="glyphicon glyphicon-plane"></span> 物流跟踪</a>
												</c:if>
												<c:if test="${order.order_status==8||order.order_status==9}">
													&nbsp;<a class="btn btn-danger btn-sm" href="javascript:deleteOrder('${order.order_id}');"><span class="glyphicon glyphicon-trash"></span> 删除订单</a>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td class="text-center danger" colspan="9">没有订单信息</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</table>
						<c:if test="${page != null && fn:length(page.list) gt 0}">
							<div class="pagination pull-right">${page.pageHtml}</div>
						</c:if>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>