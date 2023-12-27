<%@ include file="../common/header.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8" />
<title>提交订单</title>
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

</head>
<body data-target="#one" data-spy="scroll">
	<%--引入头部--%>
	<jsp:include page="../common/config/top.jsp"></jsp:include>
	<div class="container" style="padding-bottom: 15px; min-height: 300px; margin-top: 40px;">
		<div class="row">
			<div class="col-md-12">
				<h2>提交订单</h2>
				<hr>
				<form method="post" action="" id="formaddress" class="form-inline">
					<h3>收货人信息</h3>
					<div class="well">
						<shiro:hasPermission name="/address/createAddress.shtml">
							<a class="btn btn-info" onclick="$('#addAddress').modal();"><span class="glyphicon glyphicon-plus"></span> 新增收货地址</a>
						</shiro:hasPermission>
					</div>
					<table class="table table-bordered">
						<c:choose>
							<c:when test="${addressList != null && fn:length(addressList) gt 0}">
								<c:forEach items="${addressList}" var="address">
									<tr>
										<td>
											<c:if test="${address.default_address==true }">
												<input type="radio" name="receiverAddress" id="receiverAddressId_${address.id}" checked="checked" value="${address.id}"/>
											</c:if>
											<c:if test="${address.default_address==false }">
												<input type="radio" name="receiverAddress" id="receiverAddressId_${address.id}" value="${address.id}"/>
											</c:if>
										</td>
										<td>${address.receiver_name}，${address.receiver_mobile}，${address.receiver_state} (省)/${address.receiver_city} (市)/${address.receiver_district} (区/县)/${address.receiver_address}</td>
										<td>
											<shiro:hasPermission name="/address/setDefaultAddress.shtml">
												<a href="javascript:setAsDefaultAddress('${address.id}');">设为默认地址</a>
											</shiro:hasPermission>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td class="text-center danger" colspan="3">没有地址信息，请添加！</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</table>
					<hr>
					<h3>送货清单</h3>
					<table class="table table-bordered">
						<tr>
							<th>物资编号</th>
							<th>物资名称</th>
							<th>物资数量</th>
						</tr>
						<c:forEach items="${itemList}" var="item">
							<tr>
								<td><input type="hidden" id="termId" value="${item.id}"/>${item.item_id}</td>
								<td>${item.item_name}</td>
								<td>${item.num}</td>
							</tr>
						</c:forEach>
					</table>
					<shiro:hasPermission name="/order/createOrder.shtml">
						<a class="btn btn-success" onclick="createOrder();"><span class="glyphicon glyphicon-ok"></span> 提交订单</a>
					</shiro:hasPermission>
				</form>
			</div>
		</div>
		<shiro:hasPermission name="/address/createAddress.shtml">
			<%--添加弹框--%>
			<div class="modal fade" id="addAddress" tabindex="-1" role="dialog" aria-labelledby="addAddressLabel">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="addAddressLabel">新增收货地址</h4>
						</div>
						<div class="modal-body">
							<form id="boxRoleForm">
								<div class="form-group">
									<label for="recipient-name" class="control-label">收货人姓名:</label>
									<input type="text" class="form-control" name="receiver_name" id="receiver_name" placeholder="请输入收货人姓名（必填）" />
									<label for="recipient-name" class="control-label">固定电话:</label>
									<input type="text" class="form-control" id="receiver_phone" name="receiver_phone" placeholder="请输入固定电话">
									<label for="recipient-name" class="control-label">移动电话:</label>
									<input type="text" class="form-control" id="receiver_mobile" name="receiver_mobile" placeholder="请输入移动电话（必填）">
									<label for="recipient-name" class="control-label">省份:</label>
									<input type="text" class="form-control" id="receiver_state" name="receiver_state" placeholder="请输入省份（必填）">
									<label for="recipient-name" class="control-label">城市:</label>
									<input type="text" class="form-control" id="receiver_city" name="receiver_city" placeholder="请输入城市（必填）">
									<label for="recipient-name" class="control-label">区县:</label>
									<input type="text" class="form-control" id="receiver_district" name="receiver_district" placeholder="请输入区县（必填）">
									<label for="recipient-name" class="control-label">收货人地址:</label>
									<input type="text" class="form-control" name="receiver_address" id="receiver_address" placeholder="请输入收货人地址（必填）" />
									<label for="recipient-name" class="control-label">邮政编码:</label>
									<input type="text" class="form-control" id="receiver_zip" name="receiver_zip" placeholder="请输入邮政编码">
								</div>
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-primary" data-dismiss="modal"><span class="glyphicon glyphicon-off"></span> 关闭</button>
							<button type="button" onclick="addAddress();" class="btn btn-success"><span class="glyphicon glyphicon-save"></span> 保存</button>
						</div>
					</div>
				</div>
			</div>
			<%--添加弹框--%>
		</shiro:hasPermission>
	</div>
	
	<script>
		$(document).ready(function(){
		  	$("#orderCenter").addClass("active");
		  	$("#orderitems").addClass("active");
		});	
	</script>
</body>
</html>