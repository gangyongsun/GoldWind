package com.sojson.common.dao;

import java.util.List;
import java.util.Map;

import com.sojson.common.model.UUserRole;

public interface UUserRoleMapper {
	/**
	 * 添加用户角色关系
	 * 
	 * @param record
	 * @return
	 */
	int insert(UUserRole record);

	/**
	 * 添加用户角色关系
	 * 
	 * @param record
	 * @return
	 */
	int insertSelective(UUserRole record);

	/**
	 * 根据用户ID删除
	 * 
	 * @param id 用户ID
	 * @return
	 */
	int deleteByUserId(Long id);

	/**
	 * 根据用户IDs删除
	 * 
	 * @param resultMap
	 * @return
	 */
	int deleteRoleByUserIds(Map<String, Object> resultMap);

	/**
	 * 根据角色ID查询用户ID
	 * 
	 * @param id 角色ID
	 * @return
	 */
	List<Long> findUserIdByRoleId(Long id);
}