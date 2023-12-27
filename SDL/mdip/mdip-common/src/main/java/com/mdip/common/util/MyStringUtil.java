package com.mdip.common.util;

/**
 * string处理
 * 
 * @author yonggang
 *
 */
public class MyStringUtil {
	/**
	 * 判断where是否是以and结尾，如果是就要处理
	 * 
	 * @param where
	 * @return
	 */
	public static String finalWhere(String where) {
		String and = " and ";
		String finalWhere = "";
		if (where.toString().endsWith(and)) {
			finalWhere = where.toString().substring(0, where.toString().lastIndexOf(and));
		}else{
			finalWhere=where.toString();
		}
		return finalWhere;
	}
}
