/**
 * 显示弹框
 * @returns
 */
function showModal(modalName){
	$('#'+modalName+'').modal({backdrop: false,keyboard: true})
}

/**
 * 隐藏弹框
 * 
 * @param modalName
 * @returns
 */
function hideModal(modalName){
	$('#'+modalName+'').modal("hide");
}

/**
 * 刷新页面
 * 
 * @param tableName
 * @returns
 */
function refreshPage(tableName){
	$("#"+tableName+"").bootstrapTable('refresh');
}

/**
 * 获得选中
 * 
 * @param tableName
 * @returns
 */
function getSelections(tableName) {
	return $.map($("#"+tableName+"").bootstrapTable('getSelections'), function(row) {
		return row.id;
	});
}