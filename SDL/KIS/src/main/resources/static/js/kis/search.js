jQuery(document).ready(function() {
	/**
	 * 回车事件绑定
	 */
	document.onkeydown = function(event) {
		console.log(event);
		var e = event || window.event || arguments.callee.caller.arguments[0];
		if (e && e.keyCode == 13) {
			searchKeyInfo();
		}
	}
});

function searchKeyInfo() {
	var findContentVal = $("#findContent").val();
	$.ajax({
		type : "POST",
		url : 'searchAll',
		data : {
			findContent : findContentVal
		},
		success : function(data) {
			$('#keyInfoMain').html(data);
			$("#findContent").val(findContentVal);
			$("#findContent").focus();
		},
		error : function(data) {
			console.log("搜索失败");
		}
	});
}