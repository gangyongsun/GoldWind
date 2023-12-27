package com.sojson.terminology.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sojson.terminology.bo.OrderItem;

/**
 * 购物车或订单相关商品详情接口
 * 
 * @author alvin
 *
 */
public interface OrderItemMapper {

	List<OrderItem> findList(Long user_id);

	List<OrderItem> findListByIds(@Param("user_id") Long user_id, @Param("ids") List<Long> ids);

	List<OrderItem> findListByOrderId(String orderId);

	int insert(OrderItem orderItem);

	int insertSelective(OrderItem orderItem);

	int updateByPrimaryKey(OrderItem orderItem);

	int updateByPrimaryKeySelective(OrderItem orderItem);

	int deleteByPrimaryKey(Long id);

	OrderItem searchByNameAndUid(@Param("itemName") String itemName, @Param("uid") Long uid);

}
