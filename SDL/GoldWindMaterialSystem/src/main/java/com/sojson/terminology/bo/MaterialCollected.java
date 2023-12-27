package com.sojson.terminology.bo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户收藏的物资信息
 * 
 * @author alvin
 *
 */
@Data
@NoArgsConstructor
public class MaterialCollected implements Serializable {
	private static final long serialVersionUID = -4303983706317256955L;

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 用户ID
	 */
	private Long uid;
	
	/**
	 * 物资ID
	 */
	private String material_id;

	/**
	 * 物资名称
	 */
	private String material_name;
	
	/**
	 * 收藏时间
	 */
	private Date collected_time;
	
	/**
	 * 是否有效
	 */
	private boolean status = Boolean.TRUE;
}
