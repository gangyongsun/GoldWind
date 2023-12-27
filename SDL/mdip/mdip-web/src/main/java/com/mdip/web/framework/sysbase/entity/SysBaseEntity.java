package com.mdip.web.framework.sysbase.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mdip.common.util.DateUtil;
import com.mdip.web.framework.base.entity.BaseEntity;
import com.mdip.web.framework.util.UserUtil;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Description;

@MappedSuperclass
public abstract class SysBaseEntity<T> extends BaseEntity<T> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id", unique = true, nullable = false, length = 64)
	protected String id;
	private String create_by;
	private String create_date = DateUtil.getDateTime();
	private String update_by;
	private String update_date = DateUtil.getDateTime();
	private String del_flag = "0";
	private String remarks;

	@Description("基础信息id")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Description("备注")
	public String getRemarks() {
		return this.remarks;
	}

	@PrePersist
	protected void onCreate() {
		this.create_date = DateUtil.getDateTime();
		this.update_date = DateUtil.getDateTime();

		String currentUser = UserUtil.getCurrentUser();
		if ((currentUser == null) || ("".equals(currentUser))) {
			currentUser = "guest";
		}
		this.create_by = currentUser;
		this.update_by = currentUser;
	}

	@PreUpdate
	protected void onUpdate() {
		this.update_date = DateUtil.getDateTime();
		this.update_by = UserUtil.getCurrentUser();
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Length(min = 1, max = 64, message = "创建者长度必须介于 1 和 64 之间")
	@Description("创建者")
	public String getCreate_by() {
		return this.create_by;
	}

	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Description("创建时间")
	public String getCreate_date() {
		if ((this.create_date == null) || ("".equals(this.create_date))) {
			return "0000-00-00 00:00:00";
		}
		return this.create_date;
	}

	public void setCreate_date(String create_date) {
		if ((create_date == null) || ("".equals(create_date)))
			this.create_date = "0000-00-00 00:00:00";
		else
			this.create_date = create_date;
	}

	@Length(min = 1, max = 64, message = "更新者长度必须介于 1 和 64 之间")
	@Description("更新者")
	public String getUpdate_by() {
		return this.update_by;
	}

	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Description("更新时间")
	public String getUpdate_date() {
		if ((this.update_date == null) || ("".equals(this.update_date))) {
			return "0000-00-00 00:00:00";
		}
		return this.update_date;
	}

	public void setUpdate_date(String update_date) {
		if ((update_date == null) || ("".equals(update_date)))
			this.update_date = "0000-00-00 00:00:00";
		else
			this.update_date = update_date;
	}

	@Length(min = 1, max = 1, message = "删除标记长度必须介于 1 和 1 之间")
	@Description("删除标识")
	public String getDel_flag() {
		return this.del_flag;
	}

	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (!(super.getClass().equals(obj.getClass()))) {
			return false;
		}
		SysBaseEntity that = (SysBaseEntity) obj;
		return ((getId() == null) ? false : getId().equals(that.getId()));
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public int hashCode() {
		if (getId() != null) {
			return getId().hashCode();
		}
		return super.hashCode();
	}
}
