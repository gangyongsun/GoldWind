package cn.com.goldwind.kis.shiro.dao;

import java.util.List;

import cn.com.goldwind.kis.shiro.domain.SysRoleResources;
import tk.mybatis.mapper.common.Mapper;

public interface SysRoleResourcesMapper extends Mapper<SysRoleResources> {

	/**
	 * 根据资源ID查询角色资源对应
	 * 
	 * @param id 资源ID
	 * @return
	 */
	List<SysRoleResources> findRoleResourceByResourceId(Integer id);

	/**
	 * 根据角色ID删除角色资源对应关系
	 * 
	 * @param roleId 角色ID
	 * @return
	 */
	int deleteRoleResourceRelationshipByRoleId(Integer roleId);

	/**
	 * 根据角色IDs删除角色资源对应关系
	 * 
	 * @param roleIds 角色ID
	 * @return
	 */
	int deleteRoleResourceRelationshipByRoleIds(Integer[] roleIds);

}