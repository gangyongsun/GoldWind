package com.sojson.permission.bo;

import java.io.Serializable;

import lombok.Data;

/**
 * 权限分配 查询列表BO
 * 
 * @author alvin
 *
 */
@Data
public class RolePermissionAllocationBo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 角色ID
	 */
	private Long id;
	
	/**
	 * 角色type
	 */
	private String type;
	
	/**
	 * 角色Name
	 */
	private String name;
	
	/**
	 * 权限Name列转行，以,分割
	 */
	private String permissionNames;
	
	/**
	 * 权限Id列转行，以‘,’分割
	 */
	private String permissionIds;
	
}
