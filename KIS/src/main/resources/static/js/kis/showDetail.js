function praise(obj) {
//	console.log("operateValue原始值：",obj);
//	console.log("operateValue修改值：",getOperateValue(obj));
//	console.log("termID：",getTermId());
//	console.log(obj.children[0]);
	var flag=obj.id;//是否赞
	var elementId=obj.children[0].id;//标签ID
	
//	console.log(flag);
//	console.log(elementId);
	
	if(flag=="false"){
		//点击"赞"则变深色
		$("#"+elementId).attr("class","fa fa-thumbs-up");
		$("#"+elementId).text(" 取消赞");
	}else{
		//点击"取消赞"则变浅色
		$("#"+elementId).attr("class","fa fa-thumbs-o-up");
		$("#"+elementId).text(" 赞");
	}
	termOperate(getTermId(), "praise", getOperateValue(flag));
}

function trample(obj) {
	var flag=obj.id;//是否踩
	var elementId=obj.children[0].id;//标签ID

	if(flag=="false"){
		//点击"踩"则变深色
		$("#"+elementId).attr("class","fa fa-thumbs-down");
		$("#"+elementId).text(" 取消踩");
	}else{
		//点击"取消踩"则变浅色
		$("#"+elementId).attr("class","fa fa-thumbs-o-down");
		$("#"+elementId).text(" 踩");
	}
	termOperate(getTermId(), "trample", getOperateValue(flag));
}

function collect(obj) {
	var flag=obj.id;//是否收藏
	var elementId=obj.children[0].id;//标签ID

	if(flag=="false"){
		//点击"收藏"则变深色
		$("#"+elementId).attr("class","fa fa-heart");
		$("#"+elementId).text(" 移除收藏");
	}else{
		//点击"移除收藏"则变浅色
		$("#"+elementId).attr("class","fa fa-heart-o");
		$("#"+elementId).text(" 收藏");
	}
	termOperate(getTermId(), "collect", getOperateValue(flag));
}

function getTermId() {
	return $("#hiddenTermId").val();
}

function getOperateValue(operateValue){
	var newValue;
	if (operateValue == "true") {
		newValue = false;
	} else {
		newValue = true;
	}
	return newValue;
}

function termOperate(termId, operateType, operateValue) {
	$.ajax({
		type : "POST",
		url : '/kis/termOperate',
		data : {
			termId : termId,
			operateType : operateType,
			operateValue : operateValue
		},
		success : function(data) {
//			console.log(data);
		},
		error : function(data) {
			console.log("操作传输失败！");
		}
	});
}