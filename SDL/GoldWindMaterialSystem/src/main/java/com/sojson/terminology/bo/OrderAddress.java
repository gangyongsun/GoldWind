package com.sojson.terminology.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单地址对应关系类
 * 
 * @author alvin
 *
 */
@Data
@NoArgsConstructor
public class OrderAddress {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 订单ID
	 */
	private String order_id;

	/**
	 * 收货人地址主键
	 */
	private Long address_id;

}
