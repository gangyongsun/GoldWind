package com.sojson.common.utils;

/**
 * 文件操作公共类
 * 
 * @author yonggang
 *
 */
public class FileUtil {


	/**
	 * 判断文件夹是否以斜杠结尾，没有加入斜杠
	 * 
	 * @param filePath
	 * @return
	 */
	public static String setPathEndWithSlash(String filePath) {
		if (!isDirectoryEndWithSlash(filePath)) {
			filePath = filePath + "/";
		}
		return filePath;
	}

	/**
	 * 判断目录是否以斜杠结尾
	 * 
	 * @param path
	 *            目录名
	 * @return
	 */
	public static boolean isDirectoryEndWithSlash(String path) {
		return path.endsWith("/") || path.endsWith("\\");
	}

}