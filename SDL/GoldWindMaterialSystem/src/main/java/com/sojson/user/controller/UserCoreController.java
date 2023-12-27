package com.sojson.user.controller;

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sojson.common.controller.BaseController;
import com.sojson.common.model.UUser;
import com.sojson.common.utils.LoggerUtils;
import com.sojson.common.utils.PropertiesUtil;
import com.sojson.common.utils.StringUtils;
import com.sojson.core.shiro.token.manager.TokenManager;
import com.sojson.permission.service.RoleService;
import com.sojson.user.manager.UserManager;
import com.sojson.user.service.UUserService;

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
public class UserCoreController extends BaseController {

	@Resource
	public UUserService userService;
	
	@Autowired
	public RoleService roleService;
	
	/**
	 * 个人资料
	 * 
	 * @return
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public ModelAndView userIndex() {
		UUser token = TokenManager.getToken();
		return new ModelAndView("user/index", "token", token);
	}

	/**
	 * 通用页面跳转
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "{page}", method = RequestMethod.GET)
	public ModelAndView toPage(@PathVariable("page") String page) {
		return new ModelAndView(String.format("user/%s", page), "token", TokenManager.getToken());
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
		String email = TokenManager.getToken().getEmail();
		pswd = UserManager.md5Pswd(email, pswd);
		UUser user = userService.login(email, pswd);
		
		Set<String> roleSet = roleService.findRoleByUserId(TokenManager.getUserId());
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
			user.setPswd(newPswd);
			user = UserManager.md5Pswd(user);
			userService.updateByPrimaryKeySelective(user);
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
	public Map<String, Object> updateSelf(UUser entity, HttpServletRequest request) {
		try {
			userService.updateByPrimaryKeySelective(entity);
			resultMap.put("status", 200);
			resultMap.put("message", "修改成功!");
			//更新token的Nickname
			TokenManager.setUser(entity);
			//跳转到首页
			String url = null;
			if (StringUtils.isBlank(url)) {
				url = request.getContextPath() + "/user/index.shtml";
			}
			resultMap.put("back_url", url);
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "修改失败!");
			LoggerUtils.fmtError(getClass(), e, "修改个人资料出错![%s]", JSONObject.fromObject(entity).toString());
		}
		return resultMap;
	}
	
}
