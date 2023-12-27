package com.mdip.web.framework.base.entity;

import java.io.Serializable;

import java.util.HashMap;

import com.mdip.web.framework.statuscode.StatusCode;

public class ResultEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 请求系统时服务状态信息代码 StutusCode
	 */
	public int status = 47;// 系统级别信息代码
	/**
	 * 请求系统时服务状态信息
	 */
	public String message = "正常";// 系统级别信息

	/**
	 * 存放 返回的结果，各种结果
	 */
	public Object result = new HashMap(); // 当前页结果集

	public int getStatus() {
		return status;
	}

	public void addRmg(String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages) {
			sb.append(message).append(messages.length > 1 ? "<br/>" : "");
		}
		this.add("rmg", sb.toString());
	}

	public void addRst(int status) {
		this.add("rst", status);
	}

	public void success(String rmg) {
		this.add("rst", 1);
		this.add("rmg", rmg);
	}

	public void failed(String rmg) {
		this.add("rst", 2);
		this.add("rmg", rmg);
	}

	public Object getResult() {
		return result;
	}

	public void setStatus(int status) {
		this.setMessage(StatusCode.getCodeInfo(status));// 根据状态码自动设置状态信息
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	private void setMessage(String message) {
		this.message = message;
	}

	public void add(String key, Object val) {// 将结果放List结果中
		((HashMap) result).put(key, val);
	}

	public void setResult(Object result) {// 将结果放List结果中
		this.result = result;
	}

}
