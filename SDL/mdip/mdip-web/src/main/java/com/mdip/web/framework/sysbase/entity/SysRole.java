package com.mdip.web.framework.sysbase.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Description;

@Entity
@Table(name = "sys_role")
@Description("角色信息")
public class SysRole extends SysBaseEntity<SysRole> {
	private static final long serialVersionUID = 1L;
	private String name;
	private String role_type;
	private String sys_office_id;
	private String data_scope;
	private String use_able;

	public SysRole() {
	}

	public SysRole(String id) {
		this.id = id;
	}

	@Length(min = 1, max = 64, message = "角色名称长度必须介于 1 和 64 之间")
	@NotNull(message = "角色名称不能为空")
	@Description("角色名称")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 0, max = 64, message = "权限类型长度必须介于 0 和 64 之间")
	@Description("权限类型")
	public String getRole_type() {
		return this.role_type;
	}

	public void setRole_type(String role_type) {
		this.role_type = role_type;
	}

	@Length(min = 0, max = 64, message = "归属机构长度必须介于 0 和 64 之间")
	@Description("归属机构")
	public String getSys_office_id() {
		return this.sys_office_id;
	}

	public void setSys_office_id(String sys_office_id) {
		this.sys_office_id = sys_office_id;
	}

	@Length(min = 0, max = 100, message = "数据范围围长度必须介于 0 和 100 之间")
	@Description("数据范围数据范围")
	public String getData_scope() {
		return this.data_scope;
	}

	public void setData_scope(String data_scope) {
		this.data_scope = data_scope;
	}

	@Length(min = 0, max = 20, message = "是否是可用长度必须介于 0 和 20 之间")
	@Description("是否是可用")
	public String getUse_able() {
		return this.use_able;
	}

	public void setUse_able(String use_able) {
		this.use_able = use_able;
	}
}