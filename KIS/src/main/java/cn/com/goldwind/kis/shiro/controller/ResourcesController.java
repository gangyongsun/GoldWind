package cn.com.goldwind.kis.shiro.controller;

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
import cn.com.goldwind.kis.shiro.domain.SysResources;
import cn.com.goldwind.kis.shiro.service.ResourcesService;
import cn.com.goldwind.kis.utils.LoggerUtils;

/**
 * 资源管理
 * 
 * @author alvin
 *
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("resource")
public class ResourcesController extends BaseController {

	@Autowired
	ResourcesService resourcesService;

	/**
	 * 资源列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "index")
	public ModelAndView index() {
		return new ModelAndView("system/resource/index");
	}

	/**
	 * 资源列表分页查询
	 * 
	 * @param modelMap
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "pageList")
	@ResponseBody
	public TableSplitResult<SysResources> pageList(ModelMap modelMap, Integer pageSize, Integer pageNumber) {
//		modelMap.put("findContent", findContent);
		TableSplitResult<SysResources> page = resourcesService.findResourceByPage(modelMap, pageNumber, pageSize);
		modelMap.put("page", page);
		return page;
	}

	/**
	 * 资源添加
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "addResource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addResource(SysResources resource) {
		try {
			SysResources entity = resourcesService.saveResource(resource);
			resultMap.put("status", 200);
			resultMap.put("message", "添加成功！");
			resultMap.put("entity", entity);
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "添加失败，请刷新后再试！");
			LoggerUtils.fmtError(getClass(), e, "添加资源报错！source[%s]", resource.toString());
		}
		return resultMap;
	}

	/**
	 * 更新资源信息
	 * 
	 * @param resource
	 * @return
	 */
	@RequestMapping(value = "updateResource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateResource(SysResources resource) {
		try {
			int count = resourcesService.updateResource(resource);
			resultMap.put("status", 200);
			resultMap.put("message", "更新成功！");
			resultMap.put("count", count);
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "更新失败！");
			LoggerUtils.fmtError(getClass(), e, "更新权限报错！source[%s]", resource.toString());
		}
		return resultMap;
	}

	/**
	 * 根据资源IDs删除资源
	 * <p>
	 * 如果有角色在使用资源，那么就不能删除
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "deleteResourceById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteRoleById(String ids) {
		return resourcesService.deleteResourceByIds(ids);
	}
}
