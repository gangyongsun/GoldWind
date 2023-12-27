package com.sojson.common.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sojson.common.model.URole;

public interface URoleMapper {
	/**
	 * 根据ID删除角色
	 * 
	 * @param id 角色ID
	 * @return
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * 添加角色
	 * 
	 * @param record 角色对象
	 * @return
	 */
	int insert(URole record);

	/**
	 * 添加角色
	 * 
	 * @param record 角色对象
	 * @return
	 */
	int insertSelective(URole record);

	/**
	 * 查询角色
	 * 
	 * @param id 角色ID
	 * @return
	 */
	URole selectByPrimaryKey(Long id);

	/**
	 * 更新角色
	 * 
	 * @param record 角色对象
	 * @return
	 */
	int updateByPrimaryKeySelective(URole record);

	/**
	 * 更新角色
	 * 
	 * @param record 角色对象
	 * @return
	 */
	int updateByPrimaryKey(URole record);

	/**
	 * 根据用户ID查询角色类型集合
	 * 
	 * @param id 用户ID
	 * @return
	 */
	Set<String> findRoleByUserId(Long id);

	/**
	 * 根据用户ID，查询所有权限
	 * 
	 * @param map
	 * @return
	 */
	List<URole> findNowAllPermission(Map<String, Object> map);

	void initData();
}