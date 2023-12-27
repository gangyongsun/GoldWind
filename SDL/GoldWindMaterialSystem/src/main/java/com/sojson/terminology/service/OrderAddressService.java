package com.sojson.terminology.service;

import com.sojson.terminology.bo.OrderAddress;

/**
 * 订单地址信息服务接口
 * 
 * @author alvin
 *
 */
public interface OrderAddressService {
	/**
	 * 新增订单和收货地址的对应信息
	 * 
	 * @param orderAddress 收货地址
	 * @return
	 */
	int insert(OrderAddress orderAddress);

	/**
	 * 根据订单号查询订单地址对应信息
	 * 
	 * @param orderId
	 * @return
	 */
	OrderAddress findByOrderId(String orderId);

}
