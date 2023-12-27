package cn.com.goldwind.kis.shiro.service;

import java.util.Map;
import java.util.Set;

import org.springframework.ui.ModelMap;

import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.shiro.bo.UserRoleAllocationBo;
import cn.com.goldwind.kis.shiro.domain.SysUser;

public interface UserService {

	/***
	 * 根据用户信息获取用户
	 *
	 * @param user
	 * @return
	 */
	public SysUser getUser(SysUser user);

	/**
	 * 根据userId获取用户权限
	 *
	 * @param userId userId
	 * @return 用户权限
	 */
	public Set<String> findResourcesByUserId(int userId);

	/**
	 * 用户登录，用于密码修改
	 * 
	 * @param username
	 * @param pswd
	 * @return
	 */
	public SysUser login(String username, String pswd);

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 */
	public int updateUser(SysUser user);

	/**
	 * 根据用户id删除用户,ids 如果有多个则以“,”间隔。
	 * 
	 * @param ids
	 * @return
	 */
	public Map<String, Object> deleteUserByIds(String ids);

	/**
	 * 根据ID改变用户有效状态
	 * 
	 * @param id
	 * @param userEnable
	 * @return
	 */
	public Map<String, Object> updateUserOnUserEnable(Integer id, Integer userEnable);

	/**
	 * 根据ID查找用户
	 * 
	 * @param userId
	 * @return
	 */
	public SysUser findUserById(Integer userId);

	/**
	 * 添加用户
	 * 
	 * @param sysUser
	 * @return
	 */
	public int saveUser(SysUser sysUser);

	/**
	 * bootstrap table 分页查询用户列表
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public TableSplitResult<SysUser> findUserByPage(Map<String, Object> map, Integer pageNo, Integer pageSize);

	/**
	 * bootstrap table 分页查询用户列表
	 * 
	 * @param modelMap
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public TableSplitResult<UserRoleAllocationBo> findUserAndRoleByPage(ModelMap modelMap, Integer pageNumber, Integer pageSize);

}
