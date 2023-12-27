package com.mdip.web.framework.sysbase.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Description;

@Entity
@Table(name = "sys_menu")
@Description("角色信息")
public class SysMenu extends SysBaseEntity<SysMenu> {
	private static final long serialVersionUID = 1L;
	private String parent_id;
	private String parent_ids;
	private String full_name;
	private String parent_name;
	private String name;
	private String sort;
	private String href;
	private String target;
	private String iconimg;
	private String is_show;
	private String permission;

	@Length(min = 1, max = 64, message = "父级编号长度必须介于 1 和 64 之间")
	@NotNull(message = "父级编号不能为空")
	@Description("父级编号")
	public String getParent_id() {
		return this.parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public SysMenu() {
	}

	public SysMenu(String id) {
		this.id = id;
	}

	@Length(min = 1, max = 2000, message = "所有父级编号长度必须介于 1 和 2000 之间")
	@NotNull(message = "所有父级编号不能为空")
	@Description("所有父级编号")
	public String getParent_ids() {
		return this.parent_ids;
	}

	public void setParent_ids(String parent_ids) {
		this.parent_ids = parent_ids;
	}

	@Length(min = 0, max = 2000, message = "全称长度必须介于 0 和 2000 之间")
	@Description("全称")
	public String getFull_name() {
		return this.full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	@Length(min = 0, max = 255, message = "父名称长度必须介于 0 和 255 之间")
	@Description("父名称")
	public String getParent_name() {
		return this.parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	@Length(min = 1, max = 100, message = "名称长度必须介于 1 和 100 之间")
	@NotNull(message = "名称不能为空")
	@Description("名称")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Description("排序")
	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Length(min = 0, max = 2000, message = "链接长度必须介于 0 和 2000 之间")
	@Description("链接")
	public String getHref() {
		return this.href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Length(min = 0, max = 20, message = "目标长度必须介于 0 和 20 之间")
	@Description("目标")
	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getIconimg() {
		return this.iconimg;
	}

	public void setIconimg(String iconimg) {
		this.iconimg = iconimg;
	}

	@Length(min = 1, max = 1, message = "是否在菜单中显示长度必须介于 1 和 1 之间")
	@NotNull(message = "是否在菜单中显示不能为空")
	@Description("是否在菜单中显示")
	public String getIs_show() {
		return this.is_show;
	}

	public void setIs_show(String is_show) {
		this.is_show = is_show;
	}

	@Length(min = 0, max = 200, message = "权限标识长度必须介于 0 和 200 之间")
	@Description("权限标识")
	public String getPermission() {
		return this.permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
}