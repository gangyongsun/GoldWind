package cn.com.goldwind.kis.shiro.bo;

import java.io.Serializable;

import cn.com.goldwind.kis.shiro.domain.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限分配 查询列表BO
 * 
 * @author alvin
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleResourceAllocationBo extends SysRole implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 权限Name列转行，以,分割
	 */
	private String resourceNames;
	
	/**
	 * 权限Id列转行，以‘,’分割
	 */
	private String resourceIds;
	
}
