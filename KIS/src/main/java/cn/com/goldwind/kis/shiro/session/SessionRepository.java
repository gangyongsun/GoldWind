package cn.com.goldwind.kis.shiro.session;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;

public interface SessionRepository {

	Collection<Session> getActiveSessions();

	Session getSession(Serializable sessionId);

	void save(Session session);

	void deleteSession(Serializable id);
}
