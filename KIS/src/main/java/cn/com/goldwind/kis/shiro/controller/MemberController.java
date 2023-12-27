package cn.com.goldwind.kis.shiro.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.shiro.bo.UserOnlineBo;
import cn.com.goldwind.kis.shiro.domain.SysUser;
import cn.com.goldwind.kis.shiro.service.RoleService;
import cn.com.goldwind.kis.shiro.service.UserService;
import cn.com.goldwind.kis.shiro.session.SessionService;
import cn.com.goldwind.kis.shiro.utils.UserManager;
import cn.com.goldwind.kis.utils.PropertiesUtil;

/**
 * 用户会员管理
 * 
 * @author alvin
 *
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("member")
public class MemberController extends BaseController {
	/***
	 * 用户手动操作Session
	 */
	@Autowired
	SessionService sessionService;

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	/**
	 * 用户列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView list() {
		return new ModelAndView("system/member/list");
	}

	/**
	 * bootstrap table分页查询用户列表
	 * 
	 * @param pageNumber 参数名必须为这个才能接收到bootstrap table传的参数
	 * @param pageSize   参数名必须为这个才能接收到bootstrap table传的参数
	 * @return
	 */
	@RequestMapping(value = "pageList")
	@ResponseBody
	public TableSplitResult<SysUser> pageList(ModelMap map, Integer pageSize, Integer pageNumber) {
		TableSplitResult<SysUser> page = userService.findUserByPage(map, pageNumber, pageSize);
		return page;
	}

	/**
	 * 在线用户管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "online")
	public ModelAndView online() {
		return new ModelAndView("system/member/online");
	}

	/**
	 * 查询在线用户列表分页查询
	 * 
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "pageOnline")
	@ResponseBody
	public TableSplitResult<List<UserOnlineBo>> pageOnline(Integer pageSize, Integer pageNumber) {
		TableSplitResult<List<UserOnlineBo>> page = new TableSplitResult<List<UserOnlineBo>>();

		List<UserOnlineBo> list = sessionService.getAllUsers();
		page.setRows(list);
		page.setTotal(list.size());

		page.setPageNo(null == pageNumber ? 1 : pageNumber);
		page.setPageSize(null == pageSize ? 10 : pageSize);

		return page;
	}

	/**
	 * 添加用户
	 * 
	 * @param ids 如果有多个，以“,”间隔
	 * @return
	 */
	@RequestMapping(value = "addUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addUser(SysUser sysUser) {
		SysUser user = UserManager.md5Pswd(sysUser);
		int result = userService.saveUser(user);
		if (result == 1) {
			resultMap.put("status", 200);
			resultMap.put("message", "添加用户成功！");
		} else {
			resultMap.put("status", 500);
			resultMap.put("message", "添加用户失败！");
		}
		return resultMap;
	}

	/**
	 * 在线用户详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "onlineDetail")
	public ModelAndView onlineDetail(ModelMap map, String sessionId) {
		UserOnlineBo onlineUser = sessionService.getSession(sessionId);
		map.put("onlineUser", onlineUser);
		return new ModelAndView("system/member/onlineDetail");
	}

	/**
	 * 改变Session状态
	 * 
	 * @param status
	 * @param sessionId
	 * @return
	 */
	@RequestMapping(value = "changeSessionStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changeSessionStatus(Boolean status, String sessionIds) {
		Map<String, Object> map = sessionService.changeSessionStatus(status, sessionIds);
		return map;
	}

	/**
	 * 根据ID删除，
	 * 
	 * @param ids 如果有多个，以“,”间隔。
	 * @return
	 */
	@RequestMapping(value = "deleteUserById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteUserById(String ids) {
		return userService.deleteUserByIds(ids);
	}

	/**
	 * 禁止登录
	 * 
	 * @param id     用户ID
	 * @param status 1:有效，0:禁止登录
	 * @return
	 */
	@RequestMapping(value = "activeUserByStatusAndId", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUserOnUserEnable(Integer id, Integer userEnable) {
		return userService.updateUserOnUserEnable(id, userEnable);
	}

	/**
	 * 密码重置
	 * 
	 * @return
	 */
	@RequestMapping(value = "resetPasswd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetPasswd(String id, String newPswd) {
		Integer userId = null;
		if (null != id && !"".equals(id)) {
			userId = new Integer(id);
		}
		SysUser user = userService.findUserById(userId);

		Set<String> roleSet = roleService.findRoleNameByUserId(userId);
		String admin_role_type = PropertiesUtil.getValueByKey("ADMIN_ROLE_TYPE", "config.properties");
		if (null != roleSet && roleSet.contains(admin_role_type)) {
			resultMap.put("status", 300);
			resultMap.put("message", "管理员密码不允许重置！");
			return resultMap;
		}

		user.setPassWord(newPswd);
		user = UserManager.md5Pswd(user);
		userService.updateUser(user);
		resultMap.put("status", 200);
		resultMap.put("message", "密码重置成功!");
		return resultMap;
	}

}
