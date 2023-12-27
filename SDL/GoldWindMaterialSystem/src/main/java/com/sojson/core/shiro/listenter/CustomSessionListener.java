package com.sojson.core.shiro.listenter;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import com.sojson.core.shiro.session.ShiroSessionRepository;

import lombok.Data;

/**
 * shiro 会话监听
 * 
 * @author alvin
 *
 */
@Data
public class CustomSessionListener implements SessionListener {
	private ShiroSessionRepository shiroSessionRepository;

	/**
	 * 一个会话的生命周期开始
	 */
	@Override
	public void onStart(Session session) {
		// TODO
		System.out.println("session on start");
	}

	/**
	 * 一个会话的生命周期结束
	 */
	@Override
	public void onStop(Session session) {
		// TODO
		System.out.println("session on stop");
	}

	@Override
	public void onExpiration(Session session) {
		shiroSessionRepository.deleteSession(session.getId());
	}

}
