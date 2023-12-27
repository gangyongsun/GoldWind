package cn.com.goldwind.kis.shiro.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.shiro.domain.SysRole;
import cn.com.goldwind.kis.shiro.service.RoleService;
import cn.com.goldwind.kis.shiro.utils.UserManager;
import cn.com.goldwind.kis.utils.LoggerUtils;

/**
 * 用户角色管理
 * 
 * @author alvin
 *
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("role")
public class RoleController extends BaseController {
	@Autowired
	RoleService roleService;

	/**
	 * 角色列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "index")
	public ModelAndView index() {
		return new ModelAndView("system/role/index");
	}
	
	/**
	 * 分页查询角色列表
	 * 
	 * @param modelMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "pageList")
	@ResponseBody
	public TableSplitResult<SysRole>  pageList(ModelMap modelMap, Integer pageSize, Integer pageNumber) {
//		modelMap.put("findContent", findContent);
		TableSplitResult<SysRole> page = roleService.findRoleByPage(modelMap, pageNumber, pageSize);
		modelMap.put("page", page);
		return page;
	}
	
	/**
	 * 角色添加
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "addRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addRole(SysRole role) {
		try {
			int count = roleService.saveRole(role);
			resultMap.put("status", 200);
			resultMap.put("message", "添加成功！");
			resultMap.put("successCount", count);
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "添加失败，请刷新后再试！");
			LoggerUtils.fmtError(getClass(), e, "添加角色报错!source[%s]", role.toString());
		}
		return resultMap;
	}

	/**
	 * 更新角色
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "updateRole", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateRole(SysRole role) {
		try {
			int count = roleService.updateRole(role);
			resultMap.put("status", 200);
			resultMap.put("message", "更新成功！");
			resultMap.put("count", count);
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "更新失败，请刷新后再试！");
			LoggerUtils.fmtError(getClass(), e, "更新角色报错!source[%s]", role.toString());
		}
		return resultMap;
	}
	
	/**
	 * 删除根据IDs角色<p>
	 * 如果有用户在使用角色，那么就不能删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "deleteRoleByIds", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteRoleById(String ids) {
		return roleService.deleteRoleByIds(ids);
	}

	/**
	 * 我的权限页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "mypermission", method = RequestMethod.GET)
	public ModelAndView mypermission() {
		return new ModelAndView("system/permission/mypermission");
	}

	/**
	 * 我的权限
	 * 
	 * @return
	 */
	@RequestMapping(value = "getPermissionTree", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getPermissionTree() {
		// 查询用户所有的角色 ---> 权限
		List<SysRole> roles = roleService.findNowAllPermission();
		// 把查询出来的roles 转换成bootstarp 的 tree数据
		List<Map<String, Object>> data = UserManager.toTreeData(roles);
		return data;
	}
	
}
