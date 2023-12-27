package com.mdip.web.framework.sysbase.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Description;

@Entity
@Table(name = "sys_user_sys_role")
@Description(value = "用户角色信息")
public class SysUserSysRole extends SysBaseEntity<SysUserSysRole> {

	private static final long serialVersionUID = 1L;
	/*
	 * //start 基础字段统一使用
	 * 
	 * @Id @GeneratedValue(generator="system-uuid")
	 * 
	 * @GenericGenerator(name="system-uuid",strategy = "uuid")
	 * 
	 * @Column(name = "id", unique = true, nullable = false, length = 64)
	 * private String id;
	 * 
	 * @Description(value="用户角色id") public String getId() { return id; } public
	 * void setId(String id) { this.id = id; }
	 */

	private String sys_user_id; // 用户名称
	private String sys_role_id; // 角色名称

	public SysUserSysRole() {
		super();
	}

	public SysUserSysRole(String id) {
		this.id = id;
	}

	@Length(min = 1, max = 64, message = "用户名称长度必须介于 1 和 64 之间")
	@NotNull(message = "用户名称不能为空")
	@Description(value = "用户名称")
	public String getSys_user_id() {
		return sys_user_id;
	}

	public void setSys_user_id(String sys_user_id) {
		this.sys_user_id = sys_user_id;
	}

	@Length(min = 1, max = 64, message = "角色名称长度必须介于 1 和 64 之间")
	@NotNull(message = "角色名称不能为空")
	@Description(value = "角色名称")
	public String getSys_role_id() {
		return sys_role_id;
	}

	public void setSys_role_id(String sys_role_id) {
		this.sys_role_id = sys_role_id;
	}
}
