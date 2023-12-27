package cn.com.goldwind.kis.shiro.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.ui.ModelMap;

import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.shiro.bo.SysResourcesBo;
import cn.com.goldwind.kis.shiro.domain.SysResources;

public interface ResourcesService {

	/**
	 * 添加资源
	 * 
	 * @param resource
	 * @return
	 */
	SysResources saveResource(SysResources resource);

	/**
	 * 更新资源
	 * 
	 * @param resource
	 * @return
	 */
	int updateResource(SysResources resource);

	/**
	 * 根据资源ids删除资源
	 * 
	 * @param ids
	 * @return
	 */
	Map<String, Object> deleteResourceByIds(String ids);

	/**
	 * 根据用户ID查询资源，放入到Authorization里
	 * 
	 * @param userId
	 * @return
	 */
	Set<String> findResourceByUserId(Integer userId);

	/**
	 * 根据主键查询资源
	 * 
	 * @param id
	 * @return
	 */
	SysResources selectByPrimaryKey(Integer id);

	/**
	 * 分页查询
	 * 
	 * @param modelMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	TableSplitResult<SysResources> findResourceByPage(ModelMap modelMap, Integer pageNumber, Integer pageSize);

	/**
	 * 根据角色ID查询角色权限
	 * 
	 * @param id
	 * @return
	 */
	List<SysResourcesBo> selectResourceByRoleId(Integer id);

	/**
	 * 为角色添加资源
	 * 
	 * @param roleId
	 * @param ids
	 * @return
	 */
	Map<String, Object> addResource2Role(Integer roleId, String ids);

	/**
	 * 根据角色id清空角色所拥有的权限
	 * 
	 * @param roleIds
	 * @return
	 */
	int clearRoleResourceRelationshipByRoleIds(Integer[] roleIds);
}
