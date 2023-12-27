package com.mdip.web.framework.base.util;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.UUID;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

public class IdGen implements SessionIdGenerator {
	private static SecureRandom random = new SecureRandom();

	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return Encodes.encodeBase62(randomBytes);
	}

	public String getNextId() {
		return uuid();
	}

	public Serializable generateId(Session session) {
		return uuid();
	}
}
