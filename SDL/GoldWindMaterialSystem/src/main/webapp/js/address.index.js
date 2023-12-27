/**
 * 添加收货人地址
 * 
 * @returns
 */
function addAddress(){
	var receiver_name = $('#receiver_name').val();
	var receiver_phone  = $('#receiver_phone').val();
	var receiver_mobile  = $('#receiver_mobile').val();
	var receiver_address  = $('#receiver_address').val();
	var receiver_state  = $('#receiver_state').val();
	var receiver_city  = $('#receiver_city').val();
	var receiver_district  = $('#receiver_district').val();
	var receiver_zip  = $('#receiver_zip').val();
	
	if($.trim(receiver_name) == ''){
		return layer.msg('收货人姓名不能为空!',so.default),!1;
	}
	if($.trim(receiver_mobile) == ''){
		return layer.msg('移动电话不能为空!',so.default),!1;
	}
	if($.trim(receiver_address) == ''){
		return layer.msg('收货地址不能为空!',so.default),!1;
	}
	if($.trim(receiver_state) == ''){
		return layer.msg('省份不能为空!',so.default),!1;
	}
	if($.trim(receiver_city) == ''){
		return layer.msg('城市不能为空!',so.default),!1;
	}
	if($.trim(receiver_district) == ''){
		return layer.msg('区县不能为空!',so.default),!1;
	}
	
	var load = layer.load();
	$.post('createAddress.shtml',
		{
			receiver_name:receiver_name,
			receiver_phone:receiver_phone,
			receiver_mobile:receiver_mobile,
			receiver_address:receiver_address,
			receiver_state:receiver_state,
			receiver_city:receiver_city,
			receiver_district:receiver_district,
			receiver_zip:receiver_zip
		},function(result){
			layer.close(load);
			if(result && result.status != 200){
				return layer.msg(result.message,so.default),!1;
			}
			layer.msg(result.message);
			setTimeout(function(){
				$('#formId').submit();
			},1000);
		},'json');
}


/**
 * 根据ID删除地址
 * 
 * @param id
 * @returns
 */
function deleteAddress(id){
	var index = layer.confirm("确定删除改地址？",function(){
		var load = layer.load();
		$.post('deleteAddress.shtml',{id:id},function(result){
			layer.close(load);
			if(result && result.status != 200){
				return layer.msg(result.message,so.default),!0;
			}else{
				layer.msg(result.message);
				setTimeout(function(){
					$('#formId').submit();
				},1000);
			}
		},'json');
		layer.close(index);
	});
}

/**
 * 地址详情
 * 
 * @param addressId
 * @returns
 */
function addressDetail(addressId) {
	$.ajax({
		type : "POST",
		url : 'addressDetail.shtml',
		data : {
			id : addressId
		},
		success : function(data) {
			document.getElementById("content").innerHTML = data;
		},
		error : function(data) {
			layer.msg("查看地址信息详情失败！");
		}
	});
}

/**
 * 编辑地址信息
 * 
 * @param addressId
 * @returns
 */
function editAddress(addressId){
	$.ajax({
		type : "POST",
		url : 'editAddress.shtml',
		data : {
			id : addressId
		},
		success : function(data) {
			document.getElementById("content").innerHTML = data;
		},
		error : function(data) {
			layer.msg("地址信息修改页面跳转失败！");
		}
	});
}

/**
 * 更新地址信息
 * @returns
 */
function updateAddress(){
	var receiver_name = $('#receiver_name').val();
	var receiver_phone  = $('#receiver_phone').val();
	var receiver_mobile  = $('#receiver_mobile').val();
	var receiver_address  = $('#receiver_address').val();
	var receiver_state  = $('#receiver_state').val();
	var receiver_city  = $('#receiver_city').val();
	var receiver_district  = $('#receiver_district').val();
	var receiver_zip  = $('#receiver_zip').val();
	
	if($.trim(receiver_name) == ''){
		return layer.msg('收货人姓名不能为空!',so.default),!1;
	}
	if($.trim(receiver_mobile) == ''){
		return layer.msg('移动电话不能为空!',so.default),!1;
	}
	if($.trim(receiver_address) == ''){
		return layer.msg('收货地址不能为空!',so.default),!1;
	}
	if($.trim(receiver_state) == ''){
		return layer.msg('省份不能为空!',so.default),!1;
	}
	if($.trim(receiver_city) == ''){
		return layer.msg('城市不能为空!',so.default),!1;
	}
	if($.trim(receiver_district) == ''){
		return layer.msg('区县不能为空!',so.default),!1;
	}
	if($.trim(receiver_zip) == ''){
		return layer.msg('邮编不能为空!',so.default),!1;
	}
	
	var load = layer.load();
	$.post('updateAddress.shtml',{
			id:$('#id').val(),
			receiver_name:receiver_name,
			receiver_phone:receiver_phone,
			receiver_mobile:receiver_mobile,
			receiver_address:receiver_address,
			receiver_state:receiver_state,
			receiver_city:receiver_city,
			receiver_district:receiver_district,
			receiver_zip:receiver_zip
		},function(result){
			layer.close(load);
			if(result && result.status != 200){
				return layer.msg(result.message,so.default),!1;
			}
			layer.msg(result.message);
			setTimeout(function(){
				$('#addressEdit').submit();
			},1000);
		},'json');
	layer.close(index);
}