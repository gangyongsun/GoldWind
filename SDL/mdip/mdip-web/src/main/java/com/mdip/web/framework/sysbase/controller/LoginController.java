package com.mdip.web.framework.sysbase.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mdip.web.framework.base.controller.BaseController;
import com.mdip.web.framework.base.entity.ResultEntity;
import com.mdip.web.framework.base.util.CookieUtils;
import com.mdip.web.framework.base.util.StrUtil;
import com.mdip.web.framework.config.Config;
import com.mdip.web.framework.shiro.FormAuthenticationFilter;
import com.mdip.web.framework.shiro.Principal;
import com.mdip.web.framework.sysbase.entity.SysUser;
import com.mdip.web.framework.sysbase.service.ISysMenuService;
import com.mdip.web.framework.sysbase.service.ISysUserService;
import com.mdip.web.framework.util.UserUtil;

@Controller
public class LoginController extends BaseController {
	@Autowired
	private ISysMenuService sysMenuService;
	@Autowired
	private ISysUserService sysUserService;

	/**
	 * 跳转来自get请求
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtil.getPrincipal();
		ResultEntity info=new ResultEntity();
		// 如果已登录，再次访问主页，则退出原账号。
		if (Config.TRUE.equals(Config.usermultiAccountLogin)) {
			CookieUtils.setCookie(response, "LOGINED", "false");
		}
		info.failed("用户名或密码错误");
		info.add("username", "test");
		// 如果已经登录，则跳转到管理首页
		if (principal != null) {
			info.add("username", principal.getLoginName());
			info.add("sn", principal.getSessionid());
			info.success("登录成功");
			UserUtil.removeCache(FormAuthenticationFilter.REQUEST_URL);
		}
		model.addAttribute("resultEntity",info);
		return "login";
	}

	@RequiresPermissions("user")
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtil.getPrincipal();
		ResultEntity info=new ResultEntity();
		// 得到用户的权限菜单,并返回....
		try {
			// info.add("meunlist",
			// this.sysMenuService.getMenuByUserId(principal.getId()));
			info.add("meunlist", this.sysMenuService.getMenuByUserNo(principal.getId(), "root", "1"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			info.setStatus(1);
		} // 根据用户ID得到用户权限菜单
		model.addAttribute("resultEntity",info);
		return "index"; // 还是直接返回ligon
	}

	@RequiresPermissions("user")
	@RequestMapping(value = "/loginsuc")
	public String loginsuc(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Principal principal = UserUtil.getPrincipal();
		ResultEntity info=new ResultEntity();
		// 登录成功
		info.add("username", principal.getLoginName());
		info.add("sn", principal.getSessionid());
		info.success("登录成功");
		model.addAttribute("resultEntity",info);
		return "redirect:/index";
	}

	/**
	 * 登录失败，真正登录的POST请求由Filter完成 ，统一处理PC,手机,APP等登录失败问题。自动根据后缀返回json，xml等 一套代码打天下
	 * ，那里来回那里去，控制层不处理路由
	 */
	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtil.getPrincipal();
		ResultEntity info=new ResultEntity();
		// 如果已经登录，则跳转到管理首页
		if (principal != null) {
			return "redirect:" + "index";
		}
		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		String deviceType = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
		String exception = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
		int status = (int) request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_STATUS);
		// info.setStatus(3);//权限不失败
		info.add(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		info.add(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
		info.add(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, deviceType);
		info.add(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		info.add(FormAuthenticationFilter.DEFAULT_MESSAGE_STATUS, status);
		// info.add(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
		if (StrUtil.isBlank(message) || StrUtil.equals(message, "null")) {
			info.failed("用户或密码错误, 请重试");
		} else {
			if ("validateCodeErro".equals(message)) {
				info.failed("验证码错误");
			} else {
				info.failed(message);
			}
		}
		/**
		 * 连续登陆失败账户锁死
		 */
		SysUser user = UserUtil.getByLoginName(username);
		if (user != null && "0".equals(user.getLogin_flag())) {
			int count = user.getErrorcount() + 1;
			user.setErrorcount(count);
			if (count == 5) {
				user.setLogin_flag("0");
			}
			info.add("chance", 5 - count);
			UserUtil.updateUser(user);
		}
		model.addAttribute("resultEntity",info);
		return "login";
	}

}
