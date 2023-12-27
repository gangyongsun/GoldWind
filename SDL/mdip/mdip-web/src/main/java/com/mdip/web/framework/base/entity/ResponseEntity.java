package com.mdip.web.framework.base.entity;

import java.io.Serializable;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.mdip.web.framework.statuscode.AppEnvConsts;
import com.mdip.web.framework.statuscode.IErrorCode;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * ResponseBody注解返回的JSON对象类
 * 
 * @author Administrator
 *
 * @param <T>
 */
@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseEntity<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 0表示成功，>0表示失败，<0系统保留
	 */
	private int code = 0;

	/**
	 * 提示信息
	 */
	private String msg = "";

	/**
	 * 详细提示信息
	 */
	private String detailMsg = "";

	/**
	 * 返回的数据
	 */
	private T data = null;

	/**
	 * @param data
	 * @param <U>
	 * @return
	 */
	public static <U> ResponseEntity success(final U data) {
		return build(0, "", "", data);
	}

	/**
	 * @param code
	 * @param msg
	 * @return
	 */
	public static ResponseEntity failure(final int code, final String msg) {
		return failure(code, msg, "");
	}

	/**
	 * @param IErrorCode
	 * @return
	 */
	public static ResponseEntity failure(final IErrorCode IErrorCode) {
		return failure(IErrorCode, "");
	}

	/**
	 * @param IErrorCode
	 * @param ex
	 * @return
	 */
	public static ResponseEntity failure(final IErrorCode IErrorCode, final Throwable ex) {
		return failure(IErrorCode, AppEnvConsts.isProductionMode() ? "" : ExceptionUtils.getStackTrace(ex));
	}

	/**
	 * @param IErrorCode
	 * @param detailMsg
	 * @return
	 */
	public static ResponseEntity failure(final IErrorCode IErrorCode, final String detailMsg) {
		return failure(IErrorCode.getCode(), IErrorCode.getMessage(), detailMsg);
	}

	/**
	 * @param IErrorCode
	 * @param data
	 * @param <U>
	 * @return
	 */
	public static <U> ResponseEntity failure(final IErrorCode IErrorCode, final U data) {
		return build(IErrorCode.getCode(), IErrorCode.getMessage(), "", data);
	}

	/**
	 * @param msg
	 * @return
	 */
	public static ResponseEntity failure(final String msg) {
		return failure(-1, msg, "");
	}

	/**
	 * @param msg
	 * @param detailMsg
	 * @return
	 */
	public static ResponseEntity failure(final String msg, final String detailMsg) {
		return failure(-1, msg, detailMsg);
	}

	/**
	 * @param code
	 * @param msg
	 * @param detailMsg
	 * @return
	 */
	public static ResponseEntity failure(final int code, final String msg, final String detailMsg) {
		return build(code, msg, detailMsg, null);
	}

	/**
	 * @param code
	 * @param msg
	 * @param ex
	 * @return
	 */
	public static ResponseEntity failure(final int code, final String msg, final Throwable ex) {
		return build(code, msg, AppEnvConsts.isProductionMode() ? "" : ExceptionUtils.getStackTrace(ex), null);
	}

	/**
	 * @param code
	 * @param msg
	 * @param detailMsg
	 * @param data
	 * @param <U>
	 * @return
	 */
	public static <U> ResponseEntity build(final int code, final String msg, final String detailMsg, final U data) {
		return new ResponseEntity<>(code, msg, detailMsg, data);
	}
}
