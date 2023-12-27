package com.sojson.terminology.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sojson.core.mybatis.BaseMybatisDao;
import com.sojson.core.mybatis.page.Pagination;
import com.sojson.terminology.bo.Order;
import com.sojson.terminology.dao.OrderMapper;
import com.sojson.terminology.service.OrderService;

/**
 * 订单服务类实现
 * 
 * @author alvin
 *
 */
@Service("orderService")
public class OrderServiceImpl extends BaseMybatisDao<OrderMapper> implements OrderService {

	@Autowired
	OrderMapper orderMapper;

	@Override
	public int insert(Order order) {
		return orderMapper.insert(order);
	}

	@Override
	public int insertSelective(Order order) {
		return orderMapper.insertSelective(order);
	}

	@Override
	public Order selectByPrimaryKey(String orderId) {
		return orderMapper.selectByPrimaryKey(orderId);
	}

	@Override
	public int updateByPrimaryKeySelective(Order order) {
		return orderMapper.updateByPrimaryKeySelective(order);
	}

	@Override
	public int updateByPrimaryKey(Order order) {
		return orderMapper.updateByPrimaryKey(order);
	}

	@Override
	public Pagination<Order> findByPage(Map<String, Object> resultMap, Integer pageNo, Integer pageSize) {
		return super.findPage(resultMap, pageNo, pageSize);
	}

}
