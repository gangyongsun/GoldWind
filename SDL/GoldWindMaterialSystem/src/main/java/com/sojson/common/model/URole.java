package com.sojson.common.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import lombok.Data;

/**
 * 角色实体类
 * 
 * @author alvin
 *
 */
@Data
public class URole implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	private Long id;

	/**
	 * 角色名称
	 */
	private String name;

	/**
	 * 角色类型
	 */
	private String type;

	/**
	 * 一个role对多个permission，一对多处理
	 */
	private List<UPermission> permissions = new LinkedList<UPermission>();

}