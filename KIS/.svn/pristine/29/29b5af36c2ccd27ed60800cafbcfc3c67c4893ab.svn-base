package cn.com.goldwind.kis.shiro.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.goldwind.kis.shiro.bo.SysRoleBo;
import cn.com.goldwind.kis.shiro.domain.SysRole;
import tk.mybatis.mapper.common.Mapper;

public interface SysRoleMapper extends Mapper<SysRole>{
	/**
	 * 根据用户ID查询角色类型集合
	 * 
	 * @param id 用户ID
	 * @return
	 */
	Set<String> findRoleNameByUserId(Integer id);
	
	/**
	 * 根据用户主键ID查询用户角色
	 * 
	 * @param id
	 * @return
	 */
	List<SysRoleBo> findRoleByUserId(Integer id);

	/**
	 * 根据用户ID，查询所有权限
	 * 
	 * @param map
	 * @return
	 */
	List<SysRole> findNowAllPermission(Map<String, Object> map);

}