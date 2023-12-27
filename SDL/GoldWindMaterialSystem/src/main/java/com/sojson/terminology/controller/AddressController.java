package com.sojson.terminology.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sojson.common.controller.BaseController;
import com.sojson.common.utils.LoggerUtils;
import com.sojson.core.mybatis.page.Pagination;
import com.sojson.core.shiro.token.manager.TokenManager;
import com.sojson.terminology.bo.Address;
import com.sojson.terminology.service.AddressService;

/**
 * 地址控制类
 * 
 * @author alvin
 *
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("address")
public class AddressController extends BaseController {

	@Autowired
	public AddressService addressService;

	/**
	 * 跳转到地址列表页面
	 * @param modelMap
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value = "index")
	public ModelAndView list(ModelMap modelMap, Integer pageNo) {
		modelMap.put("viewable", true);
		modelMap.put("user_id", TokenManager.getUserId());
		Pagination<Address> page = addressService.findByPage(modelMap, pageNo, pageSize);
		modelMap.put("page", page);
		return new ModelAndView("address/index");
	}
	
	/**
	 * 新增收货地址
	 * 
	 * @return
	 */
	@RequestMapping(value = "createAddress", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createAddress(Address address) {
		try {
			Date date = new Date();
			address.setCreate_time(date);
			address.setUpdate_time(date);
			address.setViewable(true);
			address.setUser_id(TokenManager.getUserId());
			addressService.insertSelective(address);
			resultMap.put("status", 200);
			resultMap.put("message", "新增收货地址成功！");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "新增收货地址失败！");
			LoggerUtils.fmtError(getClass(), e, "新增收货地址失败！");
		}
		return resultMap;
	}
	
	/**
	 * 地址编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "editAddress")
	public ModelAndView editAddress(ModelMap modelMap, String id) {
		Address address = addressService.selectByPrimaryKey(new Long(id));
		modelMap.put("address", address);
		return new ModelAndView("address/addressEdit");
	}

	/**
	 * 更新收货地址
	 * 
	 * @return
	 */
	@RequestMapping(value = "updateAddress", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAddress(Address address) {
		address.setViewable(true);
		address.setUpdate_time(new Date());
		try {
			addressService.updateSelectiveByPrimaryKey(address);
			resultMap.put("status", 200);
			resultMap.put("message", "地址更新成功！");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "地址更新失败！");
			LoggerUtils.fmtError(getClass(), e, "地址更新失败！");
		}
		return resultMap;
	}

	/**
	 * 删除收货地址,即更新为不可见
	 * 
	 * @return
	 */
	@RequestMapping(value = "deleteAddress", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAddress(Address address) {
		try {
			address.setViewable(false);
			addressService.updateSelectiveByPrimaryKey(address);
			resultMap.put("status", 200);
			resultMap.put("message", "删除地址成功！");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "删除地址失败！");
			LoggerUtils.fmtError(getClass(), e, "删除地址失败！");
		}
		return resultMap;
	}

	/**
	 * 查看订单详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "addressDetail")
	public ModelAndView addressDetail(ModelMap modelMap, String id) {
		Address address = addressService.selectByPrimaryKey(new Long(id));
		modelMap.put("address", address);
		return new ModelAndView("address/addressDetail");
	}

}
