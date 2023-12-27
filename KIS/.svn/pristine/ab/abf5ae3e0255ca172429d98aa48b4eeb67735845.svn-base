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
import cn.com.goldwind.kis.shiro.bo.RoleResourceAllocationBo;
import cn.com.goldwind.kis.shiro.bo.SysResourcesBo;
import cn.com.goldwind.kis.shiro.service.ResourcesService;
import cn.com.goldwind.kis.shiro.service.RoleService;
import cn.com.goldwind.kis.utils.StringUtils;

/**
 * 用户权限分配
 * 
 * @author alvin
 *
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("resource")
public class ResourcesAllocationController extends BaseController {

	@Autowired
	ResourcesService resourcesService;
	
	@Autowired
	RoleService roleService;

	/**
	 * 权限分配页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "allocation")
	public ModelAndView allocation() {
		return new ModelAndView("system/resource/allocation");
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
	public TableSplitResult<RoleResourceAllocationBo>  pageList4Allocation(ModelMap modelMap, Integer pageSize, Integer pageNumber) {
//		modelMap.put("findContent", findContent);
		TableSplitResult<RoleResourceAllocationBo> page = roleService.findRoleAndResourceByPage(modelMap, pageNumber, pageSize);
		modelMap.put("page", page);
		return page;
	}

	/**
	 * 根据角色ID查询权限
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "selectResourceByRoleId")
	@ResponseBody
	public List<SysResourcesBo> selectResourceByRoleId(Integer id) {
		List<SysResourcesBo> resourceBoList = resourcesService.selectResourceByRoleId(id);
		return resourceBoList;
	}

	/**
	 * 为角色添加权限
	 * 
	 * @param roleId 角色ID
	 * @param ids    权限ID，以‘,’间隔
	 * @return
	 */
	@RequestMapping(value = "addResource2Role")
	@ResponseBody
	public Map<String, Object> addResource2Role(Integer roleId, String ids) {
		Map<String, Object> resultMap=resourcesService.addResource2Role(roleId, ids);
		return resultMap;
	}

	/**
	 * 根据角色id清空角色所拥有的权限
	 * 
	 * @param roleIds 角色ID ，以‘,’间隔
	 * @return
	 */
	@RequestMapping(value = "clearRoleResourceRelationshipByRoleIds")
	@ResponseBody
	public Map<String, Object> clearRoleResourceRelationshipByRoleIds(String roleIds) {
		String[] array1 = null;
		if (StringUtils.contains(roleIds, ",")) {
			array1 = roleIds.split(",");
		} else {
			array1 = new String[] { roleIds };
		}
		Integer[] roleIdsArray = (Integer[])ConvertUtils.convert(array1, Integer.class);
		
		try {
			resourcesService.clearRoleResourceRelationshipByRoleIds(roleIdsArray);
			resultMap.put("status", 200);
			resultMap.put("message", "清空角色权限成功！");
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "清空角色权限失败！");
		}
		return resultMap;
	}
}
