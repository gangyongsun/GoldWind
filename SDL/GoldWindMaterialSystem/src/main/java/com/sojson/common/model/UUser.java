package com.sojson.common.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体类
 * 
 * @author alvin
 *
 */
@Data
@NoArgsConstructor
public class UUser implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 0:禁止登录
	 */
	public static final Long LOGINDENY = new Long(0);

	/**
	 * 1:有效
	 */
	public static final Long LOGINACCESS = new Long(1);

	/**
	 * 用户ID
	 */
	private Long id;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 邮箱 | 登录帐号
	 */
	private String email;

	/**
	 * 密码
	 */
	private transient String pswd;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;

	/**
	 * 1:有效，0:禁止登录
	 */
	private Long status;

	public UUser(UUser user) {
		this.id = user.getId();
		this.nickname = user.getNickname();
		this.email = user.getEmail();
		this.pswd = user.getPswd();
		this.createTime = user.getCreateTime();
		this.lastLoginTime = user.getLastLoginTime();
	}

}