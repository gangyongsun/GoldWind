package cn.com.goldwind.kis.shiro.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import cn.com.goldwind.kis.shiro.domain.SysUser;

public class RequestUtils {

	/**
	 * 获取当前登录的用户，若用户未登录，则返回未登录 json
	 *
	 * @return
	 */
	public static SysUser currentLoginUser() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			Object principal = subject.getPrincipals().getPrimaryPrincipal();
			if (principal instanceof SysUser) {
				return (SysUser) principal;
			}
		}
		return null;
	}
	
}
