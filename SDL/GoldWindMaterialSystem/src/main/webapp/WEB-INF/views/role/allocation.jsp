<%@ include file="../common/header.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8" />
<title>用户角色分配 - 权限管理</title>
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
<link rel="icon" href="<%=basePath%>/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/favicon.ico" />
<link href="<%=basePath%>/js/common/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet" />
<link href="<%=basePath%>/css/common/base.css" rel="stylesheet" />
<script src="<%=basePath%>/js/common/jquery/jquery1.8.3.min.js"></script>
<script src="<%=basePath%>/js/common/layer/layer.js"></script>
<script src="<%=basePath%>/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="<%=basePath%>/js/shiro.demo.js"></script>
<script src="<%=basePath%>/js/role.allocation.js"></script>
<script src="<%=basePath%>/js/common.checkbox.js"></script>
</head>
<body data-target="#one" data-spy="scroll">
	<%--引入头部--%>
	<jsp:include page="../common/config/top.jsp"></jsp:include>
	<div class="container" style="padding-bottom: 15px; min-height: 300px; margin-top: 40px;">
		<div class="row">
			<%--引入左侧菜单--%>
			<jsp:include page="../common/config/rolepermission.left.jsp"></jsp:include>
			<div class="col-md-10 col-xs-10">
				<h2>用户角色分配</h2>
				<hr>
				<form method="post" action="" id="formId" class="form-inline">
					<div class="form-group well">
						<shiro:hasPermission name="/member/searchUser.shtml">
							<input type="text" class="form-control" style="width: 300px;" value="${findContent}" name="findContent" id="findContent" placeholder="输入用户昵称 / 用户帐号">
							<button type="submit" class="btn btn-info btn-md"><span class="glyphicon glyphicon-search"></span> 查询</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="/role/clearRoleByUserIds.shtml">
							&nbsp;<a class="btn btn-danger btn-md" onclick="deleteAll();"><span class="glyphicon glyphicon-trash"></span> 清空用户角色</a>
						</shiro:hasPermission>
					</div>
					<hr>
					<table class="table table-bordered">
						<tr>
							<th width="5%">
								<input type="hidden" id="selectUserId">
								<input type="checkbox" id="checkAll" />
							</th>
							<th width="10%">用户昵称</th>
							<th width="20%">Email/帐号</th>
							<th width="5%">状态</th>
							<th width="45%">拥有的角色</th>
							<th width="15%">操作</th>
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
										<td>${it.status==1?'有效':'禁止'}</td>
										<td roleIds="${it.roleIds}">${it.roleNames}</td>
										<td>
											<shiro:hasPermission name="/role/addRole2User.shtml">
												<i class="glyphicon glyphicon-share-alt"></i>
												<a href="javascript:selectRoleById(${it.id});">选择角色</a>
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

		<%--弹框--%>
		<div class="modal fade bs-example-modal-sm" id="selectRole" tabindex="-1" role="dialog" aria-labelledby="selectRoleLabel">
			<div class="modal-dialog modal-sm" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="selectRoleLabel">添加角色</h4>
					</div>
					<div class="modal-body">
						<form id="boxRoleForm">loading...</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="button" onclick="selectRole();" class="btn btn-primary">Save</button>
					</div>
				</div>
			</div>
		</div>
		<%--/弹框--%>
	</div>
	<script>
		$(document).ready(function(){
		  	$("#rolesPermissionsCenter").addClass("active");
		  	$("#roleallocation").addClass("active");
		});	
	</script>
</body>
</html>