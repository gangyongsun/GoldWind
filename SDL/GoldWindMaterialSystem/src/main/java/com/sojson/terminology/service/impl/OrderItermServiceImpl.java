package com.sojson.terminology.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sojson.common.utils.LoggerUtils;
import com.sojson.common.utils.OrderStatus;
import com.sojson.common.utils.PaymentType;
import com.sojson.common.utils.UniqueOrderGenerate;
import com.sojson.core.mybatis.BaseMybatisDao;
import com.sojson.core.shiro.token.manager.TokenManager;
import com.sojson.terminology.bo.Order;
import com.sojson.terminology.bo.OrderAddress;
import com.sojson.terminology.bo.OrderItem;
import com.sojson.terminology.dao.OrderItemMapper;
import com.sojson.terminology.service.OrderAddressService;
import com.sojson.terminology.service.OrderItemService;
import com.sojson.terminology.service.OrderService;

/**
 * 购物车信息或订单中商品详情服务类实现
 * 
 * @author alvin
 *
 */
@Service("orderItemService")
public class OrderItermServiceImpl extends BaseMybatisDao<OrderItemMapper> implements OrderItemService {

	@Autowired
	OrderItemMapper orderItemMapper;
	
	@Autowired
	public OrderService orderService;
	
	@Autowired
	public OrderAddressService orderAddressService;

	@Override
	public int insert(OrderItem orderItem) {
		return orderItemMapper.insert(orderItem);
	}

	@Override
	public List<OrderItem> findList(Long user_id) {
		return orderItemMapper.findList(user_id);
	}

	@Override
	public int updateByPrimaryKey(OrderItem orderItem) {
		return orderItemMapper.updateByPrimaryKey(orderItem);
	}

	@Override
	public int updateByPrimaryKeySelective(OrderItem orderItem) {
		return orderItemMapper.updateByPrimaryKeySelective(orderItem);
	}

	@Override
	public int deleteByPrimaryKey(Long id) {
		return orderItemMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(OrderItem orderItem) {
		return orderItemMapper.insertSelective(orderItem);
	}

	@Override
	public List<OrderItem> findListByIds(Long userId, List<Long> ids) {
		return orderItemMapper.findListByIds(userId, ids);
	}

	@Override
	public int updateSelectedItem2AddOrderId(Long addressId,List<OrderItem> itemList) {
		/**
		 * 生成订单ID
		 */
		UniqueOrderGenerate idWorker = new UniqueOrderGenerate(0, 0);
		String orderId = idWorker.nextId();
		
		int count = 0;
		for (OrderItem orderItem : itemList) {
			orderItem.setOrder_id(orderId);
			/**
			 * 1.更新商品设置订单号
			 */
			count += this.updateByPrimaryKeySelective(orderItem);
		}

		/**
		 * 2.插入订单
		 */
		Order order=new Order();
		order.setOrder_id(orderId);
		order.setOrder_status(OrderStatus.TO_BE_PURCHASE.getIndex());
		Date date=new Date();
		order.setCreate_time(date);
		order.setUpdate_time(date);
		order.setUser_id(TokenManager.getUserId());
		order.setViewable(true);
		order.setPayment_type(PaymentType.NO_PAY.getIndex());
		orderService.insertSelective(order);
		
		/**
		 * 3.订单地址绑定
		 */
		OrderAddress orderAddress=new OrderAddress();
		orderAddress.setAddress_id(addressId);
		orderAddress.setOrder_id(orderId);
		orderAddressService.insert(orderAddress);
		
		return count;
	}

	@Override
	public List<OrderItem> findListByOrderId(String orderId) {
		return orderItemMapper.findListByOrderId(orderId);
	}

	@Override
	public Map<String, Object> deleteOrderItemsByIds(String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			int count = 0;
			String[] idArray = new String[] {};
			if (StringUtils.contains(ids, ",")) {
				idArray = ids.split(",");
			} else {
				idArray = new String[] { ids };
			}

			for (String id : idArray) {
				count += this.deleteByPrimaryKey(new Long(id));
			}
			resultMap.put("status", 200);
			resultMap.put("count", count);
			resultMap.put("message", "删除成功！");
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据IDS删除物资出现错误，ids[%s]", ids);
			resultMap.put("status", 500);
			resultMap.put("message", "删除出现错误，请刷新后再试！");
		}
		return resultMap;
	}

	@Override
	public OrderItem searchByNameAndUid(String itemName, Long uid) {
		return orderItemMapper.searchByNameAndUid(itemName,uid);
	}

}
