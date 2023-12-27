package com.mdip.web.framework.sysbase.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Description;

@Entity
@Table(name = "sys_dict")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Description("字典信息")
public class SysDict extends SysBaseEntity<SysDict> {
	private static final long serialVersionUID = 1L;
	private String value;
	private String label;
	private String type;
	private String description;
	private String sort;

	@Length(min = 0, max = 64, message = "父级编号长度必须介于 0 和 64 之间")
	private String parent_id;

	public SysDict() {
	}

	public SysDict(String id) {
		this.id = id;
	}

	@Length(min = 1, max = 100, message = "数据值长度必须介于 1 和 100 之间")
	@NotNull(message = "数据值不能为空")
	@Description("数据值")
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Length(min = 1, max = 100, message = "标签名长度必须介于 1 和 100 之间")
	@NotNull(message = "标签名不能为空")
	@Description("标签名")
	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Length(min = 1, max = 100, message = "类型长度必须介于 1 和 100 之间")
	@NotNull(message = "类型不能为空")
	@Description("类型")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Length(min = 1, max = 100, message = "描述长度必须介于 1 和 100 之间")
	@NotNull(message = "描述不能为空")
	@Description("描述")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Description("排序（升序）")
	public String getSort() {
		return this.sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Description("父级编号")
	public String getParent_id() {
		return this.parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
}
