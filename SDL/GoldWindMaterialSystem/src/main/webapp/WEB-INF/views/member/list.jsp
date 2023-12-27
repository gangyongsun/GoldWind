<%@ include file="../common/header.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8" />
<title>用户列表 —用户中心</title>
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
<link rel="icon" href="<%=basePath%>/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/favicon.ico" />
<link href="<%=basePath%>/js/common/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet" />
<link href="<%=basePath%>/css/common/base.css" rel="stylesheet" />
<script src="<%=basePath%>/js/common/jquery/jquery1.8.3.min.js"></script>
<script src="<%=basePath%>/js/common/layer/layer.js"></script>
<script src="<%=basePath%>/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="<%=basePath%>/js/shiro.demo.js"></script>
<script src="<%=basePath%>/js/member.list.js"></script>
<script src="<%=basePath%>/js/common.checkbox.js"></script>
</head>
<body data-target="#one" data-spy="scroll">
	<jsp:include page="../common/config/top.jsp"></jsp:include>
	<div class="container" style="padding-bottom: 15px; min-height: 300px; margin-top: 40px;">
		<div class="row">
			<jsp:include page="../common/config/member.left.jsp"></jsp:include>
			<div class="col-md-10 col-xs-10">
				<h2>用户列表</h2>
				<hr>
				<form method="post" action="" id="formId" class="form-inline">
					<div class="form-group well">
						<shiro:hasPermission name="/member/searchUser.shtml">
							<input type="text" class="form-control" style="width: 300px;" value="${findContent}" name="findContent" id="findContent" placeholder="输入用户昵称 / 用户帐号">
							<button type="submit" class="btn btn-info btn-md"><span class="glyphicon glyphicon-search"></span> 查询</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="/member/deleteUserById.shtml">
							<a class="btn btn-danger" onclick="deleteAll();"><span class="glyphicon glyphicon-trash"></span> 删除</a>
						</shiro:hasPermission>
					</div>
					<hr>
					<table class="table table-bordered">
						<tr>
							<th>
								<input type="checkbox" id="checkAll"/>
							</th>
							<th>昵称</th>
							<th>Email/帐号</th>
							<th>登录状态</th>
							<th>创建时间</th>
							<th>最后登录时间</th>
							<th>操作</th>
						</tr>
						<c:choose>
							<c:when test="${page != null && fn:length(page.list) gt 0}">
								<c:forEach items="${page.list}" var="it">
									<tr>
										<td>
											<input value="${it.id}" type="checkbox" id="subcheckbox" onclick="userCheck(this)"/>
										</td>
										<td>${it.nickname}</td>
										<td>${it.email}</td>
										<c:choose>
											<c:when test="${it.status==1 }">
												<td>有效</td>
											</c:when>
											<c:otherwise>
												<td>禁止</td>
											</c:otherwise>
										</c:choose>
										<td>
											<fmt:formatDate value="${it.createTime}" pattern="yyyy年MM月dd日" />
											<br>
											<fmt:formatDate value="${it.createTime}" pattern="HH点mm分ss秒" />
										</td>
										<td>
											<fmt:formatDate value="${it.lastLoginTime}" pattern="yyyy年MM月dd日" />
											<br>
											<fmt:formatDate value="${it.lastLoginTime}" pattern="HH点mm分ss秒" />
										</td>
										<td>
											<shiro:hasPermission name="/member/forbidUserById.shtml">
												<c:choose>
													<c:when test="${it.status==1 }">
														<a class="btn btn-info btn-sm" href="javascript:forbidUserById(0,${it.id})"><span class="glyphicon glyphicon-eye-close"></span> 禁止登录</a>
													</c:when>
													<c:otherwise>
														<a class="btn btn-success btn-sm" href="javascript:forbidUserById(1,${it.id})"><span class="glyphicon glyphicon-eye-open"></span> 激活登录</a>
													</c:otherwise>
												</c:choose>
											</shiro:hasPermission>
											<shiro:hasPermission name="/member/deleteUserById.shtml">
												<a class="btn btn-danger btn-sm" href="javascript:_delete([${it.id}]);"><span class="glyphicon glyphicon-trash"></span> 删除</a>
											</shiro:hasPermission>
											<shiro:hasPermission name="/member/resetPasswd.shtml">
												<a class="btn btn-info btn-sm" href="javascript:openResetPasswdWindow(${it.id});"><span class="glyphicon glyphicon-eye-open"></span> 重置密码</a>
											</shiro:hasPermission>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td class="text-center danger" colspan="6">没有找到用户</td>
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
		<div class="modal fade" id="passwdReset" tabindex="-1" role="dialog" aria-labelledby="passwdResetLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="passwdResetLabel">密码重置</h4>
					</div>
					<input type="hidden" id="userId">
					<div class="modal-body">
						<div class="form-group">
							<label for="newPswd">新密码</label>
							<input type="password" class="form-control" autocomplete="off" id="newPswd" maxlength="20" name="newPswd" placeholder="请输入新密码">
						</div>
						<div class="form-group">
							<label for="reNewPswd">新密码（再输入一次）</label>
							<input type="password" class="form-control" autocomplete="off" id="reNewPswd" maxlength="20" name="reNewPswd" placeholder="请再次输入新密码">
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-off btn-md"></span> 关闭</button>
						<button type="button" onclick="javascript:resetPasswd();" class="btn btn-success"><span class="glyphicon glyphicon-save btn-md"></span> 保存</button>
					</div>
				</div>
			</div>
		</div>
		<%--添加弹框--%>
	</div>
	<script>
		$(document).ready(function(){
		  $("#usersCenter").addClass("active");
		  $("#userlist").addClass("active");
		});	
	</script>
</body>
</html>