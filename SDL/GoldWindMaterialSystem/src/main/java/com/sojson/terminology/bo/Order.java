package com.sojson.terminology.bo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单信息
 * 
 * @author alvin
 *
 */
@Data
@NoArgsConstructor
public class Order implements Serializable {

	private static final long serialVersionUID = 4087123347133197048L;

	/**
	 * 清单或订单ID,主键
	 */
	private String order_id;

	/**
	 * 实付金额
	 */
	private BigDecimal payment;

	/**
	 * 支付类型：<br>
	 * 1.在线支付；<br>
	 * 2.货到付款；<br>
	 * 3.无需支付；
	 */
	private int payment_type;

	/**
	 * 邮费
	 */
	private BigDecimal post_fee;

	/**
	 * 状态<br>
	 * 1.未付款；<br>
	 * 2.已付款；<br>
	 * 3.待采购；<br>
	 * 4.采购中；<br>
	 * 5.采购完成；<br>
	 * 6.未发货；<br>
	 * 7.已发货；<br>
	 * 8.交易成功；<br>
	 * 9.交易关闭；<br>
	 */
	private int order_status;

	/**
	 * 订单创建时间
	 */
	private Date create_time;

	/**
	 * 订单更新时间
	 */
	private Date update_time;

	/**
	 * 付款时间
	 */
	private Date payment_time;

	/**
	 * 发货时间
	 */
	private Date consign_time;

	/**
	 * 交易完成时间
	 */
	private Date end_time;

	/**
	 * 交易关闭时间
	 */
	private Date close_time;

	/**
	 * 物流名称
	 */
	private String shipping_name;

	/**
	 * 物流单号
	 */
	private String shipping_code;

	/**
	 * 用户ID
	 */
	private Long user_id;

	/**
	 * 采购用户ID
	 */
	private Long purchaser_id;

	/**
	 * 买家留言
	 */
	private String buyer_message;

	/**
	 * 买家昵称
	 */
	private String buyer_nickname;

	/**
	 * 买家是否已评价
	 */
	private boolean buyer_rate;

	/**
	 * 订单是否可见
	 */
	private boolean viewable=true;
	
	/**
	 * 一对多处理
	 */
	private List<OrderItem> OrderItems = new LinkedList<OrderItem>();

}
