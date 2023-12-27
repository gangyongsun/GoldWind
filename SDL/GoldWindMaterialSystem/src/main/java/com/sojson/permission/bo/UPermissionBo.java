package com.sojson.permission.bo;

import java.io.Serializable;

import com.sojson.common.model.UPermission;
import com.sojson.common.utils.StringUtils;

import lombok.Data;

/**
 * 权限选择
 * 
 * @author alvin
 *
 */
@Data
public class UPermissionBo extends UPermission implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 是否勾选
	 */
	private String marker;

	/**
	 * role Id
	 */
	private String roleId;

	public boolean isCheck() {
		return StringUtils.equals(roleId, marker);
	}

}
