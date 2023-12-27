package cn.com.goldwind.kis.shiro.bo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.com.goldwind.kis.shiro.domain.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Session + User Bo
 * 
 * @author alvin
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserOnlineBo extends SysUser implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Session Id
	 */
	private String sessionId;

	/**
	 * Session Host
	 */
	private String host;

	/**
	 * Session创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date startTime;

	/**
	 * Session最后交互时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date lastAccess;

	/**
	 * Session timeout
	 */
	private long timeout;

	/**
	 * session 是否踢出
	 */
	private boolean sessionStatus = Boolean.TRUE;

	public UserOnlineBo(SysUser user) {
		super(user);
	}

}
