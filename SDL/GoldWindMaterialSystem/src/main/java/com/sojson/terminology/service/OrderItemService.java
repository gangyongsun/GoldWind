package com.sojson.terminology.service;

import java.util.List;
import java.util.Map;

import com.sojson.terminology.bo.OrderItem;

public interface OrderItemService {

	/**
	 * 向购物车添加商品
	 * 
	 * @param orderItem
	 * @return
	 */
	int insert(OrderItem orderItem);

	/**
	 * 向购物车添加商品
	 * 
	 * @param orderItem
	 * @return
	 */
	int insertSelective(OrderItem orderItem);

	/**
	 * 查询购物车列表
	 * 
	 * @param user_id
	 * @return
	 */
	List<OrderItem> findList(Long user_id);

	/**
	 * 更新购物车中的商品信息
	 * 
	 * @param orderItem
	 * @return
	 */
	int updateByPrimaryKeySelective(OrderItem orderItem);

	/**
	 * 更新购物车中的多个商品信息，设置订单号<br>
	 * 插入订单
	 * 
	 * @param addressId 地址ID
	 * @param itemList  选中的商品
	 * @return
	 */
	int updateSelectedItem2AddOrderId(Long addressId, List<OrderItem> itemList);

	/**
	 * 更新购物车中的商品信息
	 * 
	 * @param orderItem
	 * @return
	 */
	int updateByPrimaryKey(OrderItem orderItem);

	/**
	 * 删除购物车中的商品
	 * 
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * 根据用户id 和指定的商品id列表查询购物车中的商品
	 * 
	 * @param userId 用户ID
	 * @param ids    购物车中商品id列表
	 * @return
	 */
	List<OrderItem> findListByIds(Long userId, List<Long> ids);

	/**
	 * 根据订单ID查询订单物资信息
	 * 
	 * @param orderId
	 * @return
	 */
	List<OrderItem> findListByOrderId(String orderId);

	/**
	 * 根据主键id列表删除
	 * 
	 * @param ids
	 * @return
	 */
	Map<String, Object> deleteOrderItemsByIds(String ids);

	/**
	 * 根据物资名和用户ID查询购物车中是否已存在该物资
	 * 
	 * @param itemName
	 * @param uid
	 * @return
	 */
	OrderItem searchByNameAndUid(String itemName, Long uid);

}
