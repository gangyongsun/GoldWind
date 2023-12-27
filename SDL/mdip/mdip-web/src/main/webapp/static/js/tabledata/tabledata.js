$(document).ready(function() {
	search();
});

/**
 * 查询按钮点击事件方法
 * 
 * @returns
 */
function search() {
	ajaxSubmit("#conditionSelectDataForm", fnSuccess);
}

/**
 * 加载数据
 * 
 * @param frm
 * @param data
 * @returns
 */
function fnSuccess(frm, data) {
	loadPagedData(frm,listDataResult, data);
}

/**
 * 遍历返回的数据
 * 
 * @param data
 * @returns
 */
function listDataResult(data) {
	// alert(data.result)
	tbody = "<tr class='success'><th>设备标识号</td><th  align='center'>设备类别</td><th  align='center'>参数编码</td><th  align='center'>失效模式编码</td><th  align='center'>数据来源</td></tr>";
	$.each(data.result, function(i, n) {
		var trs = "";
		trs += "<tr><td align='center'>" + n.sbbsh + "</td><td align='center'>"
				+ n.sblb + "</td><td align='center'>" + n.csbm
				+ "</td><td align='center'>" + n.sxcode
				+ "</td><td align='center'>" + n.sjly + "</td></tr>";
		tbody += trs;
	});
	$("#recordTable").html(tbody);
}

function searchData() {
	var form = $('#conditionSelectDataForm');
	$.ajax({
		type : "post",
		url : form.attr('action') + "/conditionList",
		data : {
			area : $("#area").val(),
			itemType : $("#itemType").val(),
			tableName : $("#tableName").val()
		},
		success : function(data) {
			var $divTable = $('#conditionTable');
			$divTable.replaceWith(data);
		}
	});
}

function selectAreaChange(param) {
	if (param != "err") {
		var object = document.getElementById(param);
		var area = object.options[object.selectedIndex].value;
		var form = $('#conditionSelectForm');

		if (param == "area") {
			if (area != "err") {
				$.ajax({
					type : "post",
					url : form.attr('action') + "/showSelectOptions",
					data : {
						param : area
					},
					success : function(data) {
						$("#itemType").replaceWith(data);
						alert($("#itemSelectOptionReplace").html())
					}
				});
			}
		} else if (param == "itemType") {
			alert("itemType");
		} else if (param == "tableName") {
			alert("tableName");
		}
	}
}
