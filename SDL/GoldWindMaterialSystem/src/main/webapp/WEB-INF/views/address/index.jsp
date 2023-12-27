<%@ include file="../common/header.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8" />
<title>地址列表 —地址中心</title>
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
<link rel="icon" href="<%=basePath%>/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/favicon.ico" />
<link href="<%=basePath%>/js/common/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet" />
<link href="<%=basePath%>/css/common/base.css" rel="stylesheet" />
<script src="<%=basePath%>/js/common/jquery/jquery1.8.3.min.js"></script>
<script src="<%=basePath%>/js/common/layer/layer.js"></script>
<script src="<%=basePath%>/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="<%=basePath%>/js/shiro.demo.js"></script>
<script src="<%=basePath%>/js/address.index.js"></script>
</head>
<body data-target="#one" data-spy="scroll" id="content">
	<jsp:include page="../common/config/top.jsp"></jsp:include>
	<div class="container" style="padding-bottom: 15px; min-height: 300px; margin-top: 40px;">
		<div class="row">
			<div class="col-md-12 col-xs-12">
				<h2>地址列表</h2>
				<hr>
				<form method="post" action="" id="formId" class="form-inline">
					<shiro:hasPermission name="/address/createAddress.shtml">
						<a class="btn btn-info" href="javascript:$('#addAddress').modal();"><span class="glyphicon glyphicon-plus"></span> 添加地址</a>
					</shiro:hasPermission>
					<hr>
					<table class="table table-bordered">
						<tr>
							<th>收货人</th>
							<th>移动电话</th>
							<th>省份</th>
							<th>城市</th>
							<th>区县</th>
							<th>收货地址</th>
							<th>操作</th>
						</tr>
						<c:choose>
							<c:when test="${page != null && fn:length(page.list) gt 0}">
								<c:forEach items="${page.list}" var="address">
									<tr>
										<td>${address.receiver_name}</td>
										<td>${address.receiver_mobile}</td>
										<td>${address.receiver_state}</td>
										<td>${address.receiver_city}</td>
										<td>${address.receiver_district}</td>
										<td>${address.receiver_address}</td>
										<td>
											<shiro:hasPermission name="/address/editAddress.shtml">
												<a class="btn btn-info btn-sm" href="javascript:editAddress(${address.id})"><span class="glyphicon glyphicon-edit"></span> 编辑</a>&nbsp;
											</shiro:hasPermission>
											<shiro:hasPermission name="/address/deleteAddress.shtml">
												<a class="btn btn-danger btn-sm" href="javascript:deleteAddress(${address.id});"><span class="glyphicon glyphicon-trash"></span> 删除</a>&nbsp;
											</shiro:hasPermission>
											<shiro:hasPermission name="/address/addressDetail.shtml">
												<a class="btn btn-success btn-sm" href="javascript:addressDetail(${address.id});"><span class="glyphicon glyphicon-info-sign"></span> 详情</a>
											</shiro:hasPermission>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td class="text-center danger" colspan="8">没有地址信息，请添加！</td>
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
						<button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-off"></span> 关闭</button>
						<button type="button" onclick="addAddress();" class="btn btn-success"><span class="glyphicon glyphicon-save"></span> 保存</button>
					</div>
				</div>
			</div>
		</div>
		<%--添加弹框--%>
	</div>
</body>
</html>