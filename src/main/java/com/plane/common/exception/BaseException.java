package com.plane.common.exception;

public class BaseException extends RuntimeException {
	private final ErrorCode errorCode;
	private final String message;
	
	public BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.message = null;
    }

	public BaseException(ErrorCode errorCode, String message) {
		super(message);
        this.errorCode = errorCode;
        this.message = message;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}
	
}
