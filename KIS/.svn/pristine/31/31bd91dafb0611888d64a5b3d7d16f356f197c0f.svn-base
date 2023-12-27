package cn.com.goldwind.kis.shiro.secutity;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.goldwind.kis.shiro.domain.SysUser;
import cn.com.goldwind.kis.shiro.service.RoleService;
import cn.com.goldwind.kis.shiro.service.UserService;

public class MyShiroRealm extends AuthorizingRealm {
	private static Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

	// 如果项目中用到了事物，@Autowired注解会使事物失效，可以自己用get方法获取值
	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	/**
	 * 认证信息.(身份验证) : Authentication用来验证用户身份
	 *
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		logger.info("---------------- 执行 Shiro 凭证认证 ----------------------");
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String name = token.getUsername();
		String password = String.valueOf(token.getPassword());

		// 根据token里的用户名密码创建用户
		SysUser user = new SysUser();
		user.setUserName(name);
		user.setPassWord(password);

		// 根据用户信息从数据库获取对应用户名密码的用户
		SysUser sysUser = userService.getUser(user);
		if (sysUser != null) {
			// 用户为禁用状态
			if (sysUser.getUserEnable() != 1) {
				throw new DisabledAccountException();
			}
			logger.info("---------------- Shiro 凭证认证成功 ----------------------");
			SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(sysUser, sysUser.getPassWord(), getName());
			return authenticationInfo;
		}
		throw new UnknownAccountException();
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.info("---------------- 执行 Shiro 权限获取 ---------------------");
		Object principal = principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		if (principal instanceof SysUser) {
			SysUser userLogin = (SysUser) principal;
			Set<String> roles = roleService.findRoleNameByUserId(userLogin.getId());
			authorizationInfo.addRoles(roles);

			Set<String> permissions = userService.findResourcesByUserId(userLogin.getId());
			authorizationInfo.addStringPermissions(permissions);
		}
		logger.info("---- 获取到以下权限 ----");
		logger.info(authorizationInfo.getStringPermissions().toString());
		logger.info("---------------- Shiro 权限获取成功 ----------------------");
		return authorizationInfo;
	}

}