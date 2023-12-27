package com.sojson.common.dao;

import java.util.List;
import java.util.Map;

import com.sojson.common.model.UUser;
import com.sojson.permission.bo.URoleBo;

public interface UUserMapper {
	/**
	 * 根据用户ID删除
	 * 
	 * @param id 用户ID
	 * @return
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * 添加用户
	 * 
	 * @param record UUser对象
	 * @return
	 */
	int insert(UUser record);

	/**
	 * 添加用户
	 * 
	 * @param record UUser对象
	 * @return
	 */
	int insertSelective(UUser record);

	/**
	 * 根据用户ID查询用户
	 * 
	 * @param id 用户ID
	 * @return
	 */
	UUser selectByPrimaryKey(Long id);

	/**
	 * 更新用户
	 * 
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(UUser record);

	/**
	 * 更新用户
	 * 
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(UUser record);

	/**
	 * 用户登录
	 * 
	 * @param map
	 * @return
	 */
	UUser login(Map<String, Object> map);

	/**
	 * 根据用户email查询用户
	 * 
	 * @param email
	 * @return
	 */
	UUser findUserByEmail(String email);

	/**
	 * 根据用户ID查询角色
	 * 
	 * @param id 用户ID
	 * @return
	 */
	List<URoleBo> selectRoleByUserId(Long id);

	/**
	 * 根据主键id查询用户
	 * @param id
	 * @return
	 */
	UUser findUserById(Long id);

}