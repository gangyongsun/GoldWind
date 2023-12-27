package cn.com.goldwind.kis.shiro.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.shiro.bo.SysRoleBo;
import cn.com.goldwind.kis.shiro.bo.UserRoleAllocationBo;
import cn.com.goldwind.kis.shiro.service.RoleService;
import cn.com.goldwind.kis.shiro.service.UserService;
import cn.com.goldwind.kis.utils.StringUtils;

/**
 * 用户角色分配
 * 
 * @author alvin
 *
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("role")
public class RoleAllocationController extends BaseController {
	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	/**
	 * 用户角色权限分配页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "allocation")
	public ModelAndView allocation() {
		return new ModelAndView("system/role/allocation");
	}
	
	/**
	 * 用户角色分配页面列表
	 * 
	 * @param modelMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "pageList4Allocation")
	@ResponseBody
	public TableSplitResult<UserRoleAllocationBo>  pageList4Allocation(ModelMap modelMap, Integer pageSize, Integer pageNumber) {
//		modelMap.put("findContent", findContent);
		TableSplitResult<UserRoleAllocationBo> page = userService.findUserAndRoleByPage(modelMap, pageNumber, pageSize);
		modelMap.put("page", page);
		return page;
	}

	/**
	 * 根据用户ID查询权限
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "selectRoleByUserId")
	@ResponseBody
	public List<SysRoleBo> selectRoleByUserId(Integer id) {
		List<SysRoleBo> sysRoleBoList = roleService.findRoleByUserId(id);
		return sysRoleBoList;
	}

	/**
	 * 操作用户的角色
	 * 
	 * @param userId  用户ID
	 * @param roleIds 角色ID，以‘,’间隔
	 * @return
	 */
	@RequestMapping(value = "addRole2User")
	@ResponseBody
	public Map<String, Object> addRole2User(Integer userId, String roleIds) {
		return roleService.addRole2User(userId, roleIds);
	}

	/**
	 * 根据用户id清空用户拥有的角色
	 * 
	 * @param userIds 用户ID ，以‘,’间隔
	 * @return
	 */
	@RequestMapping(value = "clearUserRoleRelationshipByUserIds")
	@ResponseBody
	public Map<String, Object> clearUserRoleRelationshipByUserIds(String userIds) {
		String[] array1 = null;
		if (StringUtils.contains(userIds, ",")) {
			array1 = userIds.split(",");
		} else {
			array1 = new String[] { userIds };
		}
		Integer[] userIdArray = (Integer[])ConvertUtils.convert(array1, Integer.class);
		
		try {
			roleService.clearUserRoleRelationshipByUserIds(userIdArray);
			resultMap.put("status", 200);
			resultMap.put("message", "清空用户角色成功！");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "清空用户角色失败！");
		}
		return resultMap;
	}
}
