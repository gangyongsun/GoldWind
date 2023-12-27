package cn.com.goldwind.kis.shiro.session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.goldwind.kis.shiro.bo.UserOnlineBo;
import cn.com.goldwind.kis.shiro.domain.SysUser;
import cn.com.goldwind.kis.utils.LoggerUtils;

@Service
public class SessionServiceImpl implements SessionService {
	public static final String SESSION_STATUS = "kis-online-status";

	@Autowired
	public SessionRepository sessionRepository;

	@Override
	public List<UserOnlineBo> getAllUsers() {
		Collection<Session> sessions = sessionRepository.getActiveSessions();
		List<UserOnlineBo> userOnlineList = new ArrayList<UserOnlineBo>();

		for (Session session : sessions) {
			UserOnlineBo userOnline = getSessionBo(session);
			if (null != userOnline) {
				userOnlineList.add(userOnline);
			}
		}
		return userOnlineList;
	}

	@Override
	public UserOnlineBo getSession(String sessionId) {
		return getSessionBo(sessionRepository.getSession(sessionId));
	}

	@Override
	public Map<String, Object> changeSessionStatus(Boolean status, String sessionIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String[] sessionIdArray = null;
			if (sessionIds.indexOf(",") == -1) {
				sessionIdArray = new String[] { sessionIds };
			} else {
				sessionIdArray = sessionIds.split(",");
			}
			for (String sessionId : sessionIdArray) {
				Session session = sessionRepository.getSession(sessionId);
				//TODO getSession拿不到
				SessionStatus sessionStatus = new SessionStatus();
				sessionStatus.setOnlineStatus(status);

				session.setAttribute(SESSION_STATUS, sessionStatus);
				sessionRepository.save(session);
			}
			map.put("status", 200);
			map.put("message", "状态改变成功！");
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtils.fmtError(getClass(), e, "改变Session状态错误，sessionId[%s]", sessionIds);
			map.put("status", 500);
			map.put("message", "状态改变失败，Session可能不存在，请刷新再试！");
		}
		return map;
	}

	@Override
	public List<SimplePrincipalCollection> getSimplePrincipalCollectionByUserId(Integer... userIds) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获取单个Session
	 * 
	 * @param session
	 * @return
	 */
	private UserOnlineBo getSessionBo(Session session) {
		UserOnlineBo userOnlineBo = null;
		// 获取session登录信息
		Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
		// 确保是 SimplePrincipalCollection对象
		if (null != obj && obj instanceof SimplePrincipalCollection) {
			SimplePrincipalCollection simplePrincipalCollection = (SimplePrincipalCollection) obj;
			/**
			 * 获取用户登录
			 * 
			 * @link SampleRealm.doGetAuthenticationInfo(...)方法中 return new
			 *       SimpleAuthenticationInfo(user,user.getPswd(), getName()); 的user 对象。
			 */
			obj = simplePrincipalCollection.getPrimaryPrincipal();

			if (null != obj && obj instanceof SysUser) {
				// 存储session + user 综合信息
				userOnlineBo = new UserOnlineBo((SysUser) obj);
				// 最后一次和系统交互的时间
				userOnlineBo.setLastAccess(session.getLastAccessTime());
				// 主机的ip地址
				userOnlineBo.setHost(session.getHost());
				// session ID
				userOnlineBo.setSessionId(session.getId().toString());
				// session最后一次与系统交互的时间
				userOnlineBo.setLastLoginTime(session.getLastAccessTime());
				// 会话到期 ttl(ms)
				userOnlineBo.setTimeout(session.getTimeout());
				// session创建时间
				userOnlineBo.setStartTime(session.getStartTimestamp());
				// 是否踢出
				SessionStatus sessionStatus = (SessionStatus) session.getAttribute(SESSION_STATUS);
				boolean status = Boolean.TRUE;
				if (null != sessionStatus) {
					status = sessionStatus.getOnlineStatus();
				}
				userOnlineBo.setSessionStatus(status);
			}
		}
		return userOnlineBo;
	}

	@Override
	public void forbidUserById(Integer id, Integer userEnable) {
		List<UserOnlineBo> userOnlineList = getAllUsers();
		if (null != userOnlineList) {
			for (UserOnlineBo userOnlineBo : userOnlineList) {
				Integer userId = userOnlineBo.getId();
				// 匹配用户ID
				if (null != userId && userId.equals(id)) {
					// 获取用户Session
					Session session = sessionRepository.getSession(userOnlineBo.getSessionId());
					// 标记用户Session
					SessionStatus sessionStatus = (SessionStatus) session.getAttribute(SESSION_STATUS);
					// 是否踢出 true:启用，false：禁用
					sessionStatus.setOnlineStatus(userEnable.intValue() == 1);
					// 更新Session
					sessionRepository.save(session);
				}
			}
		}
	}

}
