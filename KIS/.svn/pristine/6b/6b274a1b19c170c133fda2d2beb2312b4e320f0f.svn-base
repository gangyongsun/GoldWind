package cn.com.goldwind.kis.utils;

import org.apache.log4j.Logger;

/**
 * Log输出封装
 * 
 */
public class LoggerUtils {
	/**
	 * 是否开启Debug
	 */
	public static boolean isDebug = Logger.getLogger(LoggerUtils.class).isDebugEnabled();
	/**
	 * 是否开启Info
	 */
	public static boolean isInfo = Logger.getLogger(LoggerUtils.class).isInfoEnabled();

	/**
	 * 无格式化Debug 输出
	 * 
	 * @param clazz   目标.Class
	 * @param message 输出信息
	 */
	public static void debug(Class<? extends Object> clazz, String message) {
		if (isDebug) {
			Logger logger = Logger.getLogger(clazz);
			logger.debug(message);
		}
	}

	/**
	 * 格式化Debug 输出
	 * 
	 * @param clazz     目标.Class
	 * @param fmtString 输出信息key
	 * @param value     输出信息value
	 */
	public static void fmtDebug(Class<? extends Object> clazz, String fmtString, Object... value) {
		if (isDebug && !StringUtils.isBlank(fmtString) && null != value && value.length != 0) {
			fmtString = String.format(fmtString, value);
			debug(clazz, fmtString);
		}
	}

	/**
	 * Info 输出
	 * 
	 * @param clazz   目标.Class
	 * @param message 输出信息
	 */
	public static void info(Class<? extends Object> clazz, String message) {
		if (isInfo) {
			Logger logger = Logger.getLogger(clazz);
			logger.info(message);
		}
	}

	/**
	 * 格式化info 输出
	 * 
	 * @param clazz     目标.Class
	 * @param fmtString 输出信息key
	 * @param value     输出信息value
	 */
	public static void fmtInfo(Class<? extends Object> clazz, String fmtString, Object... value) {
		if (isInfo && !StringUtils.isBlank(fmtString) && null != value && value.length != 0) {
			fmtString = String.format(fmtString, value);
			info(clazz, fmtString);
		}
	}

	/**
	 * 有异常Error 输出
	 * 
	 * @param clazz   目标.Class
	 * @param message 输出信息
	 * @param e       异常类
	 */
	public static void error(Class<? extends Object> clazz, String message, Exception e) {
		Logger logger = Logger.getLogger(clazz);
		if (null == e) {
			logger.error(message);
		} else {
			logger.error(message, e);
		}
	}

	/**
	 * 无异常Error 输出
	 * 
	 * @param clazz   目标.Class
	 * @param message 输出信息
	 */
	public static void error(Class<? extends Object> clazz, String message) {
		error(clazz, message, null);
	}

	/**
	 * 有异常填充值输出
	 * 
	 * @param clazz     目标.Class
	 * @param fmtString 输出信息key
	 * @param e         异常类
	 * @param value     输出信息value
	 */
	public static void fmtError(Class<? extends Object> clazz, Exception e, String fmtString, Object... value) {
		if (!StringUtils.isBlank(fmtString) && null != value && value.length != 0) {
			fmtString = String.format(fmtString, value);
		}
		error(clazz, fmtString, e);
	}

	/**
	 * 无异常填充值输出
	 * 
	 * @param clazz     目标.Class
	 * @param fmtString 输出信息key
	 * @param value     输出信息value
	 */
	public static void fmtError(Class<? extends Object> clazz, String fmtString, Object... value) {
		if (!StringUtils.isBlank(fmtString) && null != value && value.length != 0) {
			fmtString = String.format(fmtString, value);
		}
		error(clazz, fmtString);
	}

}
