/**
 * 查看订单详情
 * 
 * @returns
 */
function orderDetail(orderId) {
	$.ajax({
		type : "POST",
		url : 'orderDetail.shtml',
		data : {
			orderId : orderId
		},
		success : function(data) {
			document.getElementById("content").innerHTML = data;
		},
		error : function(data) {
			layer.msg("查看订单详情失败！");
		}
	});
}
/**
 * 取消订单
 * 
 * @param
 * @returns
 */
function cancelOrder(orderId) {
	var index = layer.confirm("确定取消该订单？",function(){
		var load = layer.load();
		$.post('cancelOrder.shtml',{
				order_id : orderId
			},function(result){
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
		layer.close(index);
	});
}

/**
 * 更新订单状态
 * 
 * @param
 * @returns
 */
function updateOrderStatus(orderId,status) {
	var index = layer.confirm("确定更新订单状态？",function(){
		var load = layer.load();
		$.post('updateOrderStatus.shtml',{
				order_id : orderId,
				order_status : status
			},function(result){
				layer.close(load);
				if(result && result.status != 200){
					return layer.msg(result.message,so.default),!1;
				}else{
					layer.msg(result.message);
				}
				setTimeout(function(){
					$('#formDetail').submit();
				},1000);
		},'json');
		layer.close(index);
	});
}

/**
 * 填写物流单号，发货
 * 
 * @returns
 */
function shipping(orderId,status){
	var shipping_name = $('#shipping_name').val();
	var shipping_code  = $('#shipping_code').val();
	
	if($.trim(shipping_name) == ''){
		return layer.msg('物流供应商不能为空!',so.default),!1;
	}
	if($.trim(shipping_code) == ''){
		return layer.msg('物流单号不能为空!',so.default),!1;
	}
	var load = layer.load();
	$.post('updateOrderStatus.shtml',
		{
			order_id : orderId,
			order_status : status,
			shipping_name:shipping_name,
			shipping_code:shipping_code
		},function(result){
			layer.close(load);
			if(result && result.status != 200){
				return layer.msg(result.message,so.default),!1;
			}
			layer.msg(result.message);
			setTimeout(function(){
				$('#formDetail').submit();
			},1000);
		},'json');
}

/**
 * 填写评价
 * 
 * @returns
 */
function orderEvaluate(orderId){
	var buyer_message = $('#buyer_message').val();
	
	if($.trim(buyer_message) == ''){
		return layer.msg('评价内容不能为空!',so.default),!1;
	}
	
	var load = layer.load();
	$.post('updateOrderStatus.shtml',
		{
			order_id : orderId,
			buyer_message:buyer_message
		},function(result){
			layer.close(load);
			if(result && result.status != 200){
				return layer.msg(result.message,so.default),!1;
			}
			layer.msg(result.message);
			setTimeout(function(){
				$('#formDetail').submit();
			},1000);
		},'json');
}

/**
 * 物流跟踪
 * 
 * @param orderId
 * @returns
 */
function shippingDetail(orderId){
	return layer.msg("暂且没实现!",so.default),!1;
}

/**
 * 导出Excel
 * 
 * @returns
 */
function exportExcel(orderId){
    window.location.href="export2Excel.shtml?orderId="+orderId;
}

/**
 * 删除订单
 * 
 * @param orderId
 * @returns
 */
function deleteOrder(orderId){
	var index = layer.confirm("确定删除该订单？",function(){
	var load = layer.load();
	$.post('deleteOrder.shtml',
		{
			order_id : orderId
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
	});
}

/**
 * 根据订单状态过滤
 * 
 * @param status
 * @returns
 */
function orderStatusFilter(status){
	$.ajax({
		type : "POST",
		url : 'filterOrderByStatus.shtml',
		data : {
			status : status
		},
		success : function(data) {
			document.getElementById("content").innerHTML = data;
		},
		error : function(data) {
			layer.msg("查询过滤订单失败！");
		}
	});
}