package cn.com.goldwind.kis.shiro.session;

import java.io.Serializable;

import lombok.Data;

/**
 * Session 状态 VO
 * 
 * @author alvin
 *
 */
@Data
public class SessionStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 是否踢出 true:有效<br>
	 * false：踢出
	 */
	private Boolean onlineStatus = Boolean.TRUE;

	public Boolean isOnlineStatus() {
		return getOnlineStatus();
	}

}
