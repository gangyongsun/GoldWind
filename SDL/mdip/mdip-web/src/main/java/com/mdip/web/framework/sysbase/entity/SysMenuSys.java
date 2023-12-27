package com.mdip.web.framework.sysbase.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "sys_menu")
public class SysMenuSys extends SysBaseEntity<SysMenuSys> {
	private static final long serialVersionUID = 1L;
	private String name;
	private String parent_name;
	private String full_name;
	private String parent_ids;
	private String sort;
	private String href;
	private String target;
	private String iconimg;
	private String is_show;
	private String permission;

	@Length(min = 1, max = 64, message = "父级编号长度必须介于 1 和 64 之间")
	@NotNull(message = "父级编号不能为空")
	private String parent_id;

	@OneToMany(cascade = { javax.persistence.CascadeType.ALL }, fetch = FetchType.EAGER, mappedBy = "parent_id")
	@Where(clause = "is_show='1' and del_flag='0'")
	@OrderBy("sort asc")
	private List<SysMenu> children = new ArrayList();

	public SysMenuSys() {
	}

	public SysMenuSys(String id) {
		this.id = id;
	}

	@Length(min = 1, max = 100, message = "名称长度必须介于 1 和 100 之间")
	@NotNull(message = "名称不能为空")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 0, max = 100, message = "parent_name长度必须介于 0 和 100 之间")
	public String getParent_name() {
		return this.parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	@Length(min = 0, max = 2000, message = "full_name长度必须介于 0 和 2000 之间")
	public String getFull_name() {
		return this.full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getParent_id() {
		return this.parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public List<SysMenu> getChildren() {
		return this.children;
	}

	public void setChildren(List<SysMenu> children) {
		this.children = children;
	}

	@Length(min = 1, max = 2000, message = "所有父级编号长度必须介于 1 和 2000 之间")
	@NotNull(message = "所有父级编号不能为空")
	public String getParent_ids() {
		return this.parent_ids;
	}

	public void setParent_ids(String parent_ids) {
		this.parent_ids = parent_ids;
	}

	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Length(min = 0, max = 2000, message = "链接长度必须介于 0 和 2000 之间")
	public String getHref() {
		return this.href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Length(min = 0, max = 20, message = "目标长度必须介于 0 和 20 之间")
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
	public String getIs_show() {
		return this.is_show;
	}

	public void setIs_show(String is_show) {
		this.is_show = is_show;
	}

	@Length(min = 0, max = 200, message = "权限标识长度必须介于 0 和 200 之间")
	public String getPermission() {
		return this.permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
}
