package com.sojson.terminology.dao;

import com.sojson.terminology.bo.OrderAddress;

/**
 * 订单地址对应信息接口
 * 
 * @author alvin
 *
 */
public interface OrderAddressMapper {

	int insert(OrderAddress orderAddress);

	int insertSelective(OrderAddress orderAddress);

	OrderAddress selectByOrderId(String id);

}
