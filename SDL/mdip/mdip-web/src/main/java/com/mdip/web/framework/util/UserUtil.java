package com.mdip.web.framework.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.mdip.web.framework.base.cache.CacheUtils;
import com.mdip.web.framework.base.util.SpringContextHolder;
import com.mdip.web.framework.shiro.Principal;
import com.mdip.web.framework.sysbase.entity.SysMenu;
import com.mdip.web.framework.sysbase.entity.SysOffice;
import com.mdip.web.framework.sysbase.entity.SysRole;
import com.mdip.web.framework.sysbase.entity.SysUser;
import com.mdip.web.framework.sysbase.service.ISysMenuService;
import com.mdip.web.framework.sysbase.service.ISysUserService;

public class UserUtil {
	/**
	 * 获取当前用户
	 * 
	 * @return 取不到返回null
	 */
	public static String getCurrentUser() {
		Principal principal = UserUtil.getPrincipal();
		return principal == null ? null : principal.getLoginName();
		// return "admin";
	}

	/**
	 * 获取所有用户的登录名和真实姓名，组成下拉框所需的map
	 * 
	 * @return map
	 */
	public static Map getAllUserMap() {
		Map map = new HashMap();
		try {
			map = sysUserService.getAllUserMap();
			return map;
		} catch (Exception e) {
			return new HashMap();
		}
	}

	private static ISysUserService sysUserService = ((ISysUserService) SpringContextHolder.getBean("sysUserServiceImpl"));
	// @Resource(name = "sysMenuServiceImp")
	// private SysMenuService sysMenuService;
	private static ISysMenuService sysMenuService = ((ISysMenuService) SpringContextHolder.getBean("sysMenuServiceImpl"));

	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_ID_ = "id_";
	public static final String USER_CACHE_LOGIN_NAME_ = "ln";
	public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";

	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_MENU_LIST = "menuList";
	public static final String CACHE_AREA_LIST = "areaList";
	public static final String CACHE_OFFICE_LIST = "officeList";
	public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";

	/**
	 * 根据ID获取用户
	 * 
	 * @param id
	 * @return 取不到返回null
	 */
	public static SysUser get(String id) {
		SysUser user = (SysUser) CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
		if (user == null) {
			try {
				user = (SysUser) sysUserService.queryById(id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (user == null) {
				return null;
			}
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLogin_name(), user);

		}
		return user;
	}

	/**
	 * 根据登录名获取用户姓名
	 * 
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static String getNameByLoginName(String loginName) {
		String name = "";
		SysUser user = new SysUser();
		try {
			user = sysUserService.getByLoginName(loginName);
			if (user != null) {
				name = user.getName();
			}
		} catch (Exception e) {
		}
		return name;
	}

	/**
	 * 根据登录名获取用户
	 * 
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static SysUser getByLoginName(String loginName) {
		loginName = loginName.trim();
		SysUser user = (SysUser) CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
		if (user == null) {
			try {
				user = sysUserService.getByLoginName(loginName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (user == null) {
				return null;
			}
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLogin_name(), user);
		}
		return user;
	}

	/**
	 * 更新用户信息，包括数据库和缓存
	 */
	public static SysUser updateUser(SysUser user) {
		clearCache(user);
		CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
		CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLogin_name(), user);
		// if (userDao.update(user)) {
		// CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
		// CacheUtils.put(USER_CACHE,
		// USER_CACHE_LOGIN_NAME_ + user.getLogin_name(), user);
		// }
		return user;
	}

	/**
	 * 清除当前用户缓存
	 */
	public static void clearCache() {
		removeCache(CACHE_ROLE_LIST);
		removeCache(CACHE_MENU_LIST);
		removeCache(CACHE_AREA_LIST);
		removeCache(CACHE_OFFICE_LIST);
		removeCache(CACHE_OFFICE_ALL_LIST);
		UserUtil.clearCache(getUser());
	}

	/**
	 * 清除指定用户缓存
	 * 
	 * @param user
	 */
	public static void clearCache(SysUser user) {
		CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLogin_name());
		/*
		 * CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ +
		 * user.getOldLoginName()); if (user.getOffice() != null &&
		 * user.getOffice().getId() != null){ CacheUtils.remove(USER_CACHE,
		 * USER_CACHE_LIST_BY_OFFICE_ID_ + user.getOffice().getId()); }
		 */
	}

	/**
	 * 获取当前用户
	 * 
	 * @return 取不到返回 new User()
	 */
	public static SysUser getUser() {
		Principal principal = getPrincipal();
		if (principal != null) {
			SysUser user = get(principal.getId());
			if (user != null) {
				return user;
			}
			return new SysUser();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new SysUser();
	}

	/**
	 * 获取当前用户角色列表
	 * 
	 * @return
	 */
	public static List<SysRole> getRoleList() {
		@SuppressWarnings("unchecked")
		List<SysRole> roleList = (List<SysRole>) getCache(CACHE_ROLE_LIST);
		if (roleList == null) {
			SysUser user = getUser();
			// TODO 是否要更清楚的开发

			try {
				roleList = sysUserService.getSysRoleList(user.getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			putCache(CACHE_ROLE_LIST, roleList);
		}
		return roleList;
	}

	/**
	 * 获取当前用户授权菜单
	 * 
	 * @return
	 */
	public static List<SysMenu> getMenuList() {
		@SuppressWarnings("unchecked")
		List<SysMenu> menuList = (List<SysMenu>) getCache(CACHE_MENU_LIST);
		if (menuList == null) {
			SysUser user = getUser();
			try {
				menuList = sysMenuService.getMenuByUserId(user.getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			putCache(CACHE_MENU_LIST, menuList);
		}
		return menuList;
	}

	/**
	 * 获取当前用户授权的区域
	 * 
	 * @return
	 */
	/*
	 * public static List<Area> getAreaList(){
	 * 
	 * @SuppressWarnings("unchecked") List<Area> areaList =
	 * (List<Area>)getCache(CACHE_AREA_LIST); if (areaList == null){ areaList =
	 * areaDao.findAllList(new Area()); putCache(CACHE_AREA_LIST, areaList); }
	 * return areaList; }
	 */

	/**
	 * 获取当前用户有权限访问的部门
	 * 
	 * @return
	 */
	public static List<SysOffice> getOfficeList() {
		@SuppressWarnings("unchecked")
		List<SysOffice> officeList = (List<SysOffice>) getCache(CACHE_OFFICE_LIST);

		return officeList;
	}

	/**
	 * 获取当前用户有权限访问的部门
	 * 
	 * @return
	 */
	public static List<SysOffice> getOfficeAllList() {
		@SuppressWarnings("unchecked")
		List<SysOffice> officeList = (List<SysOffice>) getCache(CACHE_OFFICE_ALL_LIST);
		/*
		 * if (officeList == null){ officeList = officeDao.findAllList(new
		 * Office()); }
		 */
		return officeList;
	}

	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**
	 * 获取当前登录者对象
	 */
	public static Principal getPrincipal() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				return principal;
			}
			// subject.logout();
		} catch (UnavailableSecurityManagerException e) {

		} catch (InvalidSessionException e) {

		}
		return null;
	}

	public static Session getSession() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null) {
				session = subject.getSession();
			}
			if (session != null) {
				return session;
			}
			// subject.logout();
		} catch (InvalidSessionException e) {

		}
		return null;
	}

	// ============== User Cache ==============

	public static Object getCache(String key) {
		return getCache(key, null);
	}

	public static Object getCache(String key, Object defaultValue) {
		// Object obj = getCacheMap().get(key);
		Object obj = getSession().getAttribute(key);
		return obj == null ? defaultValue : obj;
	}

	public static void putCache(String key, Object value) {
		// getCacheMap().put(key, value);
		getSession().setAttribute(key, value);
	}

	public static void removeCache(String key) {
		// getCacheMap().remove(key);
		getSession().removeAttribute(key);
	}

}
