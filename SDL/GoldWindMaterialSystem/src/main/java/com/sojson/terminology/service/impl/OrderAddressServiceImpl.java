package com.sojson.terminology.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sojson.core.mybatis.BaseMybatisDao;
import com.sojson.terminology.bo.OrderAddress;
import com.sojson.terminology.dao.OrderAddressMapper;
import com.sojson.terminology.service.OrderAddressService;

/**
 * 订单地址对应信息服务接口实现类
 * 
 * @author alvin
 *
 */
@Service("orderAddressService")
public class OrderAddressServiceImpl extends BaseMybatisDao<OrderAddress> implements OrderAddressService {

	
	@Autowired
	OrderAddressMapper orderAddressMapper;
	
	@Override
	public int insert(OrderAddress orderAddress) {
		return orderAddressMapper.insert(orderAddress);
	}

	@Override
	public OrderAddress findByOrderId(String orderId) {
		return orderAddressMapper.selectByOrderId(orderId);
	}

}
