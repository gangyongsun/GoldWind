package com.mdip.web.framework.sysbase.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import javax.persistence.*;
import org.springframework.context.annotation.Description;

@Entity
@Table(name = "sys_user_sys_menu")
@Description(value = "用户权限表")
public class SysUserSysMenu extends SysBaseEntity<SysUserSysMenu> {

	private static final long serialVersionUID = 1L;

	private String sys_user_id; // 用户id
	private String sys_menu_id; // 功能id

	public SysUserSysMenu() {
		super();
	}

	public SysUserSysMenu(String id) {
		this.id = id;
	}

	@Length(min = 1, max = 64, message = "用户id长度必须介于 1 和 64 之间")
	@NotNull(message = "用户id不能为空")
	@Description(value = "用户id")
	public String getSys_user_id() {
		return sys_user_id;
	}

	public void setSys_user_id(String sys_user_id) {
		this.sys_user_id = sys_user_id;
	}

	@Length(min = 1, max = 64, message = "功能id长度必须介于 1 和 64 之间")
	@NotNull(message = "功能id不能为空")
	@Description(value = "功能id")
	public String getSys_menu_id() {
		return sys_menu_id;
	}

	public void setSys_menu_id(String sys_menu_id) {
		this.sys_menu_id = sys_menu_id;
	}

}