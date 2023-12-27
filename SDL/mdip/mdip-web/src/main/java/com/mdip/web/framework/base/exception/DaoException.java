package com.mdip.web.framework.base.exception;

public class DaoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DaoException() {
	}

	public DaoException(String msg) {
		super(msg);
	}
}