package com.mdip.web.framework.sysbase.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.context.annotation.Description;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "sys_user")
@Description("用户信息")
public class SysUser extends SysBaseEntity<SysUser> {
	private static final long serialVersionUID = 1L;

	private String sys_office_type;
	private String sys_office_id;
	private String login_name;
	private String password;
	private String no;
	private String name;
	private String email;
	private String phone;
	private String mobile;
	private String user_duty;
	private String user_type;
	private String userphoto;
	private String login_ip;
	private String login_date = "0000-00-00 00:00:00";
	private String accredit_flag;
	private String login_flag;
	private Integer errorcount = Integer.valueOf(0);

	public SysUser() {
	}

	public SysUser(String id) {
		this.id = id;
	}

	@Description("登陆失败次数")
	public Integer getErrorcount() {
		return this.errorcount;
	}

	public void setErrorcount(Integer errorcount) {
		this.errorcount = errorcount;
	}

	@Length(min = 1, max = 64, message = "归属部门长度必须介于 1 和 64 之间")
	@NotNull(message = "归属部门不能为空")
	@Description("归属部门")
	public String getSys_office_id() {
		return this.sys_office_id;
	}

	public void setSys_office_id(String sys_office_id) {
		this.sys_office_id = sys_office_id;
	}

	@Length(min = 1, max = 100, message = "登录名长度必须介于 1 和 100 之间")
	@NotNull(message = "登录名不能为空")
	@Description("登录名")
	public String getLogin_name() {
		return this.login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	@Length(min = 1, max = 100, message = "密码长度必须介于 1 和 256 之间")
	@NotNull(message = "密码不能为空")
	@Description("密码")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Length(min = 0, max = 100, message = "工号长度必须介于 0 和 100 之间")
	@Description("工号")
	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Length(min = 1, max = 100, message = "姓名长度必须介于 1 和 100 之间")
	@NotNull(message = "姓名不能为空")
	@Description("姓名")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min = 0, max = 200, message = "邮箱长度必须介于 0 和 200 之间")
	@Description("邮箱")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Length(min = 0, max = 200, message = "电话长度必须介于 0 和 200 之间")
	@Description("电话")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min = 0, max = 200, message = "手机长度必须介于 0 和 200 之间")
	@Description("手机")
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Length(min = 0, max = 200, message = "用户类型长度必须介于 0 和 200 之间")
	@Description("用户类型")
	public String getUser_type() {
		return this.user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	@Length(min = 0, max = 100, message = "最后登陆IP长度必须介于 0 和 100 之间")
	@Description("最后登陆IP")
	public String getLogin_ip() {
		return this.login_ip;
	}

	public void setLogin_ip(String login_ip) {
		this.login_ip = login_ip;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Description("最后登陆时间")
	public String getLogin_date() {
		if ((this.login_date == null) || ("".equals(this.login_date))) {
			return "0000-00-00 00:00:00";
		}
		return this.login_date;
	}

	public void setLogin_date(String login_date) {
		if ((login_date == null) || ("".equals(login_date)))
			this.login_date = "0000-00-00 00:00:00";
		else
			this.login_date = login_date;
	}

	@Length(min = 0, max = 64, message = "是否可登录长度必须介于 0 和 64 之间")
	@Description("是否可登录")
	public String getLogin_flag() {
		return this.login_flag;
	}

	public void setLogin_flag(String login_flag) {
		this.login_flag = login_flag;
	}

	public String getSys_office_type() {
		return sys_office_type;
	}

	public void setSys_office_type(String sys_office_type) {
		this.sys_office_type = sys_office_type;
	}

	public String getUser_duty() {
		return user_duty;
	}

	public void setUser_duty(String user_duty) {
		this.user_duty = user_duty;
	}

	public String getUserphoto() {
		return userphoto;
	}

	public void setUserphoto(String userphoto) {
		this.userphoto = userphoto;
	}

	public String getAccredit_flag() {
		return accredit_flag;
	}

	public void setAccredit_flag(String accredit_flag) {
		this.accredit_flag = accredit_flag;
	}

}
