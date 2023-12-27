<%@ include file="../common/header.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8" />
<title>关键词收藏列表</title>
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
<link rel="icon" href="<%=basePath%>/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/favicon.ico" />
<link href="<%=basePath%>/js/common/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet" />
<link href="<%=basePath%>/css/common/base.css" rel="stylesheet" />
<script src="<%=basePath%>/js/common/jquery/jquery1.8.3.min.js"></script>
<script src="<%=basePath%>/js/common/layer/layer.js"></script>
<script src="<%=basePath%>/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="<%=basePath%>/js/terminology.search.js"></script>
</head>
<body data-target="#one" data-spy="scroll">
	<div  id="terminology_index_page">
		<%--引入头部--%>
		<jsp:include page="../common/config/top.jsp"></jsp:include>
		<div class="container" style="padding-bottom: 15px; min-height: 300px; margin-top: 40px;">
			<div class="row">
				<%--引入左侧菜单--%>
				<jsp:include page="../common/config/terminology.left.jsp"></jsp:include>
				<div class="col-md-10 col-xs-10">
					<h2>关键词收藏列表</h2>
					<hr>
					<form method="post" action="" id="formId" class="form-inline">
					</form>
					<table class="table table-bordered">
						<tr>
							<th width="10%">物资ID</th>
							<th width="30%">物资名称</th>
							<th width="25%">收藏时间</th>
							<th width="10%">状态</th>
							<th width="25%">操作</th>
						</tr>
						<c:choose>
							<c:when test="${page != null && fn:length(page.list) gt 0}">
								<c:forEach items="${page.list}" var="materialCollected">
									<tr>
										<td>${materialCollected.material_id}</td>
										<td>${materialCollected.material_name}</td>
										<td><fmt:formatDate value="${materialCollected.collected_time}" pattern="yyyy年MM月dd日HH点mm分ss秒" /></td>
										<c:if test="${materialCollected.status==true}">
											<td>有效</td>
										</c:if>
										<c:if test="${materialCollected.status==false}">
											<td>无效</td>
										</c:if>
										<td>
											<shiro:hasPermission name="/terminology/deleteTerm.shtml">
												<a class="btn btn-danger btn-sm" href="javascript:_delete([${materialCollected.id}]);"><span class="glyphicon glyphicon-trash"></span> 删除</a>
											</shiro:hasPermission>
											<shiro:hasPermission name="/terminology/add2cart.shtml">
												&nbsp;
												<c:if test="${materialCollected.status==true}">
													<a class="btn btn-info btn-sm" href="javascript:add2cart('${materialCollected.material_name}');"><span class="glyphicon glyphicon-plus"></span> 加入清单</a>
												</c:if>
											</shiro:hasPermission>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td class="text-center danger" colspan="5">没有收藏的关键词信息</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</table>
					<c:if test="${page != null && fn:length(page.list) gt 0}">
						<div class="pagination pull-right">${page.pageHtml}</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<script>
		$(document).ready(function(){
		 	$("#terminologyCenter").addClass("active");
		 	$("#termcollection").addClass("active");
		});	
	</script>
</body>
</html>