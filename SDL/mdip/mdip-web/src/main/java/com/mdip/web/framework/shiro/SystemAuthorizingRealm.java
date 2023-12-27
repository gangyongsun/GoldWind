package com.mdip.web.framework.shiro;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdip.common.util.DateUtil;
import com.mdip.web.framework.base.util.Encodes;
import com.mdip.web.framework.config.Config;
import com.mdip.web.framework.security.session.ISessionDao;
import com.mdip.web.framework.sysbase.entity.SysMenu;
import com.mdip.web.framework.sysbase.entity.SysUser;
import com.mdip.web.framework.sysbase.service.ISysUserService;
import com.mdip.web.framework.util.UserUtil;

import lombok.extern.slf4j.Slf4j;

@Service
//@DependsOn({"userDao","roleDao","menuDao"})
@Slf4j
public class SystemAuthorizingRealm extends AuthorizingRealm {
	
	//@Resource(name = "sysUserServiceImpl")	
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private ISessionDao sessionDao;
	/**
	 * 认证     回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		UsernamePasswordToken usernamePasswordToke = (UsernamePasswordToken) authcToken;
		
		//String username = usernamePasswordToke.getUsername();
	
		//获取当前活动会话数
		int activeSessionSize = sessionDao.getActiveSessions(false).size();
		if (log.isDebugEnabled()){
			log.debug("login submit, active session size: {}, username: {}", activeSessionSize, usernamePasswordToke.getUsername());
		}
		// 校验登录验证码
		String deviceType = usernamePasswordToke.getDeviceType();
//		if("pc".equals(deviceType)){
//			Session session = UserUtil.getSession();
//			String code = (String)session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
//			if (usernamePasswordToke.getCaptcha() == null || !usernamePasswordToke.getCaptcha().toUpperCase().equals(code)){
//				throw new AuthenticationException("validateCodeErro");//验证码错误
//			}
//		}
		
		// 校验用户名密码
		SysUser user = UserUtil.getByLoginName(usernamePasswordToke.getUsername());
		if (user != null) {
			if ("0".equals(user.getLogin_flag())){
				throw new AuthenticationException("该用户被冻结，请找管理员解锁账户");//用户、ak被封禁
			}
			byte[] salt = Encodes.decodeHex(user.getPassword().substring(0,16));//盐
			return new SimpleAuthenticationInfo(new Principal(user, usernamePasswordToke.getDeviceType()), 
					user.getPassword().substring(16), ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}
	}

	/**
	 * 授权    查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) getAvailablePrincipal(principals);
		// 获取当前已登录的用户
		if (!Config.TRUE.equals(Config.usermultiAccountLogin)){
			Collection<Session> sessions = sessionDao.getActiveSessions(true, principal, UserUtil.getSession());
			if (sessions.size() > 0){
				// 如果是登录进来的，则踢出已在线用户
				if (UserUtil.getSubject().isAuthenticated()){
					for (Session session : sessions){
						sessionDao.delete(session);
					}
				}else{	// 记住我进来的，并且当前用户已登录，则退出当前用户提示信息。 互斥登录只留一个，即不让存在有记住我！
					UserUtil.getSubject().logout();
					throw new AuthenticationException("账号已在其它地方登录，请重新登录。");
				}
			}
		}
		//SysUser user = getSystemService().getUserByLoginName(principal.getLoginName());
		SysUser user = UserUtil.getByLoginName(principal.getLoginName());
		if (user != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			List<SysMenu> list = UserUtil.getMenuList();
			for (SysMenu menu : list){
				if (StringUtils.isNotBlank(menu.getPermission())){
					// 添加基于Permission的权限信息					
						info.addStringPermission(menu.getPermission());				
				}
			}
			
			/*//这个确定页面中<shiro:hasRole>标签的name的值
			info.addRole("admin");
			//这个就是页面中 <shiro:hasPermission> 标签的name的值
			info.addStringPermission("ssj:hello:save");
			info.addStringPermission("ssj:hello:view");
			info.addStringPermission("ssj:hello:update");*/
//			info.addStringPermission("sys:myuser:view");
			// 添加用户权限  暂不使用角色user:view
			info.addStringPermission("user");
			// 添加用户角色信息
		//	List<SysRole> roles =  getRoleList()
		/*	for (SysRole role : UserUtil.getRoleList()){
				info.addRole(role.getName());
			}*/
			// 更新登录IP和时间
			user.setLogin_ip(UserUtil.getSession().getHost());
			user.setLogin_date(DateUtil.getDateTime());
			UserUtil.updateUser(user);
			// 记录登录日志
//			LogUtils.saveLog(Servlets.getRequest(), "系统登录");
			return info;
		} else {
			return null;
		}
	}
	
	@Override
	protected void checkPermission(Permission permission, AuthorizationInfo info) {
		authorizationValidate(permission);
		super.checkPermission(permission, info);
	}
	
	@Override
	protected boolean[] isPermitted(List<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
        		authorizationValidate(permission);
            }
        }
		return super.isPermitted(permissions, info);
	}
	
	@Override
	public boolean isPermitted(PrincipalCollection principals, Permission permission) {
		authorizationValidate(permission);
		return super.isPermitted(principals, permission);
	}
	
	@Override
	protected boolean isPermittedAll(Collection<Permission> permissions, AuthorizationInfo info) {
		if (permissions != null && !permissions.isEmpty()) {
            for (Permission permission : permissions) {
            	authorizationValidate(permission);
            }
        }
		return super.isPermittedAll(permissions, info);
	}
	
	/**
	 * 授权验证方法
	 * @param permission
	 */
	private void authorizationValidate(Permission permission){
		// 模块授权预留接口
	}
	
	/**
	 * 设定密码校验的Hash算法与迭代次数
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(sysUserService.HASH_ALGORITHM);
		matcher.setHashIterations(sysUserService.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}
	
//	/**
//	 * 清空用户关联权限认证，待下次使用时重新加载
//	 */
//	public void clearCachedAuthorizationInfo(Principal principal) {
//		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
//		clearCachedAuthorizationInfo(principals);
//	}

	/**
	 * 清空所有关联认证
	 * @Deprecated 不需要清空，授权缓存保存到session中
	 */
	@Deprecated
	public void clearAllCachedAuthorizationInfo() {
//		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
//		if (cache != null) {
//			for (Object key : cache.keys()) {
//				cache.remove(key);
//			}
//		}
	}

	
	

}
