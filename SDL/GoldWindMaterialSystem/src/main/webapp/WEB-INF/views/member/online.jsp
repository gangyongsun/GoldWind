<%@ include file="../common/header.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8" />
<title>当前在线Session — SSM + Shiro Demo</title>
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
<link rel="icon" href="<%=basePath%>/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/favicon.ico" />
<link href="<%=basePath%>/js/common/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet" />
<link href="<%=basePath%>/css/common/base.css" rel="stylesheet" />
<script src="<%=basePath%>/js/common/jquery/jquery1.8.3.min.js"></script>
<script src="<%=basePath%>/js/common/layer/layer.js"></script>
<script src="<%=basePath%>/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="<%=basePath%>/js/shiro.demo.js"></script>
<script src="<%=basePath%>/js/member.online.js"></script>
</head>
<body data-target="#one" data-spy="scroll">
	<jsp:include page="../common/config/top.jsp"></jsp:include>
	<div class="container" style="padding-bottom: 15px; min-height: 300px; margin-top: 40px;">
		<div class="row">
			<jsp:include page="../common/config/member.left.jsp"></jsp:include>
			<div class="col-md-10 col-xs-10">
				<h2>当前在线用户</h2>
				<hr>
				<form method="post" action="" id="formId" class="form-inline">
					<!-- <div class="well">
						<p>
							这里是在线已经登录的<code>有效</code>Session，不能等同于当前在线用户，来源于Redis。
						</p>
					</div> -->
					<table class="table table-bordered">
						<tr>
							<th>SessionID</th>
							<th>昵称</th>
							<th>Email/帐号</th>
							<th>创建会话</th>
							<th>会话最后活动</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
						<c:choose>
							<c:when test="${page != null && fn:length(page.list) gt 0}">
								<c:forEach items="${page.list}" var="it">
									<tr>
										<td>${it.sessionId}</td>
										<td>${it.nickname}</td>
										<td>${it.email}</td>
										<td>
											<fmt:formatDate value="${it.startTime}" pattern="yyyy年MM月dd日" />
											<br>
											<fmt:formatDate value="${it.startTime}" pattern="HH点mm分ss秒" />
										</td>
										<td>
											<fmt:formatDate value="${it.lastAccess}" pattern="yyyy年MM月dd日" />
											<br>
											<fmt:formatDate value="${it.lastAccess}" pattern="HH点mm分ss秒" />
										</td>
										<c:choose>
											<c:when test="${ it.sessionStatus }">
												<td>有效</td>
											</c:when>
											<c:otherwise>
												<td>已踢出</td>
											</c:otherwise>
										</c:choose>
										<td>
											<shiro:hasPermission name="/member/onlineDetail.shtml">
												<a class="btn btn-success btn-sm" href="<%=basePath%>/member/onlineDetails/${it.sessionId}.shtml"><span class="glyphicon glyphicon-info-sign"></span> 详情</a>
											</shiro:hasPermission>
											<shiro:hasPermission name="/member/changeSessionStatus.shtml">
												<c:choose>
													<c:when test="${ it.sessionStatus }">
														<a class="btn btn-danger btn-sm" v="onlineDetails" href="javascript:void(0);" sessionId="${it.sessionId}" status="1"><span class="glyphicon glyphicon-eye-close"></span> 踢出</a>
													</c:when>
													<c:otherwise>
														<a class="btn btn-success btn-sm" v="onlineDetails" href="javascript:void(0);" sessionId="${it.sessionId}" status="0"><span class="glyphicon glyphicon-eye"></span> 激活</a>
													</c:otherwise>
												</c:choose>
											</shiro:hasPermission>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td class="text-center danger" colspan="7">没有找到在线用户</td>
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
	</div>
	<script>
		$(document).ready(function(){
		  $("#usersCenter").addClass("active");
		  $("#onlineuserlist").addClass("active");
		});	
	</script>
</body>
</html>