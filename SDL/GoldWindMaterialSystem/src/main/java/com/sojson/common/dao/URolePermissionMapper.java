package com.sojson.common.dao;

import java.util.List;
import java.util.Map;

import com.sojson.common.model.URolePermission;

public interface URolePermissionMapper {
	/**
	 * 添加角色权限对应
	 * 
	 * @param record
	 * @return
	 */
	int insert(URolePermission record);

	/**
	 * 添加角色权限对应
	 * 
	 * @param record
	 * @return
	 */
	int insertSelective(URolePermission record);

	/**
	 * 根据权限ID查询角色权限对应
	 * 
	 * @param id 权限ID
	 * @return
	 */
	List<URolePermission> findRolePermissionByPid(Long id);

	/**
	 * 根据角色ID查询角色权限对应
	 * 
	 * @param id 角色ID
	 * @return
	 */
	List<URolePermission> findRolePermissionByRid(Long id);

	/**
	 * 根据权限 && 角色ID查找
	 * 
	 * @param entity
	 * @return
	 */
	List<URolePermission> find(URolePermission entity);

	/**
	 * 根据权限ID删除
	 * 
	 * @param id 权限ID
	 * @return
	 */
	int deleteByPid(Long id);

	/**
	 * 根据角色ID删除
	 * 
	 * @param id 角色ID
	 * @return
	 */
	int deleteByRid(Long id);

	/**
	 * 根据角色ID && 权限ID删除
	 * 
	 * @param entity
	 * @return
	 */
	int delete(URolePermission entity);

	/**
	 * 根据角色IDs删除
	 * 
	 * @param resultMap
	 * @return
	 */
	int deleteByRids(Map<String, Object> resultMap);
}