package cn.com.goldwind.kis.shiro.session;

import java.util.List;
import java.util.Map;

import org.apache.shiro.subject.SimplePrincipalCollection;

import cn.com.goldwind.kis.shiro.bo.UserOnlineBo;

public interface SessionService {

	public List<UserOnlineBo> getAllUsers();

	public UserOnlineBo getSession(String sessionId);

	public Map<String, Object> changeSessionStatus(Boolean status, String sessionIds);

	public List<SimplePrincipalCollection> getSimplePrincipalCollectionByUserId(Integer... userIds);

	public void forbidUserById(Integer id, Integer userEnable);
}
