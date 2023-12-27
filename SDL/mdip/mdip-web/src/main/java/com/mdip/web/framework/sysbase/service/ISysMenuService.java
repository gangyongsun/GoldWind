package com.mdip.web.framework.sysbase.service;

import java.util.List;
import java.util.Map;

import com.mdip.web.framework.base.entity.PageEntity;
import com.mdip.web.framework.base.exception.ServiceException;
import com.mdip.web.framework.base.service.ICRUDService;
import com.mdip.web.framework.sysbase.entity.SysMenu;
import com.mdip.web.framework.sysbase.entity.SysMenuSys;

public interface ISysMenuService extends ICRUDService<SysMenu> {

	/**
	 * 
	 * @param 得到自己的所有，供父亲调用
	 * @return checkbox 下拉等使用map方式
	 */
	public Map getAll() throws Exception;

	/**
	 * 根据联级找到显示的菜单
	 * 
	 * @param string
	 * @return
	 */
	public List<SysMenuSys> getMenuByUserNo(String string, String parentId, String is_show) throws Exception;

	/**
	 * 根据用户找用户的权限 给shiro使用的
	 * 
	 * @param string
	 * @return
	 */
	public List<SysMenu> getMenuByUserId(String string) throws Exception;

	public PageEntity getPageTree(SysMenu entity, PageEntity pageentity) throws Exception;

	public String getMenuFullNameById(String menuid) throws Exception;

	public String getMenuFullNameByUrl(String uri, String permissions) throws Exception;

	public List<SysMenu> getMenuByUser(String office_id, String login_user_id) throws ServiceException;

	public List<SysMenuSys> getOfficeMenu(String office_id, String user_id, String parentId, String is_show) throws ServiceException;

}