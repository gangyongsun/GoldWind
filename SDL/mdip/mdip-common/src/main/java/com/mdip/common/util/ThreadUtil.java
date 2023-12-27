package com.mdip.common.util;

public class ThreadUtil {

	/**
	 * 睡眠
	 * 
	 * @param second
	 *            时间(单位秒)
	 * @return wake状态
	 */
	public static boolean sleep(int second) {
		boolean wake = false;
		try {
			Thread.sleep(second * 1000);
			wake = true;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return wake;
	}
}
