<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html lang="zh" xmlns:th="http://www.thymeleaf.org">
<head>
<%  
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";  
%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="ThemeBucket">
<link rel="shortcut icon" href="#" type="image/png">
<title>MDIP数据集成平台</title>
<link href="<%=basePath %>/static/plugin/bootstrap/css/style.css" rel="stylesheet">
<link href="<%=basePath %>/static/plugin/bootstrap/css/style-responsive.css" rel="stylesheet">
</head>

<body class="login-body">
	<div class="container">
		<form class="form-signin" action = "<%=basePath %>/login" th:action="@{/login}" method="post">
			<div class="form-signin-heading text-center">
				<h1 class="sign-title">MDIP数据集成平台</h1>
				<img src="<%=basePath %>/static/images/login-logo.png" alt="" th:src="@{/static/images/login-logo.png}" />
			</div>
			<div class="login-wrap">
				<input class="form-control" id="username" name="username" type="text" placeholder="用户名" autofocus>
				<input class="form-control" id="password" name="password" type="password" placeholder="密码">

				<button class="btn btn-lg btn-login btn-block" type="submit">
					<i class="fa fa-check"></i>
				</button>

				<div class="registration">
					<input type="checkbox" value="remember-me">
					记住我
				</div>				
			</div>
		</form>
	</div>

	<!-- Placed js at the end of the document so the pages load faster -->
	<script src="<%=basePath %>/static/plugin/bootstrap/js/jquery-1.10.2.min.js" th:src="@{/static/plugin/bootstrap/js/jquery-1.10.2.min.js}"></script>
	<script src="<%=basePath %>/static/plugin/bootstrap/js/bootstrap.min.js" th:src="@{/static/plugin/bootstrap/js/bootstrap.min.js}"></script>
	<script src="<%=basePath %>/static/plugin/bootstrap/js/modernizr.min.js" th:src="@{/static/plugin/bootstrap/js/modernizr.min.js}"></script>
	<script src="<%=basePath %>/static/js/login/login.js" th:src="@{/static/js/login/login.js}"></script>
	
</body>
</html>
