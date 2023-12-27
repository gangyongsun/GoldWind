<%@ include file="../common/header.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8" />
<title>资料修改 —个人中心</title>
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
<link rel="icon" href="<%=basePath%>/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/favicon.ico" />
<link href="<%=basePath%>/js/common/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet" />
<link href="<%=basePath%>/css/common/base.css" rel="stylesheet" />
<script src="<%=basePath%>/js/common/jquery/jquery1.8.3.min.js"></script>
<script src="<%=basePath%>/js/common/layer/layer.js"></script>
<script src="<%=basePath%>/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script>
	
</script>
</head>
<body data-target="#one" data-spy="scroll">
	<%--引入头部--%>
	<jsp:include page="../common/config/top.jsp"></jsp:include>
	<div class="container" style="padding-bottom: 15px; min-height: 300px; margin-top: 40px;">
		<div class="row">
			<div class="col-md-12 col-xs-12">
				<h2>资料修改</h2>
				<hr>
				<form id="formId" enctype="multipart/form-data" action="<%=basePath%>/user/updateSelf.shtml" method="post">
					<input type="hidden" value="${token.id}" name="id" />
					<div class="form-group">
						<label for="nickname">昵称</label>
						<input type="text" class="form-control" autocomplete="off" id="nickname" maxlength="8" name="nickname" value="${token.nickname}" placeholder="请输入昵称">
					</div>
					<div class="form-group">
						<label for="email">Email(不准修改)</label>
						<input type="text" class="form-control " disabled autocomplete="off" id="email" maxlength="64" name="email" value="${token.email}" placeholder="请输入帐号">
					</div>
					<div class="form-group">
						<button type="submit" class="btn btn-info"><span class="glyphicon glyphicon-edit"></span> 确定修改</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script src="<%=basePath%>/js/common/jquery/jquery.form-2.82.js"></script>
	<script src="<%=basePath%>/js/user.updateSelf.js"></script>
</body>
</html>