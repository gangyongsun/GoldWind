package com.mdip.web.framework.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import com.mdip.web.framework.base.util.StrUtil;
import com.mdip.web.framework.sysbase.entity.SysUser;
import com.mdip.web.framework.util.UserUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 表单验证（包含验证码）过滤类
 * 
 * @version 2017-2-9
 */
@Service
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	public static final String DEFAULT_MOBILE_PARAM = "deviceType";
	public static final String DEFAULT_MESSAGE_PARAM = "message";
	public static final String DEFAULT_MESSAGE_STATUS = "status";
	public static final String REQUEST_URL = "saveUrl";// 登录成功之后去哪

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
	private String messageParam = DEFAULT_MESSAGE_PARAM;

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		if (password == null) {
			password = "";
		}
		boolean rememberMe = isRememberMe(request);
		String host = StrUtil.getRemoteAddr((HttpServletRequest) request);
		String captcha = getCaptcha(request);// 验证码
		String deviceType = getDeviceType(request);// 登陆方式
		return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha, deviceType);
	}

	public String getCaptchaParam() {
		return captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	public String getMobileLoginParam() {
		return mobileLoginParam;
	}

	protected String getDeviceType(ServletRequest request) {
		return WebUtils.getCleanParam(request, getMobileLoginParam());
	}

	public String getMessageParam() {
		return messageParam;
	}

	/**
	 * 登录成功之后跳转URL
	 */
	public String getSuccessUrl() {
		return super.getSuccessUrl();
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest req = (HttpServletRequest) request;
		// System.out.println(req.getHeader("user-agent"));
		Principal principal = (Principal) subject.getPrincipal();
		SysUser user = UserUtil.getByLoginName(principal.getLoginName());
		user.setErrorcount(0);
		UserUtil.updateUser(user);
		// String requestUrl="";
		// SavedRequest savedRequest =
		// WebUtils.getAndClearSavedRequest(request);
		// if (savedRequest != null &&
		// savedRequest.getMethod().equalsIgnoreCase(AccessControlFilter.GET_METHOD))
		// {
		// requestUrl = savedRequest.getRequestUrl();
		// System.out.println(requestUrl);
		// }
		// UserUtil.putCache(REQUEST_URL, requestUrl);
		WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
		// we handled the success redirect directly, prevent the chain from
		// continuing:
		return false;
	}
	// @Override
	// protected void issueSuccessRedirect(ServletRequest request,
	// ServletResponse response) throws Exception {
	// super.issueSuccessRedirect(request, response);
	//// Principal p = UserUtils.getPrincipal();
	//// if (p != null && !p.isMobileLogin()){
	//// WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
	//// }else{
	//// super.issueSuccessRedirect(request, response);
	//// }
	// }

	/**
	 * 登录失败调用事件
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
		String className = e.getClass().getName(), message = "";
		if (IncorrectCredentialsException.class.getName().equals(className) || UnknownAccountException.class.getName().equals(className)) {
			message = "用户或密码错误！";// 用户或密码错误, 请重试.
			// case 235:codemsg="用户名或密码错误";break;
			request.setAttribute(DEFAULT_MESSAGE_STATUS, 235);
		} else if (e.getMessage() != null) {
			message = e.getMessage();
			// 权限校验失败
			request.setAttribute(DEFAULT_MESSAGE_STATUS, 3);
		} else {
			message = "系统出现点问题，请稍后再试！";// 系统出现点问题，请稍后再试！
			// 服务器异常
			request.setAttribute(DEFAULT_MESSAGE_STATUS, 1);
			e.printStackTrace(); // 输出到控制台
		}
		String deviceType = getDeviceType(request);// 登陆方式
		request.setAttribute(mobileLoginParam, deviceType);
		request.setAttribute(getFailureKeyAttribute(), className);
		request.setAttribute(getMessageParam(), message);
		System.out.println("loginFail");		
		return true;
	}

}