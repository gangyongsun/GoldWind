package com.sojson.permission.bo;

import java.io.Serializable;

import com.sojson.common.model.URole;
import com.sojson.common.utils.StringUtils;

import lombok.Data;

@Data
public class URoleBo extends URole implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID (用String， 考虑多个ID，现在只有一个ID)
	 */
	private String userId;

	/**
	 * 是否勾选
	 */
	private String marker;

	public boolean isCheck() {
		return StringUtils.equals(userId, marker);
	}

}
