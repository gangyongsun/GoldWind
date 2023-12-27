package com.sojson.core.shiro;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;

import com.sojson.common.utils.LoggerUtils;
import com.sojson.core.shiro.session.ShiroSessionRepository;

import lombok.Data;

/**
 * shiro Session操作
 * 
 * @author alvin
 *
 */
@Data
public class CustomShiroSessionDAO extends AbstractSessionDAO {
	private ShiroSessionRepository shiroSessionRepository;

	@Override
	public void update(Session session) throws UnknownSessionException {
		shiroSessionRepository.saveSession(session);
	}

	@Override
	public void delete(Session session) {
		if (session == null) {
			LoggerUtils.error(getClass(), "Session 不能为null");
			return;
		}
		Serializable id = session.getId();
		if (id != null) {
			shiroSessionRepository.deleteSession(id);
		}
	}

	@Override
	public Collection<Session> getActiveSessions() {
		return shiroSessionRepository.getAllSessions();
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);
		this.assignSessionId(session, sessionId);
		shiroSessionRepository.saveSession(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		return shiroSessionRepository.getSession(sessionId);
	}
}
