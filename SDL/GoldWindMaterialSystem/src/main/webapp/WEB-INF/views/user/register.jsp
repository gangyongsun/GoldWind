<%@ include file="../common/header.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<meta charset="utf-8">
<title>注册</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<!-- CSS -->
<link rel="stylesheet" href="<%=basePath%>/css/login/reset.css" />
<link rel="stylesheet" href="<%=basePath%>/css/login/supersized.css" />
<link rel="stylesheet" href="<%=basePath%>/css/login/style.css" />
<style>
img {
	cursor: pointer;
	margin-bottom: -15px;
	border-radius: 5px;
}
</style>
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
    <script src="<%=basePath%>/js/common/html5shiv.js"></script>
<![endif]-->
</head>

<body id="body">
	<div class="page-container" style="margin: 100px auto 0px;">
		<h1>Register</h1>
		<form id="_form" action="" method="post">
			<input type="text" name="nickname" id="nickname" class="username" placeholder="Nickname">
			<input type="text" name="email" id="email" placeholder="Email Account">
			<input type="password" name="pswd" id="password" class="password" placeholder="Password">
			<input type="password" id="re_password" placeholder="Repeat the password">
			<div style="text-align: left; margin-left: 10px;" id="vcode">
				<input type="text" name="vcode" placeholder="Verification code" style="width: 110px; margin-left: -8px; margin-right: 8px;">
				<img src="<%=basePath%>/u/getGifCode.shtml" />
			</div>
			<button type="button" class="register">注册</button>
			<button type="button" id="login">登录</button>
			<div class="error">
				<span>+</span>
			</div>
		</form>
	</div>

	<!-- Javascript -->
	<script src="<%=basePath%>/js/common/jquery/jquery1.8.3.min.js"></script>
	<script src="<%=basePath%>/js/common/MD5.js"></script>
	<script src="<%=basePath%>/js/common/supersized.3.2.7.min.js"></script>
	<script src="<%=basePath%>/js/common/supersized-init.js"></script>
	<script src="<%=basePath%>/js/common/layer/layer.js"></script>
	<script src="<%=basePath%>/js/user.register.js"></script>
</body>
</html>