package com.sojson.terminology.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.ModelMap;

import com.sojson.core.mybatis.page.Pagination;
import com.sojson.terminology.bo.Address;

/**
 * 地址信息服务接口
 * 
 * @author alvin
 *
 */
public interface AddressService {

	/**
	 * 根据主键查询地址信息
	 * 
	 * @param id
	 * @return
	 */
	Address selectByPrimaryKey(Long id);

	/**
	 * 查询用户的地址信息列表
	 * 
	 * @param userId
	 * @return
	 */
	List<Address> findList(Long userId);

	/**
	 * 新增收货地址
	 * 
	 * @param address 收货地址
	 * @return
	 */
	int insertSelective(Address address);

	/**
	 * 更新收货地址
	 * 
	 * @param address 收货地址
	 */
	int updateSelectiveByPrimaryKey(Address address);

	/**
	 * 查询用户的地址信息列表
	 * 
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pagination<Address> findByPage(ModelMap map, Integer pageNo, int pageSize);

	/**
	 * 根据订单ID查询下单地址信息
	 * 
	 * @param orderId
	 * @return
	 */
	Address selectByOrderId(String orderId);

	/**
	 * 设为默认地址
	 * 
	 * @param long1
	 * @return
	 */
	Map<String, Object> updateDefaultAddress4Reset(Long addressId);

}
