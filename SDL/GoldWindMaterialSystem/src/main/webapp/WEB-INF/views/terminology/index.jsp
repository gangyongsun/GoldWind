<%@ include file="../common/header.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8" />
<title>关键词搜索</title>
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
					<h2>关键词搜索</h2>
					<shiro:hasPermission name="/terminology/index.shtml">
						<hr>
						<form method="post" action="" id="formId" class="form-inline">
							<div class="form-group well">
								<select id="languageId" class="form-control" style="width: 100px;">
									<option value="LANGUAGE_ZH" selected="selected">中文</option>
									<option value="LANGUAGE_EN">English</option>
								</select>
								<input type="text" class="form-control" style="width: 600px;" value="${findContent}" name="findContent" id="findContent" placeholder="请输入...">
								<a class="btn btn-info btn-md" onclick="searchTerm();">
									<span class="glyphicon glyphicon-search"></span> 查询
								</a>
							</div>
						</form>
						<hr>
					</shiro:hasPermission>
					<table class="table table-bordered">
						<tr>
							<th>score</th>
							<th>termbaseId</th>
							<th>similarity</th>
							<th>languageId</th>
							<th>term</th>
							<th>操作</th>
						</tr>
						<c:choose>
							<c:when test="${termlist != null && fn:length(termlist) gt 0}">
								<c:forEach items="${termlist}" var="term">
									<tr>
										<td>${term.score}</td>
										<td>${term.termbaseId}</td>
										<td>${term.similarity}</td>
										<td>${term.languageId}</td>
										<td>${term.term}</td>
										<td>
											<shiro:hasPermission name="/terminology/add2collection.shtml">
											<c:if test="${term.collectStatus==false}">
												<a id="${term.term}_collect" class="btn btn-info btn-sm" href="javascript:add2collection('${term.term}');"><span class="glyphicon glyphicon-star-empty"></span> 加入收藏</a>
											</c:if>
											</shiro:hasPermission>
											<shiro:hasPermission name="/terminology/removeFromCollection.shtml">
											<c:if test="${term.collectStatus==true}">
												<a id="${term.term}_collect" class="btn btn-success btn-sm" href="javascript:removeFromCollection('${term.term}');"><span class="glyphicon glyphicon-star"></span> 取消收藏</a>
											</c:if>
											</shiro:hasPermission>
											<shiro:hasPermission name="/terminology/add2cart.shtml">
												&nbsp;
												<a class="btn btn-info btn-sm" href="javascript:add2cart('${term.term}');"><span class="glyphicon glyphicon-plus"></span> 加入清单</a>
											</shiro:hasPermission>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td class="text-center danger" colspan="6">没有匹配的关键词信息</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</table>
					<shiro:hasPermission name="/order/shoppingCart.shtml">
						<div class="well">
							<a class="btn btn-success" href="<%=basePath%>/order/shopping_cart.shtml"><span class="glyphicon glyphicon-shopping-cart"></span> goto我的清单</a>
						</div>
					</shiro:hasPermission>
				</div>
			</div>
		</div>
	</div>
	<script>
		$(document).ready(function(){
		  	$("#terminologyCenter").addClass("active");
		  	$("#termsearch").addClass("active");
		  	$("#findContent").focus();
		});	
	</script>
</body>
</html>