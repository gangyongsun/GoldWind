package cn.com.goldwind.kis.shiro.dao;

import java.util.List;
import java.util.Set;

import cn.com.goldwind.kis.shiro.bo.SysResourcesBo;
import cn.com.goldwind.kis.shiro.domain.SysResources;
import tk.mybatis.mapper.common.Mapper;

public interface SysResourcesMapper extends Mapper<SysResources> {
	/**
	 * 根据角色获取权限<br>
	 * marker:0,没有权限;<br>
	 * marker：非0(和up.id)一致表示有权限
	 * 
	 * @param id 角色ID
	 * @return 权限列表
	 */
	List<SysResourcesBo> findResourceByRoleId(Integer id);

	/**
	 * 根据用户ID获取权限名称Set集合
	 * 
	 * @param id 权限ID
	 * @return
	 */
	Set<String> findResourceNameByUserId(Integer id);

}