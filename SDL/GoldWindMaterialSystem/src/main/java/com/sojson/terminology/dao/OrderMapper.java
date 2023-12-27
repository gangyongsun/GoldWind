package com.sojson.terminology.dao;

import com.sojson.terminology.bo.Order;

/**
 * 订单信息接口
 * 
 * @author alvin
 *
 */
public interface OrderMapper {

	int insert(Order record);

	int insertSelective(Order record);

	Order selectByPrimaryKey(String order_id);

	int updateByPrimaryKeySelective(Order record);

	int updateByPrimaryKey(Order record);

}
