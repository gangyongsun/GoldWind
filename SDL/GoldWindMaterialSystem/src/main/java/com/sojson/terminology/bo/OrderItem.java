package com.sojson.terminology.bo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 购物清单或订单相关商品详情
 * 
 * @author alvin
 *
 */
@Data
@NoArgsConstructor
public class OrderItem implements Serializable {

	private static final long serialVersionUID = 4961212636806067665L;

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 用户ID
	 */
	private Long user_id;

	/**
	 * 商品ID
	 */
	private String item_id;

	/**
	 * 订单ID
	 */
	private String order_id;

	/**
	 * 数量
	 */
	private int num;

	/**
	 * 商品名称
	 */
	private String item_name;

	/**
	 * 单价
	 */
	private BigDecimal price;

	/**
	 * 商品总金额
	 */
	private BigDecimal total_fee;

	/**
	 * 商品图片地址
	 */
	private String pic_path;
}
