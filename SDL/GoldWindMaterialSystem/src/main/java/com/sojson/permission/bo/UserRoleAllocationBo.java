package com.sojson.permission.bo;

import java.io.Serializable;

import com.sojson.common.model.UUser;

import lombok.Data;

/**
 * 用户角色分配 查询列表BO
 * 
 * @author alvin
 *
 */
@Data
public class UserRoleAllocationBo extends UUser implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Role Name列转行，以,分割
	 */
	private String roleNames;

	/**
	 * Role Id列转行，以‘,’分割
	 */
	private String roleIds;

}
