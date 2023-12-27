package com.sojson.core.shiro.token;

import java.io.Serializable;

import org.apache.shiro.authc.UsernamePasswordToken;

import lombok.Data;

/**
 * 自定义ShiroToken,继承UsernamePasswordToken
 * 
 * @author alvin
 *
 */
@Data
public class ShiroToken extends UsernamePasswordToken implements Serializable {
	private static final long serialVersionUID = -6451794657814516274L;
	/**
	 * 登录密码[字符串类型] 因为父类是char[]
	 */
	private String pswd;

	public ShiroToken(String username, String pswd) {
		super(username, pswd);
		this.pswd = pswd;
	}

}
