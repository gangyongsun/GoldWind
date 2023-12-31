package com.sojson.core.shiro.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;

import com.sojson.common.model.UUser;
import com.sojson.common.utils.LoggerUtils;
import com.sojson.core.shiro.token.manager.TokenManager;

/**
 * 判断登录
 * 
 * @author alvin
 *
 */
public class LoginFilter extends AccessControlFilter {
	final static Class<LoginFilter> CLASS = LoginFilter.class;

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		UUser token = TokenManager.getToken();
		if (null != token || isLoginRequest(request, response)) {// && isEnabled()
			return Boolean.TRUE;
		}
		/**
		 * ajax请求
		 */
		if (ShiroFilterUtils.isAjax(request)) {
			Map<String, String> resultMap = new HashMap<String, String>();
			LoggerUtils.debug(getClass(), "当前用户没有登录，并且是Ajax请求！");
			resultMap.put("login_status", "300");
			resultMap.put("message", "当前用户没有登录");
			ShiroFilterUtils.out(response, resultMap);
		}
		return Boolean.FALSE;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		/**
		 * 保存Request和Response 到登录后的链接
		 */
		saveRequestAndRedirectToLogin(request, response);
		return Boolean.FALSE;
	}

}
