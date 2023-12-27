package cn.com.goldwind.kis.shiro.token;

import java.util.Date;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.goldwind.kis.shiro.domain.SysUser;
import cn.com.goldwind.kis.shiro.service.ResourcesService;
import cn.com.goldwind.kis.shiro.service.RoleService;
import cn.com.goldwind.kis.shiro.service.UserService;
import cn.com.goldwind.kis.shiro.token.manager.TokenManager;

/**
 * shiro认证+授权:重写
 * 
 * @author alvin
 *
 */
@Component
public class SampleRealm extends AuthorizingRealm {
	@Autowired
	UserService userService;

	@Autowired
	ResourcesService resourcesService;

	@Autowired
	RoleService roleService;

	public SampleRealm() {
		super();
	}

	/**
	 * 认证信息，主要针对用户登录
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		ShiroToken token = (ShiroToken) authcToken;
		SysUser user = userService.login(token.getUsername(), token.getPswd());
		if (null == user) {
			throw new AccountException("帐号或密码不正确！");
		} else if (SysUser.LOGINDENY.equals(user.getUserEnable())) {
			// 如果用户的status为禁用,那么就抛出DisabledAccountException
			throw new DisabledAccountException("帐号已经禁止登录！");
		} else {
			// 更新最后登录时间
			user.setLastLoginTime(new Date());
			userService.updateUser(user);
		}
		return new SimpleAuthenticationInfo(user, user.getPassWord(), getName());
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Integer userId = TokenManager.getUserId();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		// 根据用户ID查询role，放入到Authorization里
		Set<String> roles = roleService.findRoleNameByUserId(userId);
		info.setRoles(roles);

		// 根据用户ID查询permission，放入到Authorization里
		Set<String> resourcesSet = resourcesService.findResourceByUserId(userId);
		info.setStringPermissions(resourcesSet);
		return info;
	}

	/**
	 * 清空当前用户权限信息
	 */
	public void clearCachedAuthorizationInfo() {
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 指定principalCollection 清除
	 */
	public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}

}
