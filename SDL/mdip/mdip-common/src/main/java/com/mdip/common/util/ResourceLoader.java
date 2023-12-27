package com.mdip.common.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 资源文件加载器
 * 
 * @author Bill (Alvin updated)
 * @date Aug 15, 2017
 */
public final class ResourceLoader {
	private static Map<String, Properties> map = new ConcurrentHashMap<String, Properties>();

	/**
	 * 获取properties对象
	 * 
	 * @param fileName
	 *            配置文件名
	 * @return properties对象
	 */
	public static Properties getPropertiesObject(String fileName) {
		Properties properties = map.get(fileName);
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			if (null == properties) {
				is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
				isr = new InputStreamReader(is);
				br = new BufferedReader(isr);
				properties = new Properties();// 创建Properties对象
				properties.load(br);
				map.put(fileName, properties);// 放到map
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeQuietly(br, isr, is);
		}
		return properties;
	}

	/**
	 * 获取properties对象
	 * 
	 * @param filePath
	 *            配置文件路径
	 * @param fileName
	 *            配置文件名
	 * @return properties对象
	 */
	public static Properties getPropertiesObject(String filePath, String fileName) {
		filePath = FileUtil.setPathEndWithSlash(filePath);
		Properties properties = new Properties();
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			is = new FileInputStream(filePath + fileName);
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			properties.load(br);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeQuietly(br, isr, is);
		}
		return properties;
	}
}
