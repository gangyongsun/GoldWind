package com.sojson.common.model;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色权限关系模型
 * 
 * @author alvin
 *
 */
@Data
@NoArgsConstructor
public class URolePermission implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * {@link URole.id}
	 */
	private Long rid;

	/**
	 * {@link UPermission.id}
	 */
	private Long pid;

	public URolePermission(Long rid, Long pid) {
		this.rid = rid;
		this.pid = pid;
	}

}