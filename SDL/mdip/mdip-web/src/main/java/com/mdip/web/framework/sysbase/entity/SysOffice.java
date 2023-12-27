package com.mdip.web.framework.sysbase.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Description;

@Entity
@Table(name = "sys_office")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Description("单位信息")
public class SysOffice extends SysBaseEntity<SysOffice> {
	private static final long serialVersionUID = 1L;
	private String parent_name;
	private String full_name;
	private String parent_ids;
	private String name;
	private Integer sort;
	private String tree_area_id;
	private String code;
	private String type;
	private String grade;
	private String address;
	private String zip_code;
	private String master;
	private String phone;
	private String fax;
	private String email;
	private String useable;
	private String primary_person;
	private String deputy_person;

	@Length(min = 1, max = 64, message = "父级编号长度必须介于 1 和 64 之间")
	@NotNull(message = "父级编号不能为空")
	private String parent_id;

	public SysOffice() {
	}

	public SysOffice(String id) {
		this.id = id;
	}

	@Length(min = 0, max = 100, message = "父级名称长度必须介于 0 和 100 之间")
	@Description("父级名称")
	public String getParent_name() {
		return this.parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	@Length(min = 0, max = 2000, message = "全称长度必须介于 0 和 2000 之间")
	@Description("全称")
	public String getFull_name() {
		return this.full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	@Description("父级编号")
	public String getParent_id() {
		return this.parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
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

	@Length(min = 1, max = 100, message = "名称长度必须介于 1 和 100 之间")
	@NotNull(message = "名称不能为空")
	@Description("名称")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotNull(message = "排序不能为空")
	@Description("排序")
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Length(min = 1, max = 64, message = "行政区域长度必须介于 1 和 64 之间")
	@NotNull(message = "行政区域不能为空")
	@Description("行政区域")
	public String getTree_area_id() {
		return this.tree_area_id;
	}

	public void setTree_area_id(String tree_area_id) {
		this.tree_area_id = tree_area_id;
	}

	@Length(min = 0, max = 100, message = "区域编码长度必须介于 0 和 100 之间")
	@Description("区域编码")
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Length(min = 1, max = 20, message = "机构类型长度必须介于 1 和 1 之间")
	@Description("机构类型")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Length(min = 1, max = 20, message = "机构等级长度必须介于 1 和 1 之间")
	@Description("机构等级")
	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Length(min = 0, max = 255, message = "联系地址长度必须介于 0 和 255 之间")
	@Description("联系地址")
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Length(min = 0, max = 100, message = "邮政编码长度必须介于 0 和 100 之间")
	@Description("邮政编码")
	public String getZip_code() {
		return this.zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	@Length(min = 0, max = 100, message = "负责人长度必须介于 0 和 100 之间")
	@Description("负责人")
	public String getMaster() {
		return this.master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	@Length(min = 0, max = 200, message = "电话长度必须介于 0 和 200 之间")
	@Description("电话")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min = 0, max = 200, message = "传真长度必须介于 0 和 200 之间")
	@Description("传真")
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Length(min = 0, max = 200, message = "邮箱长度必须介于 0 和 200 之间")
	@Description("邮箱")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Length(min = 0, max = 64, message = "是否启用长度必须介于 0 和 64 之间")
	@Description("是否启用")
	public String getUseable() {
		return this.useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}

	@Length(min = 0, max = 64, message = "主负责人长度必须介于 0 和 64 之间")
	@Description("主负责人")
	public String getPrimary_person() {
		return this.primary_person;
	}

	public void setPrimary_person(String primary_person) {
		this.primary_person = primary_person;
	}

	@Length(min = 0, max = 64, message = "副负责人长度必须介于 0 和 64 之间")
	@Description("副负责人")
	public String getDeputy_person() {
		return this.deputy_person;
	}

	public void setDeputy_person(String deputy_person) {
		this.deputy_person = deputy_person;
	}
}
