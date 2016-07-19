package com.youku.rpc.exception;

public class TimeoutException extends RpcException {

	private static final long serialVersionUID = 8923850882664723073L;

	public TimeoutException() {
		super();
	}

	public TimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TimeoutException(String message, Throwable cause) {
		super(message, cause);
	}

	public TimeoutException(String message) {
		super(message);
	}

	public TimeoutException(Throwable cause) {
		super(cause);
	}
}
