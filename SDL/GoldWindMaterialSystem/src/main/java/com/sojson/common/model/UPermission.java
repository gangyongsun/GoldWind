package com.sojson.common.model;

import java.io.Serializable;

import lombok.Data;

/**
 * 权限实体类
 * 
 * @author alvin
 *
 */
@Data
public class UPermission implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 权限ID
	 */
	private Long id;

	/**
	 * 操作的url
	 */
	private String url;

	/**
	 * 操作的名称
	 */
	private String name;

}