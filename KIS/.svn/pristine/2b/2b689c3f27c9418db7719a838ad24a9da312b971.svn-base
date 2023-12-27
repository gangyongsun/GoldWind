package cn.com.goldwind.kis.shiro.bo;

import java.io.Serializable;

import cn.com.goldwind.kis.shiro.domain.SysResources;
import cn.com.goldwind.kis.utils.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限选择
 * 
 * @author alvin
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysResourcesBo extends SysResources implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 是否勾选
	 */
	private String marker;

	/**
	 * role Id
	 */
	private String roleId;

	public boolean isCheck() {
		return StringUtils.equals(roleId, marker);
	}

}
