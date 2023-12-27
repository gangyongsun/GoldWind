<%@ include file="../common/header.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="zh-cn">
    <head>
        <meta charset="utf-8">
        <title>登录</title>
        <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
        <link rel="icon" href="<%=basePath%>/favicon.ico" type="image/x-icon" />
		<link rel="shortcut icon" href="<%=basePath%>/favicon.ico" />
        <link rel="stylesheet" href="<%=basePath%>/css/login/reset.css"/>
        <link rel="stylesheet" href="<%=basePath%>/css/login/supersized.css"/>
        <link rel="stylesheet" href="<%=basePath%>/css/login/style.css"/>
        <!--[if lt IE 9]>
            <script src="<%=basePath%>/js/common/html5shiv.js"></script>
        <![endif]-->
    </head>

    <body id="body">
        <div class="page-container">
            <h1>GoldWind Test System Login</h1>
            <form id="_form" action="" method="post">
                <input type="text" name="account" class="username" placeholder="Account">
                <input type="password" name="password" class="password" placeholder="Password">
                <div style="text-align: left; margin-left: 10px;">
                	<label>
                		<input type="checkbox" checked="checked" id="rememberMe" style="width: 10px; height: 10px;">记住我
                	</label>
                </div>
                <button type="button" id="login">登录</button>
                <button type="button" id="register" class="register">注册</button>
                <div class="error"><span>+</span></div>
            </form>
        </div>
        <script  src="<%=basePath%>/js/common/jquery/jquery1.8.3.min.js"></script>
        <script  src="<%=basePath%>/js/common/MD5.js"></script>
        <script  src="<%=basePath%>/js/common/supersized.3.2.7.min.js"></script>
        <script  src="<%=basePath%>/js/common/supersized-init.js"></script>
		<script  src="<%=basePath%>/js/common/layer/layer.js"></script>
		<script  src="<%=basePath%>/js/login.js"></script>
    </body>
</html>