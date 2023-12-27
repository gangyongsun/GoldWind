package cn.com.goldwind.kis.shiro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import cn.com.goldwind.kis.mybatis.BaseMybatisDao;
import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.shiro.bo.RoleResourceAllocationBo;
import cn.com.goldwind.kis.shiro.bo.SysRoleBo;
import cn.com.goldwind.kis.shiro.dao.SysRoleMapper;
import cn.com.goldwind.kis.shiro.dao.SysUserRoleMapper;
import cn.com.goldwind.kis.shiro.domain.SysRole;
import cn.com.goldwind.kis.shiro.domain.SysUserRole;
import cn.com.goldwind.kis.shiro.service.RoleService;
import cn.com.goldwind.kis.shiro.token.manager.TokenManager;
import cn.com.goldwind.kis.utils.LoggerUtils;
import cn.com.goldwind.kis.utils.StringUtils;

@Service
public class RoleServiceImpl extends BaseMybatisDao<SysRoleMapper> implements RoleService {
	@Autowired
	private TokenManager tokenManager;

	@Autowired
	private SysRoleMapper roleMapper;

	@Autowired
	private SysUserRoleMapper userRoleMapper;

	@Override
	public int saveRole(SysRole role) {
		return roleMapper.insertSelective(role);
	}

	@Override
	public List<SysRoleBo> findRoleByUserId(int id) {
		return roleMapper.findRoleByUserId(id);
	}

	@Override
	public Set<String> findRoleNameByUserId(Integer userId) {
		return roleMapper.findRoleNameByUserId(userId);
	}

	@Override
	public Map<String, Object> addRole2User(int userId, String roleIds) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int count = 0;
		try {
			// 先删除原有的关系
			userRoleMapper.deleteUserRoleRelationshipByUserId(userId);
			// 如果roleIds有值就添加，roleIds没值象征着把这个用户（userId）所有角色取消
			if (StringUtils.isNotBlank(roleIds)) {
				String[] roleIdArray = null;
				if (StringUtils.contains(roleIds, ",")) {
					roleIdArray = roleIds.split(",");
				} else {
					roleIdArray = new String[] { roleIds };
				}
				// 添加新的
				for (String roleId : roleIdArray) {
					if (StringUtils.isNotBlank(roleId)) {
						SysUserRole userRole = new SysUserRole(userId, new Integer(roleId));
						count += userRoleMapper.insertSelective(userRole);
					}
				}
			}
			//清空用户的权限，迫使再次获取权限的时候，得重新加载
			tokenManager.clearUserAuthByUserId(userId);

			resultMap.put("status", 200);
			resultMap.put("message", "操作成功");
			resultMap.put("count", count);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", 200);
			resultMap.put("message", "操作失败，请重试！");
		}

		return resultMap;
	}

	@Override
	public Map<String, Object> deleteRoleByUserIds(String userIds) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String[] array1 = null;
			if (StringUtils.contains(userIds, ",")) {
				array1 = userIds.split(",");
			} else {
				array1 = new String[] { userIds };
			}
			Integer[] userIdArray = (Integer[]) ConvertUtils.convert(array1, Integer.class);
			userRoleMapper.deleteUserRoleRelationshipByUserIds(userIdArray);
			resultMap.put("status", 200);
			resultMap.put("message", "清空用户角色成功");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", 500);
			resultMap.put("message", "清空用户角色失败，请重试！");
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> deleteRoleByIds(String ids) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String resultMsg = "删除成功！";
		int status = 200;
		try {
			int count = 0;
			String[] idArray = new String[] {};
			if (StringUtils.contains(ids, ",")) {
				idArray = ids.split(",");
			} else {
				idArray = new String[] { ids };
			}

			for (String id : idArray) {
				if (!"1".equals(id)) {
					count += roleMapper.deleteByPrimaryKey(new Integer(id));
				} else {
					resultMsg += ",<系统管理员>不能删除！";
				}
			}
			resultMap.put("count", count);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据IDS删除用户出现错误，ids[%s]", ids);
			status = 500;
			resultMsg = "删除出现错误！";
		} finally {
			resultMap.put("status", status);
			resultMap.put("message", resultMsg);
		}
		return resultMap;
	}

	@Override
	public List<SysRole> findNowAllPermission() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", TokenManager.getUserId());
		return roleMapper.findNowAllPermission(map);
	}

	@Override
	public TableSplitResult<RoleResourceAllocationBo> findRoleAndResourceByPage(ModelMap modelMap, Integer pageNumber, Integer pageSize) {
		return super.findPage("findRoleAndResources", "findCount", modelMap, pageNumber, pageSize);
	}

	@Override
	public TableSplitResult<SysRole> findRoleByPage(ModelMap modelMap, Integer pageNumber, Integer pageSize) {
		return super.findPage(modelMap, pageNumber, pageSize);
	}

	@Override
	public int updateRole(SysRole role) {
		return roleMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public int clearUserRoleRelationshipByUserIds(Integer[] userIds) {
		return userRoleMapper.deleteUserRoleRelationshipByUserIds(userIds);
	}

}
