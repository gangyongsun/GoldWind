package com.sojson.terminology.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.sojson.common.utils.LoggerUtils;
import com.sojson.core.mybatis.BaseMybatisDao;
import com.sojson.core.mybatis.page.Pagination;
import com.sojson.terminology.bo.Address;
import com.sojson.terminology.dao.AddressMapper;
import com.sojson.terminology.service.AddressService;

/**
 * 地址信息服务接口实现类
 * 
 * @author alvin
 *
 */
@Service("addressService")
public class AddressServiceImpl extends BaseMybatisDao<AddressMapper> implements AddressService {

	@Autowired
	AddressMapper addressMapper;

	@Override
	public List<Address> findList(Long userId) {
		return addressMapper.findList(userId);
	}

	@Override
	public int insertSelective(Address address) {
		return addressMapper.insertSelective(address);
	}

	@Override
	public int updateSelectiveByPrimaryKey(Address address) {
		return addressMapper.updateSelectiveByPrimaryKey(address);
	}

	@Override
	public Address selectByPrimaryKey(Long id) {
		return addressMapper.selectByPrimaryKey(id);
	}

	@Override
	public Pagination<Address> findByPage(ModelMap map, Integer pageNo, int pageSize) {
		return super.findPage(map, pageNo, pageSize);
	}

	@Override
	public Address selectByOrderId(String orderId) {
		return addressMapper.selectByOrderId(orderId);
	}

	@Override
	public Map<String, Object> updateDefaultAddress4Reset(Long addressId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Address address=addressMapper.selectByPrimaryKey(addressId);
			/**
			 * 首先，设置其他的地址默认值为false
			 */
			addressMapper.updateDefaultAddress4Reset(address.getUser_id());
			/**
			 * 之后，修改此地址默认为true
			 */
			address.setDefault_address(true);
			addressMapper.setDefaultAddressByPrimaryKey(addressId);
			
			resultMap.put("status", 200);
			resultMap.put("message", "默认收货地址设置成功！");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "默认收货地址设置失败！");
			LoggerUtils.fmtError(getClass(), e, "默认收货地址设置失败！");
		}
		return resultMap;
	}

}
