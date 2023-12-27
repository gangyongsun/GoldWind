/**
 * 删除多个角色
 * 
 * @returns
 */
function deleteAll(){
	var checkeds = $('[id=subcheckbox]:checked');
	if(!checkeds.length){
		return layer.msg('请选择要删除的选项!',so.default),!0;
	}
	var array = [];
	checkeds.each(function(){
		array.push(this.value);
	});
	return deleteById(array);
};

/**
 * 根据ID数组删除角色
 * 
 * @param ids
 * @returns
 */
function deleteById(ids){
	var index = layer.confirm("确定这"+ ids.length +"个角色？",function(){
		var load = layer.load();
		$.post('deleteRoleById.shtml',{ids:ids.join(',')},function(result){
			layer.close(load);
			if(result && result.status != 200){
				return layer.msg(result.message,so.default),!0;
			}else{
				layer.msg(result.resultMsg);
				setTimeout(function(){
					$('#formId').submit();
				},1000);
			}
		},'json');
		layer.close(index);
	});
}

/**
 * 添加角色
 * 
 * @returns
 */
function addRole(){
	var name = $('#name').val();
	var	type = $('#type').val();
	if($.trim(name) == ''){
		return layer.msg('角色名称不能为空!',so.default),!1;
	}
	if(!/^[a-z0-9A-Z]{6}$/.test(type)){
		return layer.msg('角色类型为6数字字母!',so.default),!1;
	}
	// loding
	var load = layer.load();
	$.post('addRole.shtml',{name:name,type:type},function(result){
		layer.close(load);
		if(result && result.status != 200){
			return layer.msg(result.message,so.default),!1;
		}else{
			layer.msg(result.message);
		}
		setTimeout(function(){
			$('#formId').submit();
		},1000);
	},'json');
}