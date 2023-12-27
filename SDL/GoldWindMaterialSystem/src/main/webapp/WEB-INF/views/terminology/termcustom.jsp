<%@ include file="../common/header.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8" />
<title>关键词自定义</title>
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
<link rel="icon" href="<%=basePath%>/favicon.ico" type="image/x-icon" />
<link rel="shortcut icon" href="<%=basePath%>/favicon.ico" />
<link href="<%=basePath%>/js/common/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet" />
<link href="<%=basePath%>/js/common/jquery/jquery.tagsinput.css" rel="stylesheet" />
<link href="<%=basePath%>/css/common/base.css" rel="stylesheet" />
<script src="<%=basePath%>/js/common/jquery/jquery1.8.3.min.js"></script>
<script src="<%=basePath%>/js/common/jquery/jquery.tagsinput.js"></script>
<script src="<%=basePath%>/js/common/layer/layer.js"></script>
<script src="<%=basePath%>/js/common/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="<%=basePath%>/js/terminology.search.js"></script>
</head>
<body data-target="#one" data-spy="scroll">
	<div id="terminology_index_page">
		<%--引入头部--%>
		<jsp:include page="../common/config/top.jsp"></jsp:include>
		<div class="container" style="padding-bottom: 15px; min-height: 300px; margin-top: 40px;">
			<div class="row">
				<%--引入左侧菜单--%>
				<jsp:include page="../common/config/terminology.left.jsp"></jsp:include>
				<div class="col-md-10 col-xs-10">
					<h2>关键词自定义</h2>
					<hr>
					<span class="label label-info">关键词自定义编辑</span>
					<input id="termCustomTag" type="text" class="tags"/>
					<hr>
					
					<span class="label label-info">新关键词</span>
					<h4><span id="customTerm" class="label label-success"></span></h4>
					<br><br>
					<button type="button" class="btn btn-success" onclick="addCustomTerm2Cart();"><span class="glyphicon glyphicon-plus"></span> 加入清单</button>
					<button type="button" class="btn btn-success" onclick="addCustomTerm2Collection();"><span class="glyphicon glyphicon-star-empty"></span> 加入收藏</button>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#terminologyCenter").addClass("active");
			$("#termcustom").addClass("active");
			
			$('#termCustomTag').tagsInput({
				width : 'auto',
				defaultText:'请输入...',
				onAddTag : function(tag) {
					$('#customTerm').text($('#termCustomTag').val().replace(/,/g, ""));
				},
				onRemoveTag : function(tag) {
					$('#customTerm').text($('#termCustomTag').val());
				}
			});
		});
		function addCustomTerm2Cart(){
			add2cart($('#customTerm').text());
		}
		function addCustomTerm2Collection(){
			add2collection($('#customTerm').text());
		}
	</script>
</body>
</html>