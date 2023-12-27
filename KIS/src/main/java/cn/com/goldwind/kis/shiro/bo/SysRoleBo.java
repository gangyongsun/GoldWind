package cn.com.goldwind.kis.shiro.bo;

import java.io.Serializable;

import cn.com.goldwind.kis.shiro.domain.SysRole;
import cn.com.goldwind.kis.utils.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleBo extends SysRole implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID (用String， 考虑多个ID，现在只有一个ID)
	 */
	private String userId;

	/**
	 * 是否勾选
	 */
	private String marker;

	public boolean isCheck() {
		return StringUtils.equals(userId, marker);
	}

}
