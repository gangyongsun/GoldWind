<%@ include file="../common/header.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8" />
<title>密码修改—个人中心</title>
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
	<div class="container">
		<div class="row">
			<div class="col-md-12 col-xs-12">
				<h2>密码修改</h2>
				<hr>
				<form id="formId" enctype="multipart/form-data" action="<%=basePath%>/user/updatePswd.shtml" method="post">
					<div class="form-group">
						<label for="pswd">原密码</label>
						<input type="password" class="form-control" autocomplete="off" id="pswd" maxlength="20" name="pswd" placeholder="请输入原密码">
					</div>
					<div class="form-group">
						<label for="newPswd">新密码</label>
						<input type="password" class="form-control" autocomplete="off" id="newPswd" maxlength="20" name="newPswd" placeholder="请输入新密码">
					</div>
					<div class="form-group">
						<label for="reNewPswd">新密码（再输入一次）</label>
						<input type="password" class="form-control" autocomplete="off" id="reNewPswd" maxlength="20" name="reNewPswd" placeholder="请再次输入新密码">
					</div>
					<div class="form-group">
						<button type="submit" class="btn btn-info"><span class="glyphicon glyphicon-edit"></span> 确定修改</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script src="<%=basePath%>/js/common/jquery/jquery.form-2.82.js"></script>
	<script src="<%=basePath%>/js/user.updatePswd.js"></script>
</body>
</html>