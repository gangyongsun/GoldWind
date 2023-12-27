<%@ include file="../common/header.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8" />
<title>订单详情-</title>
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
<link rel="icon" href="<%=basePath%>/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/favicon.ico" />
<link href="<%=basePath%>/js/common/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet" />
<link href="<%=basePath%>/css/common/base.css" rel="stylesheet" />
<script src="<%=basePath%>/js/common/jquery/jquery1.8.3.min.js"></script>
<script src="<%=basePath%>/js/common/layer/layer.js"></script>
<script src="<%=basePath%>/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="<%=basePath%>/js/shiro.demo.js"></script>
<script src="<%=basePath%>/js/common.checkbox.js"></script>
<style type="text/css" media="print">
	@page {
		size: auto; /* auto is the initial value */
		margin: 1cm 1cm 0cm 1cm; /* this affects the margin in the printer settings */
	}
	.noprint { display:none;}
</style>
</head>
<body data-target="#one" data-spy="scroll">
	<%--引入头部--%>
	<jsp:include page="../common/config/top.jsp"></jsp:include>
	<div class="container" style="padding-bottom: 15px; min-height: 300px; margin-top: 40px;">
		<div class="row">
			<div class="col-md-12">
				<a class="btn btn-info btn-sm noprint" href="<%=basePath%>/order/index.shtml"><span class="glyphicon glyphicon-backward"></span> 返回订单列表</a>
				<a class="btn btn-info btn-sm noprint" href="javascript:window.print();"><span class="glyphicon glyphicon-print"></span> 打印订单</a>
				<shiro:hasAnyRoles name='100004'>
					<c:if test="${order.order_status==3}">
						<a class="btn btn-info btn-sm noprint" href="javascript:updateOrderStatus('${order.order_id}',${order.order_status+1});">开始采购</a>
					</c:if>
					<c:if test="${order.order_status==4}">
						<a class="btn btn-info btn-sm noprint" href="javascript:updateOrderStatus('${order.order_id}',${order.order_status+1});">采购完成</a>
					</c:if>
					<c:if test="${order.order_status==5}">
						<a class="btn btn-info btn-sm noprint" href="javascript:updateOrderStatus('${order.order_id}',${order.order_status+1});">准备发货</a>
					</c:if>
					<c:if test="${order.order_status==6}">
						<a class="btn btn-success btn-sm noprint" onclick="$('#shipping').modal();">填写物流信息</a>
					</c:if>
				</shiro:hasAnyRoles>
				<c:if test="${order.order_status==7}">
					<a class="btn btn-info btn-sm noprint" href="javascript:updateOrderStatus('${order.order_id}',${order.order_status+1});">确认收货</a>
				</c:if>
				<c:if test="${order.order_status==8 && order.buyer_rate==false }">
					<a class="btn btn-success btn-sm noprint" onclick="$('#evaluation').modal();">评价</a>
				</c:if>
				<hr>
				<form method="post" action="" id="formDetail" class="form-inline">
					<h3>订单[${order.order_id}]信息</h3>
					<table class="table table-bordered">
						<tr>
							<td>下单者：${user.nickname}；邮箱：${user.email}</td>
						</tr>
						<c:if test="${order.order_status==4||order.order_status==5||order.order_status==6||order.order_status==7||order.order_status==8}">
							<tr>
								<td>采购者：${purchaser.nickname};邮箱：${purchaser.email}</td>
							</tr>
						</c:if>
						<tr>
							<td>订单状态：
								<c:if test="${order.order_status==3}">
									待采购
								</c:if>
								<c:if test="${order.order_status==4}">
									采购中
								</c:if>
								<c:if test="${order.order_status==5}">
									采购完成
								</c:if>
								<c:if test="${order.order_status==6}">
									待发货
								</c:if>
								<c:if test="${order.order_status==7}">
									已发货
								</c:if>
								<c:if test="${order.order_status==8}">
									交易成功
								</c:if>
								<c:if test="${order.order_status==9}">
									交易关闭
								</c:if>
							</td>
						</tr>
						<c:if test="${order.order_status==8}">
							<tr>
								<td>交易完成时间：<fmt:formatDate value="${order.end_time}" pattern="yyyy年MM月dd日HH点mm分ss秒" /></td>
							</tr>
						</c:if>
						<c:if test="${order.order_status==9}">
							<tr>
								<td>交易关闭时间：<fmt:formatDate value="${order.close_time}" pattern="yyyy年MM月dd日HH点mm分ss秒" /></td>
							</tr>
						</c:if>
						<c:if test="${order.buyer_rate==true}">
							<tr>
								<td>用户评价信息：${order.buyer_message}</td>
							</tr>
						</c:if>
						<tr>
							<td>订单创建时间：<fmt:formatDate value="${order.create_time}" pattern="yyyy年MM月dd日HH点mm分ss秒" /></td>
						</tr>
						<tr>
							<td>订单更新时间：<fmt:formatDate value="${order.update_time}" pattern="yyyy年MM月dd日HH点mm分ss秒" /></td>
						</tr>
						<c:if test="${order.order_status==7||order.order_status==8}">
							<tr>
								<td>物流供应商：${order.shipping_name}，物流单号：${order.shipping_code}</td>
							</tr>
						</c:if>
					</table>
					<hr>
					<h3>物资信息</h3><a class="btn btn-info btn-sm noprint" href="javascript:exportExcel('${order.order_id}');"><span class="glyphicon glyphicon-download"></span> 导出</a>
					<table id="itemTable" class="table table-bordered">
						<tr>
							<th>物资编号</th>
							<th>物资名称</th>
							<th>物资数量</th>
						</tr>
						<c:forEach items="${itemList}" var="item">
							<tr>
								<td>${item.item_id}</td>
								<td>${item.item_name}</td>
								<td>${item.num}</td>
							</tr>
						</c:forEach>
					</table>
					<hr>
					<h3>收货人信息</h3>
					<table class="table table-bordered">
						<tr>
							<td>收货人：${address.receiver_name}</td>
							<td>固定电话：${address.receiver_phone}</td>
							<td>移动电话：${address.receiver_mobile}</td>
							<td>邮政编码：${address.receiver_zip}</td>
						</tr>
						<tr>
							<td colspan="4">收货地址：${address.receiver_state}/${address.receiver_city}/${address.receiver_district}/${address.receiver_address}</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<%--添加弹框--%>
		<div class="modal fade" id="shipping" tabindex="-1" role="dialog" aria-labelledby="shippingLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="addShippingLabel">填写发货物流信息</h4>
					</div>
					<div class="modal-body">
						<form id="boxRoleForm">
							<div class="form-group">
								<label for="recipient-name" class="control-label">物流提供商:</label>
								<input type="text" class="form-control" name="shipping_name" id="shipping_name" placeholder="请输入物流提供商" />
								<label for="recipient-name" class="control-label">物流单号:</label>
								<input type="text" class="form-control" id="shipping_code" name="shipping_code" placeholder="请输入物流单号">
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" onclick="javascript:shipping('${order.order_id}',${order.order_status+1});">发货</button>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="evaluation" tabindex="-1" role="dialog" aria-labelledby="evaluationLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="addevaluationLabel">填写评价信息</h4>
					</div>
					<div class="modal-body">
						<form id="boxRoleForm">
							<div class="form-group">
								<label for="recipient-name" class="control-label">评价内容:</label>
								<textarea class="form-control" rows="4" id="buyer_message" name="buyer_message" placeholder="请输入..."></textarea>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" onclick="javascript:orderEvaluate('${order.order_id}');">评价</button>
					</div>
				</div>
			</div>
		</div>
		<%--添加弹框--%>
	</div>
	<script>
		$(document).ready(function(){
		  	$("#personalCenter").addClass("active");
		});	
	</script>
</body>
</html>