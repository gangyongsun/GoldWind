package com.sojson.terminology.dao;

import java.util.List;

import com.sojson.terminology.bo.Address;

/**
 * 地址信息接口
 * 
 * @author alvin
 *
 */
public interface AddressMapper {

	int insertSelective(Address address);

	int updateSelectiveByPrimaryKey(Address address);

	Address selectByPrimaryKey(Long id);

	Address selectByOrderId(String orderId);

	List<Address> findList(Long userId);

	int updateDefaultAddress4Reset(Long user_id);

	int setDefaultAddressByPrimaryKey(Long id);

}
