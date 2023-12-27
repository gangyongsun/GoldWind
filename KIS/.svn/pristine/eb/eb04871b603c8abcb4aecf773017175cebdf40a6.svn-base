package cn.com.goldwind.kis.shiro.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Table(name = "sys_user")
@Data
@NoArgsConstructor
public class SysUser implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 0:禁止登录
	 */
	public static final Integer LOGINDENY = new Integer(0);

	/**
	 * 1:有效
	 */
	public static final Integer LOGINACCESS = new Integer(1);

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
	private Integer id;

	/**
	 * 登录名
	 */
	@Column(name = "user_name")
	private String userName;

	/**
	 * 登录密码
	 */
	@Column(name = "pass_word")
	private String passWord;

	/**
	 * 用户昵称
	 */
	@Column(name = "nick_name")
	private String nickname;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date createTime;

	/**
	 * 最后登录时间
	 */
	@Column(name = "lastLogin_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date lastLoginTime;

	/**
	 * 是否启用
	 */
	@Column(name = "user_enable")
	private Integer userEnable;

	public SysUser(SysUser user) {
		this.userName = user.userName;
		this.passWord = user.passWord;
		this.nickname = user.nickname;
		this.createTime = user.createTime;
		this.lastLoginTime = user.lastLoginTime;
		this.userEnable = user.userEnable;
	}

}