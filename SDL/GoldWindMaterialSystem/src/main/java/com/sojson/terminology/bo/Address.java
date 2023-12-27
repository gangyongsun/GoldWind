package com.sojson.terminology.bo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地址信息类
 * 
 * @author alvin
 *
 */
@Data
@NoArgsConstructor
public class Address implements Serializable {

	private static final long serialVersionUID = -96552245728063146L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 收货人姓名
	 */
	private String receiver_name;

	/**
	 * 收货人固定电话
	 */
	private String receiver_phone;

	/**
	 * 收货人移动电话
	 */
	private String receiver_mobile;

	/**
	 * 省份
	 */
	private String receiver_state;

	/**
	 * 城市
	 */
	private String receiver_city;

	/**
	 * 区县
	 */
	private String receiver_district;

	/**
	 * 收货地址
	 */
	private String receiver_address;

	/**
	 * 邮政编码
	 */
	private String receiver_zip;

	/**
	 * 创建时间
	 */
	private Date create_time;

	/**
	 * 更新时间
	 */
	private Date update_time;

	/**
	 * 用户ID
	 */
	private Long user_id;

	/**
	 * 是否可见，删除=不可见
	 */
	private boolean viewable;
	
	/**
	 * 是否是默认地址
	 */
	private boolean default_address;
	
}
