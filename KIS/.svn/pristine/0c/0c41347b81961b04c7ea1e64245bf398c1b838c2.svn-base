package cn.com.goldwind.kis.shiro.utils;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.com.goldwind.kis.shiro.domain.SysResources;
import cn.com.goldwind.kis.shiro.domain.SysRole;
import cn.com.goldwind.kis.shiro.domain.SysUser;
import cn.com.goldwind.kis.utils.MathUtil;

public class UserManager {

	/**
	 * 加工密码，和登录一致
	 * <p>
	 * 密码为 userrname + '#' + pswd，然后MD5
	 * 
	 * @param user
	 * @return
	 */
	public static SysUser md5Pswd(SysUser user) {
		user.setPassWord(md5Password(user.getUserName(), user.getPassWord()));
		return user;
	}

	/**
	 * 字符串返回值
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static String md5Password(String username, String password) {
		password = String.format("%s#%s", username, password);
		password = MathUtil.getMD5(password);
		return password;
	}

	/**
	 * 把查询出来的roles 转换成bootstarp 的 tree数据
	 * 
	 * @param roles
	 * @return
	 */
	public static List<Map<String, Object>> toTreeData(List<SysRole> roles) {
		List<Map<String, Object>> resultData = new LinkedList<Map<String, Object>>();
		for (SysRole role : roles) {
			// 角色列表
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("text", role.getRoleDesc());// 名称
			map.put("href", "javascript:void(0)");// 链接
			List<SysResources> resourceList = role.getResources();
			map.put("tags", new Integer[] { resourceList.size() });// 显示子数据条数
			if (null != resourceList && resourceList.size() > 0) {
				List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
				// 权限列表
				for (SysResources resource : resourceList) {
					Map<String, Object> mapx = new LinkedHashMap<String, Object>();
					mapx.put("text", resource.getResourceName());// 权限名称
					mapx.put("href", resource.getResourceUrl());// 权限url
					// mapx.put("tags", "0");//没有下一级
					list.add(mapx);
				}
				map.put("nodes", list);
			}
			resultData.add(map);
		}
		return resultData;
	}

}
