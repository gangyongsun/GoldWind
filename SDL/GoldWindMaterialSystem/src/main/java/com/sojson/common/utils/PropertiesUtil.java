package com.sojson.common.utils;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 资源文件操作类
 * 
 * @author Bill (Alvin updated)
 * @date Aug 15, 2017
 */
public class PropertiesUtil {
	private static Map<String, String> configMap = new ConcurrentHashMap<String, String>();

	/**
	 * 根据key获取值
	 * 
	 * @param key
	 * @param fileName 配置文件名
	 * @return
	 */
	public static String getValueByKey(String key, String fileName) {
		try {
			Properties prop = ResourceLoader.getPropertiesObject(fileName);
			key = key.trim();
			/**
			 * 判断map中是否存在这个key，存在直接取，否则先放再取
			 */
			if (!configMap.containsKey(key)) {
				if (null != prop.getProperty(key)) {
					configMap.put(key, prop.getProperty(key));
				}
			} else if (null != prop.getProperty(key) && !configMap.get(key).equals(prop.getProperty(key))) {
				configMap.replace(key, prop.getProperty(key));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configMap.get(key);
	}

	/**
	 * 根据key获取值
	 * 
	 * @param key
	 * @param filePath
	 * @param fileName 配置文件名
	 * @return
	 */
	public static String getValueByKey(String key, String filePath, String fileName) {
		try {
			Properties prop = ResourceLoader.getPropertiesObject(filePath, fileName);
			key = key.trim();
			/**
			 * 判断map中是否存在这个key，存在直接取，否则先放再取
			 */
			if (!configMap.containsKey(key)) {
				if (null != prop.getProperty(key)) {
					configMap.put(key, prop.getProperty(key));
				}
			} else if (null != prop.getProperty(key) && !configMap.get(key).equals(prop.getProperty(key))) {
				configMap.replace(key, prop.getProperty(key));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return configMap.get(key);
	}

	/**
	 * 获取properties键值对列表
	 * 
	 * @param propertie
	 * @return
	 */
	public static Set<Entry<Object, Object>> getEntrySet(String filePath, String fileName) {
		Properties propertie = getAllParams(filePath, fileName);
		return propertie.entrySet();
	}

	public static Properties getAllParams(String filePath, String fileName) {
		return ResourceLoader.getPropertiesObject(filePath, fileName);
	}

}
