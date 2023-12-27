package com.sojson.terminology.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sojson.common.controller.BaseController;
import com.sojson.common.model.UUser;
import com.sojson.common.utils.LoggerUtils;
import com.sojson.common.utils.OrderStatus;
import com.sojson.common.utils.PropertiesUtil;
import com.sojson.core.mybatis.page.Pagination;
import com.sojson.core.shiro.token.manager.TokenManager;
import com.sojson.permission.service.RoleService;
import com.sojson.terminology.bo.Address;
import com.sojson.terminology.bo.Order;
import com.sojson.terminology.bo.OrderItem;
import com.sojson.terminology.service.AddressService;
import com.sojson.terminology.service.OrderAddressService;
import com.sojson.terminology.service.OrderItemService;
import com.sojson.terminology.service.OrderService;
import com.sojson.user.service.UUserService;

/**
 * 订单控制类
 * 
 * @author alvin
 *
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("order")
public class OrderController extends BaseController {

	@Autowired
	public OrderService orderService;

	@Autowired
	public OrderItemService orderItemService;

	@Autowired
	public AddressService addressService;

	@Autowired
	public OrderAddressService orderAddressService;

	@Autowired
	public UUserService userService;

	@Autowired
	public RoleService roleService;

	/**
	 * 跳转到我的清单或订单首页
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("index")
	public ModelAndView index(ModelMap modelMap, Integer pageNo, String findContent) {
		modelMap.put("findContent", findContent);
		Set<String> roleSet = roleService.findRoleByUserId(TokenManager.getUserId());
		String admin_role_type = PropertiesUtil.getValueByKey("ADMIN_ROLE_TYPE", "config.properties");
		String purchaser_role_type = PropertiesUtil.getValueByKey("PURCHASER_ROLE_TYPE", "config.properties");
		if (null != roleSet && !roleSet.contains(admin_role_type) && !roleSet.contains(purchaser_role_type)) {
			modelMap.put("user_id", TokenManager.getUserId());
		}
		modelMap.put("viewable", true);
		Pagination<Order> orders = orderService.findByPage(modelMap, pageNo, pageSize);
		return new ModelAndView("order/index", "page", orders);
	}
	
	/**
	 * 跳转到我的清单或订单首页
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("filterOrderByStatus")
	public ModelAndView filterOrderByStatus(ModelMap modelMap, Integer pageNo, String status) {
		Set<String> roleSet = roleService.findRoleByUserId(TokenManager.getUserId());
		String admin_role_type = PropertiesUtil.getValueByKey("ADMIN_ROLE_TYPE", "config.properties");
		String purchaser_role_type = PropertiesUtil.getValueByKey("PURCHASER_ROLE_TYPE", "config.properties");
		if (null != roleSet && !roleSet.contains(admin_role_type) && !roleSet.contains(purchaser_role_type)) {
			modelMap.put("user_id", TokenManager.getUserId());
		}
		modelMap.put("order_status", status);
		Pagination<Order> orders = orderService.findByPage(modelMap, pageNo, pageSize);
		return new ModelAndView("order/index", "page", orders);
	}

	/**
	 * 跳转到购物清单页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("shopping_cart")
	public ModelAndView shoppingCart(ModelMap modelMap) {
		List<OrderItem> itemList = orderItemService.findList(TokenManager.getUserId());
		modelMap.put("itemList", itemList);
		return new ModelAndView("order/shopping_cart");
	}

	/**
	 * 逗号分隔的ID字符串转换为Long list
	 * 
	 * @param ids 逗号分隔的字符串
	 * @return
	 */
	private List<Long> convertIDS2List(String ids) {
		String[] idArray = new String[] {};
		if (StringUtils.contains(ids, ",")) {
			idArray = ids.split(",");
		} else {
			idArray = new String[] { ids };
		}

		List<Long> idList = new ArrayList<Long>();
		for (String id : idArray) {
			idList.add(Long.parseLong(id));
		}
		return idList;
	}

	/**
	 * 下一步跳转到提交购物清单页面<br>
	 * 要填写收货人地址等信息
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("gotoCreateOrder")
	public ModelAndView gotoCreateOrder(ModelMap modelMap, String ids) {
		List<Long> idList = convertIDS2List(ids);
		/**
		 * 查询选中的商品
		 */
		List<OrderItem> itemList = orderItemService.findListByIds(TokenManager.getUserId(), idList);

		/**
		 * 用户地址列表信息
		 */
		List<Address> addressList = addressService.findList(TokenManager.getUserId());

		modelMap.put("addressList", addressList);
		modelMap.put("itemList", itemList);
		return new ModelAndView("order/createOrder");
	}

	/**
	 * 更新购物清单中商品数量
	 * 
	 * @param orderItem
	 * @return
	 */
	@RequestMapping(value = "updateItemNum", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateItemNum(OrderItem orderItem) {
		try {
			int result = orderItemService.updateByPrimaryKeySelective(orderItem);
			resultMap.put("status", 200);
			resultMap.put("message", "成功更新" + result + "条物资！");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "更新失败，请刷新后再试！");
			LoggerUtils.fmtError(getClass(), e, "更新物资数量报错：source[%s]", orderItem.toString());
		}
		return resultMap;
	}

	/**
	 * 创建订单
	 * 
	 * @return
	 */
	@RequestMapping(value = "createOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createOrder(String addressId, String termIds) {
		try {
			/**
			 * 查询选中的商品
			 */
			List<Long> idList = convertIDS2List(termIds);
			List<OrderItem> itemList = orderItemService.findListByIds(TokenManager.getUserId(), idList);

			/**
			 * 下单分3步：更新购物车选择商品设置订单号；插入订单；订单地址绑定；
			 */
			orderItemService.updateSelectedItem2AddOrderId(new Long(addressId), itemList);

			resultMap.put("status", 200);
			resultMap.put("message", "下单成功！");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "下单失败！");
			LoggerUtils.fmtError(getClass(), e, "下单失败！");
		}
		return resultMap;
	}

	/**
	 * 查看订单详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "orderDetail")
	public ModelAndView orderDetail(ModelMap modelMap, String orderId) {
		Order order = orderService.selectByPrimaryKey(orderId);
		Address address = addressService.selectByOrderId(orderId);
		List<OrderItem> itemList = orderItemService.findListByOrderId(orderId);
		UUser user = userService.selectByPrimaryKey(order.getUser_id());
		if (null != order.getPurchaser_id()) {
			UUser purchaser = userService.selectByPrimaryKey(order.getPurchaser_id());
			modelMap.put("purchaser", purchaser);
		}

		modelMap.put("order", order);
		modelMap.put("address", address);
		modelMap.put("itemList", itemList);
		modelMap.put("user", user);

		return new ModelAndView("order/orderDetail");
	}

	/**
	 * 取消订单
	 * 
	 * @param orderItem
	 * @return
	 */
	@RequestMapping(value = "cancelOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cancelOrder(Order order) {
		try {
			order.setOrder_status(OrderStatus.TRADE_CLOSED.getIndex());
			Date date = new Date();
			order.setUpdate_time(date);
			order.setClose_time(date);
			orderService.updateByPrimaryKeySelective(order);
			resultMap.put("status", 200);
			resultMap.put("message", "订单：" + order.getOrder_id() + "成功取消！");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "订单：" + order.getOrder_id() + "取消失败！");
			LoggerUtils.fmtError(getClass(), e, "订单：" + order.getOrder_id() + "取消失败！source[%s]", order.toString());
		}
		return resultMap;
	}

	/**
	 * 更新订单状态
	 * 
	 * @param orderItem
	 * @return
	 */
	@RequestMapping(value = "updateOrderStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateOrderStatus(Order order) {
		Order entity = orderService.selectByPrimaryKey(order.getOrder_id());
		try {
			/**
			 * 设置采购者ID
			 */
			Set<String> roleSet = roleService.findRoleByUserId(TokenManager.getUserId());
			String purchaser_role_type = PropertiesUtil.getValueByKey("PURCHASER_ROLE_TYPE", "config.properties");
			if (null != roleSet && roleSet.contains(purchaser_role_type) && null == entity.getPurchaser_id()) {
				order.setPurchaser_id(TokenManager.getUserId());
			}
			/**
			 * 每次操作设置updateTime
			 */
			Date date = new Date();
			order.setUpdate_time(date);
			/**
			 * 如果状态是已发货，设置发货时间
			 */
			if (order.getOrder_status() == OrderStatus.SHIPPED.getIndex() && null == entity.getConsign_time()) {
				order.setConsign_time(date);
			}
			/**
			 * 如果状态是交易成功，设置交易完成时间
			 */
			if (order.getOrder_status() == OrderStatus.TRADE_SUCCESS.getIndex() && null == entity.getEnd_time()) {
				order.setEnd_time(date);
			}
			/**
			 * 如果用户还未评价，并且评价内容不为空，设置评价内容和评价状态
			 */
			if (null != order.getBuyer_message() && !"".equals(order.getBuyer_message()) && !entity.isBuyer_rate()) {
				order.setBuyer_rate(true);
				order.setBuyer_nickname(TokenManager.getNickname());
				order.setOrder_status(entity.getOrder_status());
				// TODO
				// 只传 order_id, buy_message到后台，order_status 成了0，很奇怪？
			}
			orderService.updateByPrimaryKeySelective(order);
			resultMap.put("status", 200);
			resultMap.put("message", "订单状态更新：" + order.getOrder_id() + "成功！");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "订单状态更新：" + order.getOrder_id() + "失败！");
			LoggerUtils.fmtError(getClass(), e, "订单状态更新：" + order.getOrder_id() + "失败！source[%s]", order.toString());
		}
		return resultMap;
	}

	/**
	 * 删除订单
	 * 
	 * @param order_id
	 * @return
	 */
	@RequestMapping(value = "deleteOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteOrder(String order_id) {
		Order order = null;
		try {
			order = orderService.selectByPrimaryKey(order_id);
			if (null != order) {
				order.setViewable(false);
				orderService.updateByPrimaryKeySelective(order);
				resultMap.put("status", 200);
				resultMap.put("message", "订单删除成功！");
			} else {
				resultMap.put("status", 500);
				resultMap.put("message", "订单删除失败！");
			}
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "订单状态更新：" + order.getOrder_id() + "失败！");
			LoggerUtils.fmtError(getClass(), e, "订单状态更新：" + order.getOrder_id() + "失败！source[%s]", order.toString());
		}
		return resultMap;
	}

	/**
	 * 根据主键ID数组删除购物清单中的条目
	 * 
	 * @param ids 如果有多个，以“,”间隔
	 * @return
	 */
	@RequestMapping(value = "deleteOrderItemsByIds", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteOrderItemsByIds(String ids) {
		return orderItemService.deleteOrderItemsByIds(ids);
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
	 * 设为默认地址
	 * 
	 * @param addressId
	 * @return
	 */
	@RequestMapping(value = "setAsDefaultAddress", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> setAsDefaultAddress(String addressId) {
		return addressService.updateDefaultAddress4Reset(new Long(addressId));
	}

	/**
	 * 导出excel
	 * 
	 * @return
	 */
	@RequestMapping(value = "export2Excel", method = RequestMethod.GET)
	@ResponseBody
	public void doExport2(String orderId, HttpServletResponse response) {
		List<OrderItem> orderItemList = orderItemService.findListByOrderId(orderId);

		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		XSSFWorkbook wb = null;
		try {
			// 第一步：定义一个新的工作簿
			wb = new XSSFWorkbook();
			// 第二步：创建一个Sheet页
			XSSFSheet sheet = wb.createSheet("startTimeendTime");
			sheet.setDefaultRowHeight((short) (2 * 256));// 设置行高
			sheet.setColumnWidth(0, 4000);// 设置列宽
			sheet.setColumnWidth(1, 5500);
			sheet.setColumnWidth(2, 5500);
			XSSFFont font = wb.createFont();
			font.setFontName("宋体");
			font.setFontHeightInPoints((short) 16);

			XSSFRow row = sheet.createRow(0);
			XSSFCell cell0 = row.createCell(0);
			cell0.setCellValue("物资编号");
			XSSFCell cell1 = row.createCell(1);
			cell1.setCellValue("物资名称");
			XSSFCell cell2 = row.createCell(2);
			cell2.setCellValue("数量");

			for (int i = 0; i < orderItemList.size(); i++) {
				XSSFRow rows = sheet.createRow(i + 1);
				XSSFCell cells0 = rows.createCell(0);
				cells0.setCellValue(orderItemList.get(i).getItem_id());
				XSSFCell cells1 = rows.createCell(1);
				cells1.setCellValue(orderItemList.get(i).getItem_name());
				XSSFCell cells2 = rows.createCell(2);
				cells2.setCellValue(orderItemList.get(i).getNum());
			}

			// 开始执行写入操作
			File file = new File(orderId + ".xls");
			wb.write(new FileOutputStream(file));

			response.setContentType("application/vnd.ms-excel; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + orderId + ".xlsx");
			in = new BufferedInputStream(new FileInputStream(file));
			out = new BufferedOutputStream(response.getOutputStream());
			byte[] data = new byte[1024];
			int len = 0;
			while (-1 != (len = in.read(data, 0, data.length))) {
				out.write(data, 0, len);
			}
		} catch (Exception e) {
			logger.error("download file encount error!");
		} finally {
			try {
				if (null != wb) {
					wb.close();
					wb = null;
				}
				if (null != in) {
					in.close();
					in = null;
				}
				if (null != out) {
					out.close();
					out = null;
				}
			} catch (IOException e) {
				logger.error("download file colse io encount error!");
			}
		}
	}

}
