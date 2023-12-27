<%@ include file="../common/header.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8" />
<title>购物清单</title>
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
<link rel="icon" href="<%=basePath%>/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/favicon.ico" />
<link href="<%=basePath%>/js/common/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet" />
<link href="<%=basePath%>/css/common/base.css" rel="stylesheet" />
<script src="<%=basePath%>/js/common/jquery/jquery1.8.3.min.js"></script>
<script src="<%=basePath%>/js/common/layer/layer.js"></script>
<script src="<%=basePath%>/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="<%=basePath%>/js/shiro.demo.js"></script>
<script src="<%=basePath%>/js/common.checkbox.js"></script>
<script src="<%=basePath%>/js/shoppingCart.js"></script>

</head>
<body data-target="#one" data-spy="scroll" id="content">
	<%--引入头部--%>
	<jsp:include page="../common/config/top.jsp"></jsp:include>
	<div class="container" style="padding-bottom: 15px; min-height: 300px; margin-top: 40px;">
		<div class="row">
			<div class="col-md-12">
				<h2>购物清单</h2>
				<hr>
				<form method="post" action="" id="formId" class="form-inline">
					<table class="table table-bordered">
						<tr>
							<th width="5%">
								<input type="checkbox" id="checkAll" />
							</th>
							<th width="10%">物资编号</th>
							<th width="45%">物资名称</th>
							<th width="30%">物资数量</th>
							<th width="10%">操作</th>
						</tr>
						<c:choose>
							<c:when test="${itemList != null && fn:length(itemList) gt 0}">
								<c:forEach items="${itemList}" var="item">
									<tr>
										<td>
											<input value="${item.id}" type="checkbox" id="subcheckbox" onclick="userCheck(this)"/>
										</td>
										<td>${item.item_id}</td>
										<td>${item.item_name}</td>
										<td>
											<button type="button" class="btn btn-info btn-sm" onclick="num_del('${item.id}')">
									          	<span class="glyphicon glyphicon-minus"></span>
									        </button>
												<span id="quantity_${item.id}">${item.num}</span>
											<button type="button" class="btn btn-info btn-sm" onclick="num_add('${item.id}')">
									          	<span class="glyphicon glyphicon-plus"></span>
									        </button>
										</td>
										<td>
											<shiro:hasPermission name="/order/deleteItem.shtml">
												<a class="btn btn-danger btn-sm" href="javascript:_delete([${item.id}]);"><span class="glyphicon glyphicon-trash"></span> 删除</a>
											</shiro:hasPermission>
											&nbsp;&nbsp;
											<%-- <shiro:hasPermission name="/order/move2collected.shtml">
												<i class="glyphicon glyphicon-plus"></i>
												<a href="javascript:_move2list([${item.id}]);">移入收藏</a>
											</shiro:hasPermission> --%>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td class="text-center danger" colspan="5">购物清单中没有商品！</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</table>
					<div class="well">
						<span class="">
							<shiro:hasPermission name="/order/deleteItem.shtml">
								<a class="btn btn-danger" onclick="deleteAll();"><span class="glyphicon glyphicon-trash"></span> 删除</a>
							</shiro:hasPermission>
							<%-- <shiro:hasPermission name="/order/move2collected.shtml">
								<a class="btn btn-success" onclick="addItem2collected();">移入收藏夹</a>
							</shiro:hasPermission> --%>
							<shiro:hasPermission name="/order/gotoCreateOrder.shtml">
								<a class="btn btn-success" onclick="gotoCreateOrder();"><span class="glyphicon glyphicon-arrow-right"></span> 去提交</a>
							</shiro:hasPermission>
						</span>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script>
		$(document).ready(function(){
		  	$("#orderCenter").addClass("active");
		});	
	</script>
</body>
</html>