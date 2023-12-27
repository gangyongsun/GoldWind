package com.mdip.web.framework.statuscode;

/**
 * 错误代码接口
 * 
 * @author Administrator
 *
 */
public interface IErrorCode {

	/**
	 * 获取错误代码
	 * 
	 * @return
	 */
	int getCode();

	/**
	 * 获取错误信息
	 * 
	 * @return
	 */
	String getMessage();
}
