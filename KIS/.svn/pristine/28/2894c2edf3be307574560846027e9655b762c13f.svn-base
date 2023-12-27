package cn.com.goldwind.kis.shiro.controller;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.goldwind.kis.shiro.domain.SysUser;
import cn.com.goldwind.kis.shiro.service.RoleService;
import cn.com.goldwind.kis.shiro.service.UserService;
import cn.com.goldwind.kis.shiro.token.manager.TokenManager;
import cn.com.goldwind.kis.shiro.utils.UserManager;
import cn.com.goldwind.kis.utils.LoggerUtils;
import cn.com.goldwind.kis.utils.PropertiesUtil;
import cn.com.goldwind.kis.utils.StringUtils;
import net.sf.json.JSONObject;

/**
 * 用户管理
 * 
 * @author alvin
 *
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("user")
public class UserController extends BaseController {

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;
	
	/**
	 * 个人资料
	 * 
	 * @return
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public ModelAndView userIndex() {
		SysUser token = TokenManager.getToken();
		return new ModelAndView("system/user/index", "token", token);
	}

	/**
	 * 通用页面跳转
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "{page}", method = RequestMethod.GET)
	public ModelAndView toPage(@PathVariable("page") String page) {
		SysUser token = TokenManager.getToken();
		return new ModelAndView(String.format("system/%s", page), "token", token);
	}

	/**
	 * 密码修改
	 * 
	 * @return
	 */
	@RequestMapping(value = "updatePswd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePswd(String pswd, String newPswd) {
		/**
		 * 根据当前登录的用户帐号 + 老密码，查询
		 */
		String username = TokenManager.getToken().getUserName();
		pswd = UserManager.md5Password(username, pswd);
		SysUser user = userService.login(username, pswd);

		Set<String> roleSet = roleService.findRoleNameByUserId(TokenManager.getUserId());
		String admin_role_type = PropertiesUtil.getValueByKey("ADMIN_ROLE_TYPE", "config.properties");
		if (null != roleSet && roleSet.contains(admin_role_type)) {
			resultMap.put("status", 300);
			resultMap.put("message", "管理员不准修改密码！");
			return resultMap;
		}

		if (null == user) {
			resultMap.put("status", 300);
			resultMap.put("message", "密码不正确！");
		} else {
			user.setPassWord(newPswd);
			user = UserManager.md5Pswd(user);
			userService.updateUser(user);
			resultMap.put("status", 200);
			resultMap.put("message", "修改成功!");
			/**
			 * 重新登录一次
			 */
			TokenManager.login(user, Boolean.TRUE);
		}
		return resultMap;
	}

	/**
	 * 个人资料修改
	 * 
	 * @return
	 */
	@RequestMapping(value = "updateSelf", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSelf(SysUser sysUser, HttpServletRequest request) {
		try {
			userService.updateUser(sysUser);
			resultMap.put("status", 200);
			resultMap.put("message", "修改成功!");
			// 更新token的Nickname
			TokenManager.setUser(sysUser);
			// 跳转到首页
			String url = null;
			if (StringUtils.isBlank(url)) {
				url = request.getContextPath() + "system/user/index";
			}
			resultMap.put("back_url", url);
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "修改失败!");
			LoggerUtils.fmtError(getClass(), e, "修改个人资料出错![%s]", JSONObject.fromObject(sysUser).toString());
		}
		return resultMap;
	}

}
