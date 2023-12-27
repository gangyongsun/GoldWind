/**
 * 减少数量
 * 
 * @param id
 * @returns
 */
function num_del(id) {
	var num = parseInt($('#quantity_' + id).text()) - 1;
	if (num <= 1) {
		$('#quantity_' + id).text(1);
		num=1;
	} else {
		$('#quantity_' + id).text(num);
	}
	updateNum(id, num);
}

/**
 * 增加数量
 * 
 * @param id
 * @returns
 */
function num_add(id) {
	var num = parseInt($('#quantity_' + id).text()) + 1;
	$('#quantity_' + id).text(num);
	updateNum(id, num);
}

/**
 * 更新购物清单里Item的数量
 * 
 * @param id
 *            Item 主键
 * @param num
 *            Item 新数量
 * @returns
 */
function updateNum(id, num) {
//	var load = layer.load();
	$.post('updateItemNum.shtml', {
		id : id,
		num : num
	}, function(result) {
//		layer.close(load);
//		if (result && result.status != 200) {
//			return layer.msg(result.message), !0;
//		} else {
//			layer.msg(result.message);
//		}
	}, 'json');
}

/**
 * 去提交订单，转到另一个页面填写收货人等信息
 * 
 * @returns
 */
function gotoCreateOrder() {
	var checkeds = $('[id=subcheckbox]:checked');
	if(!checkeds.length){
		return layer.msg('请选择要下单的物资!',so.default),!0;
	}
	var array = [];
	checkeds.each(function(){
		array.push(this.value);
	});
	
	$.ajax({
		type : "POST",
		url : 'gotoCreateOrder.shtml',
		data : {
			ids:array.join(',')
		},
		success : function(data) {
			document.getElementById("content").innerHTML=data;
		},
		error : function(data) {
			console.log("查询下单物资失败!");
		}
	});
}


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
			layer.msg('新增地址成功!');
			setTimeout(function(){
				$('#formaddress').submit();
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
 * 提交订单
 * 
 * @returns
 */
function createOrder() {
	var checkeds = $('[name=receiverAddress]:checked');
	if(!checkeds.length){
		return layer.msg('请选择收货地址!',so.default),!0;
	}
	var addressId=checkeds[0].value;
//	console.log(addressId);
	
	var termIds=$('[id=termId]');
	var array = [];
	termIds.each(function(){
		array.push(this.value);
	});
	
//	console.log(array);
	
	$.ajax({
		type : "POST",
		url : 'createOrder.shtml',
		data : {
			addressId:addressId,
			termIds:array.join(',')
		},
		success : function(data) {
			layer.msg(data.message);
			setTimeout(function() {
				// 转到我的订单页面
				window.location.href = "index.shtml";
			}, 2000)
		},
		error : function(data) {
			layer.msg(data.message);
		}
	});
}


/**
 * 根据购物清单中物资的主键ID数组删除
 * 
 * @param ids
 * @returns
 */
function _delete(ids){
	var index = layer.confirm("确定移除这"+ ids.length +"个物资？",function(){
		var load = layer.load();
		$.post('deleteOrderItemsByIds.shtml',{ids:ids.join(',')},function(result){
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
 * 删除多个物资
 * 
 * @returns
 */
function deleteAll(){
	var checkeds = $('[id=subcheckbox]:checked');
	if(!checkeds.length){
		return layer.msg('请选择要删除的物资!',so.default),!0;
	}
	var array = [];
	checkeds.each(function(){
		array.push(this.value);
	});
	return _delete(array);
};

/**
 * 设为默认地址
 * 
 * @param addressId
 * @returns
 */
function setAsDefaultAddress(addressId){
	$.post('setAsDefaultAddress.shtml',{addressId:addressId},function(result){
		if(result && result.status != 200){
			return layer.msg(result.message,so.default),!0;
		}else{
			$("#receiverAddressId_"+addressId).attr("checked","checked");
			layer.msg(result.message);
		}
	},'json');
}