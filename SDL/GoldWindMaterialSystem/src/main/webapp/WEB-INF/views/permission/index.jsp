<%@ include file="../common/header.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8" />
<title>权限列表 - 权限管理</title>
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
<link rel="icon" href="<%=basePath%>/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/favicon.ico" />
<link href="<%=basePath%>/js/common/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet" />
<link href="<%=basePath%>/css/common/base.css" rel="stylesheet" />
<script src="<%=basePath%>/js/common/jquery/jquery1.8.3.min.js"></script>
<script src="<%=basePath%>/js/common/layer/layer.js"></script>
<script src="<%=basePath%>/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="<%=basePath%>/js/shiro.demo.js"></script>
<script src="<%=basePath%>/js/permission.index.js"></script>
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
				<h2>权限列表</h2>
				<hr>
				<form method="post" action="" id="formId" class="form-inline">
					<div class="form-group well">
						<shiro:hasPermission name="/permission/searchPermission.shtml">
							<input type="text" class="form-control" style="width: 300px;" value="${findContent}" name="findContent" id="findContent" placeholder="输入权限名称">
							<button type="submit" class="btn btn-info btn-md"><span class="glyphicon glyphicon-search"></span> 查询</button>
						</shiro:hasPermission>
						<shiro:hasPermission name="/permission/addPermission.shtml">
							<a class="btn btn-success btn-md" onclick="$('#addPermission').modal();"><span class="glyphicon glyphicon-plus"></span> 增加权限</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="/permission/deletePermissionById.shtml">
							<a class="btn btn-danger btn-md" onclick="deleteAll();"><span class="glyphicon glyphicon-trash"></span> 删除权限</a>
						</shiro:hasPermission>
					</div>
					<hr>
					<table class="table table-bordered">
						<tr>
							<th width="5%">
								<input type="checkbox" id="checkAll" />
							</th>
							<th width="40%">权限名称</th>
							<th width="40%">角色类型</th>
							<th width="15%">操作</th>
						</tr>
						<c:choose>
							<c:when test="${page != null && fn:length(page.list) gt 0}">
								<c:forEach items="${page.list}" var="it">
									<tr>
										<td>
											<input value="${it.id}" type="checkbox" id="subcheckbox" onclick="userCheck(this)"/>
										</td>
										<td>${it.name}</td>
										<td>${it.url}</td>
										<td>
											<shiro:hasPermission name="/permission/deletePermissionById.shtml">
												<a class="btn btn-danger btn-sm" href="javascript:deleteById([${it.id}]);"><span class="glyphicon glyphicon-remove"></span> 删除</a>
											</shiro:hasPermission>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td class="text-center danger" colspan="4">没有找到权限</td>
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
		<shiro:hasPermission name="/permission/addPermission.shtml">
			<%--添加弹框--%>
			<div class="modal fade" id="addPermission" tabindex="-1" role="dialog" aria-labelledby="addPermissionLabel">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="addPermissionLabel">添加权限</h4>
						</div>
						<div class="modal-body">
							<form id="boxRoleForm">
								<div class="form-group">
									<label for="recipient-name" class="control-label">权限名称:</label>
									<input type="text" class="form-control" name="name" id="name" placeholder="请输入权限名称" />
								</div>
								<div class="form-group">
									<label for="recipient-name" class="control-label">权限URL地址:</label>
									<input type="text" class="form-control" id="url" name="url" placeholder="请输入权限URL地址">
								</div>
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
							<button type="button" onclick="addPermission();" class="btn btn-primary">Save</button>
						</div>
					</div>
				</div>
			</div>
			<%--添加弹框--%>
		</shiro:hasPermission>
	</div>
	<script>
		$(document).ready(function(){
		  	$("#rolesPermissionsCenter").addClass("active");
		  	$("#permissionlist").addClass("active");
		});	
	</script>
</body>
</html>