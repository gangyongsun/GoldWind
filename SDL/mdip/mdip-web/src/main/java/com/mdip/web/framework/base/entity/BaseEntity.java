package com.mdip.web.framework.base.entity;

import java.io.Serializable;

public abstract class BaseEntity<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	public BaseEntity() {
	}

	/**
	 * 删除标记（0：正常；1：删除；2：审核；）
	 */
	public static final String DEL_FLAG_NORMAL = "0";
	public static final String DEL_FLAG_DELETE = "1";
	public static final String DEL_FLAG_AUDIT = "2";
}