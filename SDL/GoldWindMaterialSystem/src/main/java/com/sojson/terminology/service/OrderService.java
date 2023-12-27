package com.sojson.terminology.service;

import java.util.Map;

import com.sojson.core.mybatis.page.Pagination;
import com.sojson.terminology.bo.Order;

/**
 * 订单服务类
 * 
 * @author alvin
 *
 */
public interface OrderService {

	/**
	 * 插入订单
	 * 
	 * @param order 订单
	 * @return
	 */
	int insert(Order order);

	/**
	 * 插入订单
	 * 
	 * @param order 订单
	 * @return
	 */
	int insertSelective(Order order);

	/**
	 * 根据主键(订单号)查询订单
	 * 
	 * @param orderId
	 * @return
	 */
	Order selectByPrimaryKey(String orderId);

	/**
	 * 更新选择的订单
	 * 
	 * @param order
	 * @return
	 */
	int updateByPrimaryKeySelective(Order order);

	/**
	 * 更新订单
	 * 
	 * @param order
	 * @return
	 */
	int updateByPrimaryKey(Order order);

	/**
	 * 订单分页查询
	 * 
	 * @param resultMap
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pagination<Order> findByPage(Map<String, Object> resultMap, Integer pageNo, Integer pageSize);

}
