package com.mdip.web.framework.shiro;

import java.io.Serializable;
import com.mdip.web.framework.sysbase.entity.SysUser;
import com.mdip.web.framework.util.UserUtil;

public class Principal implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String loginName;
	private String name;
	private String deviceType;

	public Principal(SysUser user, String deviceType) {
		this.id = user.getId();
		this.loginName = user.getLogin_name();
		this.name = user.getName();
		this.deviceType = deviceType;
	}

	public String getSessionid() {
		try {
			return ((String) UserUtil.getSession().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getId() {
		return this.id;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public String getName() {
		return this.name;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	public String toString() {
		return this.id;
	}
}