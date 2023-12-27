<%@ include file="../common/header.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8" />
<title>${token.nickname}—个人中心</title>
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
<link rel="icon" href="<%=basePath%>/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/favicon.ico" />
<link href="<%=basePath%>/js/common/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet"/>
<link href="<%=basePath%>/css/common/base.css" rel="stylesheet"/>
<script src="<%=basePath%>/js/common/jquery/jquery1.8.3.min.js"></script>
<script src="<%=basePath%>/js/common/layer/layer.js"></script>
<script src="<%=basePath%>/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body data-target="#one" data-spy="scroll">
	<%--引入头部--%>
	<jsp:include page="../common/config/top.jsp"></jsp:include>
	<div class="container" style="padding-bottom: 15px; min-height: 300px; margin-top: 40px;">
		<div class="row">
			<div class="col-md-12 col-xs-12">
				<h2>个人资料</h2>
				<hr>
				<table class="table table-bordered">
					<tr>
						<th width="30%">昵称</th>
						<td>${token.nickname}</td>
					</tr>
					<tr>
						<th>Email/帐号</th>
						<td>${token.email}</td>
					</tr>
					<tr>
						<th>创建时间</th>
						<td><fmt:formatDate value="${token.createTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" /></td>
					</tr>
					<tr>
						<th>最后登录时间</th>
						<td><fmt:formatDate value="${token.lastLoginTime}" pattern="yyyy年MM月dd日HH点mm分ss秒" /></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>