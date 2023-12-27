package com.sojson.common.dao;

import java.util.List;
import java.util.Set;

import com.sojson.common.model.UPermission;
import com.sojson.permission.bo.UPermissionBo;

public interface UPermissionMapper {
	/**
	 * 根据ID删除权限
	 * 
	 * @param id 权限ID
	 * @return
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * 添加权限
	 * 
	 * @param record 权限对象
	 * @return
	 */
	int insert(UPermission record);

	/**
	 * 添加权限
	 * 
	 * @param record 权限对象
	 * @return
	 */
	int insertSelective(UPermission record);

	/**
	 * 查询权限
	 * 
	 * @param id 权限ID
	 * @return
	 */
	UPermission selectByPrimaryKey(Long id);

	/**
	 * 更新权限
	 * 
	 * @param record 权限对象
	 * @return
	 */
	int updateByPrimaryKeySelective(UPermission record);

	/**
	 * 更新权限
	 * 
	 * @param record 权限对象
	 * @return
	 */
	int updateByPrimaryKey(UPermission record);

	/**
	 * 根据角色获取权限<br>
	 * marker:0,没有权限;<br>
	 * marker：非0(和up.id)一致表示有权限
	 * 
	 * @param id 角色ID
	 * @return 权限列表
	 */
	List<UPermissionBo> selectPermissionById(Long id);

	/**
	 * 根据用户ID获取权限的Set集合
	 * 
	 * @param id 权限ID
	 * @return
	 */
	Set<String> findPermissionByUserId(Long id);
}