package shiro.demo;

import org.junit.Test;

import com.sojson.common.utils.JerseyClient;
import com.sojson.common.utils.PropertiesUtil;
import com.sojson.core.shiro.cache.VCache;

public class TokenTest {

	@Test
	public void generateTokenTest() {
		String apiURL = PropertiesUtil.getValueByKey("API_TOKEN", "config.properties");
		String username = PropertiesUtil.getValueByKey("TOKEN_USERNAME", "config.properties");
		String password = PropertiesUtil.getValueByKey("TOKEN_PASSWORD", "config.properties");
		String sdltoken = JerseyClient.getToken(apiURL, username, password);
		System.out.println(sdltoken);
	}
	
	@Test
	public void jedisTest() {
		String apiURL = PropertiesUtil.getValueByKey("API_TOKEN", "config.properties");
		String username = PropertiesUtil.getValueByKey("TOKEN_USERNAME", "config.properties");
		String password = PropertiesUtil.getValueByKey("TOKEN_PASSWORD", "config.properties");
		String sdltoken = JerseyClient.getToken(apiURL, username, password);
		VCache.setex("token", sdltoken,10);
		System.out.println(VCache.get("token"));
	}
	
	@Test
	public void removeCachedToken() {
		VCache.delSetByKey("SDL_TOKEN");
	}
}
