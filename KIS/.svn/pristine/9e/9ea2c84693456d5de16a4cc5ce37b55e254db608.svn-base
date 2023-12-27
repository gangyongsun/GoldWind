package cn.com.goldwind.kis.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.goldwind.kis.shiro.controller.BaseController;
import cn.com.goldwind.kis.shiro.domain.SysUser;
import cn.com.goldwind.kis.shiro.token.manager.TokenManager;
import cn.com.goldwind.kis.shiro.utils.RequestUtils;
import cn.com.goldwind.kis.utils.LoggerUtils;

@Controller
@RequestMapping(value = "/auth")
public class LoginController extends BaseController {

	/**
	 * 登录跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login() {
		return "auth/login";
	}

	/**
	 * 登录
	 * 
	 * @param user
	 * @param rememberMe
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "submitLogin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> submitLogin(SysUser user, Boolean rememberMe, HttpServletRequest request) {
		try {
			user = TokenManager.login(user, rememberMe);
			resultMap.put("status", 200);
			resultMap.put("message", "登录成功");

			// shiro 获取登录之前的地址
			SavedRequest savedRequest = WebUtils.getSavedRequest(request);
			String url = null;
			if (null != savedRequest && "admin" == user.getNickname()) {
				url = savedRequest.getRequestUrl();
				LoggerUtils.fmtDebug(getClass(), "获取登录之前的URL:[%s]", url);
			}

			// 如果登录之前没有地址，那么就跳转到首页
			if (StringUtils.isBlank(url)) {
				url = request.getContextPath() + "index";
			}
			// 跳转地址
			resultMap.put("back_url", url);
		} catch (DisabledAccountException e) {
			resultMap.put("status", 500);
			resultMap.put("message", "帐号已经禁用!");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "帐号或密码错误!");
		}
		return resultMap;
	}

	/**
	 * 登录成功后执行的方法，由submitLogin中返回redirect:index重定向到此方法
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String loginSuccessMessage(HttpServletRequest request) {
		String username = "未登录";
		SysUser currentLoginUser = RequestUtils.currentLoginUser();

		if (currentLoginUser != null && StringUtils.isNotEmpty(currentLoginUser.getUserName())) {
			username = currentLoginUser.getUserName();
		} else {
			return "auth/login";
		}
		request.setAttribute("username", username);
		return "index";
	}

	/**
	 * 退出
	 * 
	 * @return
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		try {
			TokenManager.logout();
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "退出出现错误，%s", e.getMessage());
		}
		System.out.println("kickout");
		return "auth/kickout";
	}

	/**
	 * 被踢出后跳转的页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/kickout", method = RequestMethod.GET)
	public String kickOut() {
		return "auth/kickout";
	}

}