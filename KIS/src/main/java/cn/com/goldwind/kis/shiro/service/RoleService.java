package cn.com.goldwind.kis.shiro.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.ui.ModelMap;

import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.shiro.bo.RoleResourceAllocationBo;
import cn.com.goldwind.kis.shiro.bo.SysRoleBo;
import cn.com.goldwind.kis.shiro.domain.SysRole;

public interface RoleService {

	/**
	 * 根据用户ID查询权限
	 * 
	 * @param id
	 * @return
	 */
	public List<SysRoleBo> findRoleByUserId(int id);

	/**
	 * 根据用户ID查询用户角色名称列表
	 * 
	 * @param userId
	 * @return
	 */
	Set<String> findRoleNameByUserId(Integer userId);

	/**
	 * 为用户添加角色
	 * 
	 * @param userId
	 * @param ids
	 * @return
	 */
	public Map<String, Object> addRole2User(int userId, String ids);

	/**
	 * 添加角色
	 * 
	 * @param role
	 * @return
	 */
	int saveRole(SysRole role);

	/**
	 * 更新角色
	 * 
	 * @param role
	 * @return
	 */
	int updateRole(SysRole role);

	/**
	 * 根据ID删除角色信息
	 * 
	 * @param ids
	 * @return
	 */
	Map<String, Object> deleteRoleByIds(String ids);

	/**
	 * 根据用户id删除用户拥有的角色
	 * 
	 * @param userIds
	 * @return
	 */
	public Map<String, Object> deleteRoleByUserIds(String userIds);

	/**
	 * 查询目前拥有的角色列表
	 * 
	 * @return
	 */
	List<SysRole> findNowAllPermission();

	/**
	 * 分页查询角色列表
	 * 
	 * @param modelMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	TableSplitResult<SysRole> findRoleByPage(ModelMap modelMap, Integer pageNumber, Integer pageSize);

	/**
	 * 分页查询角色资源关系列表
	 * 
	 * @param modelMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	TableSplitResult<RoleResourceAllocationBo> findRoleAndResourceByPage(ModelMap modelMap, Integer pageNumber, Integer pageSize);

	/**
	 * 根据用户id清空用户拥有的角色
	 * 
	 * @param userIds
	 * @return
	 */
	public int clearUserRoleRelationshipByUserIds(Integer[] userIds);

}
