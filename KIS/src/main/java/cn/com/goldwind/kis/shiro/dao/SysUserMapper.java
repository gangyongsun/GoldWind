package cn.com.goldwind.kis.shiro.dao;

import java.util.Map;

import cn.com.goldwind.kis.shiro.domain.SysUser;
import tk.mybatis.mapper.common.Mapper;

public interface SysUserMapper extends Mapper<SysUser> {

//	/**
//	 * 根据用户主键ID查询用户
//	 * 
//	 * @param id
//	 * @return
//	 */
//	SysUser findUserByPrimaryKey(Integer id);

//	/**
//	 * 根据用户主键ID查询用户
//	 * 
//	 * @param id
//	 * @return
//	 */
//	SysUser selectByPrimaryKey(Integer id);
//
//	/**
//	 * 根据用户主键ID删除用户
//	 * 
//	 * @param id
//	 * @return
//	 */
//	int deleteByPrimaryKey(Integer id);

//	/**
//	 * 添加用户
//	 * 
//	 * @param record UUser对象
//	 * @return
//	 */
//	int insertSelective(SysUser sysUser);

//	/**
//	 * 更新用户
//	 * 
//	 * @param sysUser
//	 * @return
//	 */
//	int updateByPrimaryKeySelective(SysUser sysUser);

	/**
	 * 用户登录
	 * 
	 * @param map
	 * @return
	 */
	SysUser login(Map<String, Object> map);

	/**
	 * 根据用户userName查询用户
	 * 
	 * @param userName
	 * @return
	 */
	SysUser findUserByName(String userName);


}