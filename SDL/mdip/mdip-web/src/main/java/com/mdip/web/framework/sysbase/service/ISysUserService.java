package com.mdip.web.framework.sysbase.service;

import java.util.List;
import java.util.Map;

import com.mdip.web.framework.base.exception.ServiceException;
import com.mdip.web.framework.base.service.ICRUDService;
import com.mdip.web.framework.sysbase.entity.SysRole;
import com.mdip.web.framework.sysbase.entity.SysUser;

public interface ISysUserService extends ICRUDService<SysUser> {
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;

	// 如果父子表，或是多对多表时生成对应的子表内容与多对多内容
	/**
	 * 
	 * @param sysUserId
	 *            当前用户的ID
	 * @return 当前用户的具有角色的ID集合，集合方式为"id,id,id"
	 */
	public String getSysRole(String sysUserId) throws Exception;

	public List<SysRole> getSysRoleList(String sysUserId) throws Exception;

	public Map getAll() throws Exception;

	public boolean configSysRole(String id, String sys_role_ids, String update_by) throws Exception;

	// public SysUser Login(String name,String md5pwd,String ip)throws
	// Exception;

	public void updateUserLoginInfo(SysUser user) throws Exception;

	public SysUser getByLoginName(String loginName) throws Exception;

	public boolean configSysMenu(String id, String role_ids, String menu_ids, String update_by) throws ServiceException;

	public String getSysMenuByUserId(String sysUser_Id) throws ServiceException;

	public Map getAllUserMap() throws ServiceException;

	// public String getSysMenuByOfficeId(String sysOffice_Id) throws
	// ServiceException;
	public Map getUserMap();
}