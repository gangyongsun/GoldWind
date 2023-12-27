package cn.com.goldwind.kis.utils;

import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * Restful 客户端
 * 
 * @author alvin
 *
 */
public class JerseyClient {
	private static final String BASE_SERVER_URL = PropertiesUtil.getValueByKey("BASE_SERVER_URL", "config.properties");
	private static final int SUCCESS_STATUS = 200;

	public static MultivaluedMap<String, String> parseJSON2Map(String jsonStr) {
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		// JSON字符串转换为JSON对象
		JSONObject jsonObject = JSON.parseObject(jsonStr);
		for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
			queryParams.add(entry.getKey(), entry.getValue().toString());
		}
		return queryParams;
	}

	/**
	 * 获取用户登录token
	 * 
	 * @param apiURL
	 * @param username
	 * @param password
	 * @return
	 */
	public static String getToken(String apiURL, String username, String password) {
		/**
		 * ClientResponse对象代表了一个客户端收到的HTTP响应
		 */
		ClientResponse response = null;
		String token = "";
		try {
			/**
			 * 创建Client的实例
			 */
			Client client = Client.create();
			WebResource webResource = client.resource(BASE_SERVER_URL + apiURL);
			/**
			 * basic验证
			 */
			client.addFilter(new HTTPBasicAuthFilter(username, password));
			response = webResource.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);
			/**
			 * 请求返回的状态
			 */
			int status = response.getStatus();

			if (SUCCESS_STATUS == status) {
				/**
				 * 请求返回的数据就是token，但是不知道这个字符串为什么前后有“，所有通过substring去掉前后的引号
				 */
				token = response.getEntity(String.class).trim();
				String regexp = "\"";
				if (token.startsWith(regexp)) {
					token = token.substring(1, token.length());
				}
				if (token.endsWith(regexp)) {
					token = token.substring(0, token.length() - 1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				response.close();
				response = null;
			}
		}
		return token;
	}

	/**
	 * get方法 例如：consultation/recommend?startDate=201412030253&endDate=201412020253
	 * 
	 * @param apiURL
	 * @param param
	 * @return
	 */
	public static JSONObject getMethod(String apiURL, String token, String param) {
		JSONObject jsonObject = null;
		ClientResponse response = null;
		try {
			Client client = Client.create();
			WebResource resource = client.resource(BASE_SERVER_URL + apiURL);
			response = resource.queryParams(parseJSON2Map(param)).header("Authorization", "Bearer " + token).accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);

			int status = response.getStatus();
			String data = response.getEntity(String.class);
			if (SUCCESS_STATUS == status) {
				jsonObject = JSON.parseObject(data);
			} else {
				System.out.println("ERROR " + status + ":" + apiURL + " GET请求失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				response.close();
				response = null;
			}
		}
		return jsonObject;
	}

	/**
	 * post方法
	 *
	 * @param apiURL
	 * @param param
	 * @return
	 */
	public static JSONObject postMethod(String apiURL, String token, String param) {
		JSONObject jsonObject = null;
		ClientResponse response = null;
		try {
			Client client = Client.create();
			WebResource resource = client.resource(BASE_SERVER_URL + apiURL);
			response = resource.header("Authorization", "Bearer " + token).type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class, param);
			int status = response.getStatus();
			String data = response.getEntity(String.class);
			if (SUCCESS_STATUS == status) {
				jsonObject = JSON.parseObject(data);
			} else {
				System.out.println("ERROR " + status + ":" + apiURL + " GET请求失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				response.close();
				response = null;
			}
		}
		return jsonObject;
	}

}
