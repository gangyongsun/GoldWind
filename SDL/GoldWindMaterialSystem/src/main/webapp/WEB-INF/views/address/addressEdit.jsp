<%@ include file="../common/header.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8" />
<title>地址详情-</title>
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
<link rel="icon" href="<%=basePath%>/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/favicon.ico" />
<link href="<%=basePath%>/js/common/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet" />
<link href="<%=basePath%>/css/common/base.css" rel="stylesheet" />
<script src="<%=basePath%>/js/common/jquery/jquery1.8.3.min.js"></script>
<script src="<%=basePath%>/js/common/layer/layer.js"></script>
<script src="<%=basePath%>/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="<%=basePath%>/js/shiro.demo.js"></script>
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
				<a class="btn btn-info btn-sm noprint" href="<%=basePath%>/address/index.shtml"><span class="glyphicon glyphicon-backward"></span> 返回地址列表</a>
				<hr>
				<form method="post" action="" id="addressEdit" class="form-inline">
					<h3>地址信息详情</h3>
					<table class="table table-bordered">
						<tr>
							<td>收货人</td>
							<td>
								<input type="hidden" value="${address.id}" id="id">
								<input type="text" class="form-control" value="${address.receiver_name}" id="receiver_name">
							</td>
						</tr>
						<tr>
							<td>固定电话</td>
							<td><input type="text" class="form-control" value="${address.receiver_phone}" id="receiver_phone"></td>
						</tr>
						<tr>
							<td>移动电话</td>
							<td><input type="text" class="form-control" value="${address.receiver_mobile}" id="receiver_mobile"></td>
						</tr>
						<tr>
							<td>省份</td>
							<td><input type="text" class="form-control" value="${address.receiver_state}" id="receiver_state"></td>
						</tr>
						<tr>
							<td>城市</td>
							<td><input type="text" class="form-control" value="${address.receiver_city}" id="receiver_city"></td>
						</tr>
						<tr>
							<td>区县</td>
							<td><input type="text" class="form-control" value="${address.receiver_district}" id="receiver_district"></td>
						</tr>
						<tr>
							<td>收货地址</td>
							<td><input type="text" class="form-control" value="${address.receiver_address}" id="receiver_address"></td>
						</tr>
						<tr>
							<td>邮政编码</td>
							<td><input type="text" class="form-control" value="${address.receiver_zip}" id="receiver_zip"></td>
						</tr>
					</table>
					<div class="form-group">
						<a class="btn btn-info btn-sm" href="<%=basePath%>/address/index.shtml"><span class="glyphicon glyphicon-remove-circle"></span> 返回</a>
						<a class="btn btn-info btn-sm" href="javascript:updateAddress();"><span class="glyphicon glyphicon-floppy-saved"></span> 保存</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script>
		$(document).ready(function(){
		  	$("#personalCenter").addClass("active");
		});	
	</script>
</body>
</html>