package com.mdip.web.framework.shiro;

public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	private String captcha;// 验证码
	private String deviceType;// 登录方式

	public UsernamePasswordToken() {
		super();
	}

	public UsernamePasswordToken(String username, char[] password, boolean rememberMe, String host, String captcha, String deviceType) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
		this.deviceType = deviceType;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getDeviceType() {
		return deviceType;
	}

}
