/**
 * 查询关键词
 * 
 * @returns
 */
function searchTerm(){
	var original_languageId = $("#languageId").val();
	var original_findContent = $("#findContent").val();
	var load = layer.load();
	$.ajax({
		type : "POST",
		url : 'search.shtml',
		data : {
			findContent : original_findContent,
			languageId : original_languageId
		},
		success : function(data) {
			layer.close(load);
			document.getElementById("terminology_index_page").innerHTML=data;
			$("#languageId").val(original_languageId);
			$("#findContent").val(original_findContent);
			$("#termsearch").addClass("active");
		  	$("#findContent").focus();
		},
		error : function(data) {
			console.log("搜索失败");
		}
	});
}

/**
 * 加入购物清单
 * 
 * @param name
 * @returns
 */
function add2cart(name){
	if(name!=null&&name!=''){
		$.post('add2cart.shtml',{item_name:name,item_id:'1004'},function(result){
			if(result && result.status != 200){
				return layer.msg(result.message),!0;
			}else{
				layer.msg(result.message);
			}
		},'json');
	}else{
		layer.msg("内容为空，加入购物清单失败!");
	}
}

/**
 * 关键词加入收藏
 * 
 * @param ids
 * @returns
 */
function add2collection(name){
	if(name!=null&&name!=''){
		$.post('add2collection.shtml',{material_name:name,material_id:'1004'},function(result){
			if(result && result.status != 200){
				return layer.msg(result.message),!0;
			}else{
				layer.msg(result.resultMsg);
	//			$("#"+name+"_li").removeClass("glyphicon-star-empty");
	//			$("#"+name+"_li").addClass("glyphicon-star");
	//			$("#"+name+"__collect").text("已收藏");
	//			$("#"+name+"__collect").attr('href',"javascript:removeFromCollection("+name+");");
			}
		},'json');
	}else{
		layer.msg("内容为空，关键词加入收藏失败!");
	}
}

/**
 * 关键词取消收藏
 * 
 * @param ids
 * @returns
 */
function removeFromCollection(name){
	var load = layer.load();
	$.post('removeFromCollection.shtml',{material_name:name},function(result){
		layer.close(load);
		if(result && result.status != 200){
			return layer.msg(result.message),!0;
		}else{
			layer.msg(result.resultMsg);
//			$("#"+name+"_li").removeClass("glyphicon-star-empty");
//			$("#"+name+"_li").addClass("glyphicon-star");
//			$("#"+name+"__collect").text("已收藏");
//			$("#"+name+"__collect").attr('href',"javascript:removeFromCollection("+name+");");
		}
	},'json');
}


/**
 * 根据收藏物资的主键ID数组删除
 * 
 * @param ids
 * @returns
 */
function _delete(ids){
	var index = layer.confirm("确定将这"+ ids.length +"个物资移除收藏夹？",function(){
		var load = layer.load();
		$.post('deleteMaterialByIds.shtml',{ids:ids.join(',')},function(result){
			layer.close(load);
			if(result && result.status != 200){
				return layer.msg(result.message,so.default),!0;
			}else{
				layer.msg('删除成功!');
				setTimeout(function(){
					$('#formId').submit();
				},1000);
			}
		},'json');
		layer.close(index);
	});
}