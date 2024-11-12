package com.plane.common.response;

import com.plane.common.exception.ErrorCode;

public class ErrorResponse {
	
	private final String code;
    private final String message;

    
    public ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
    }
    
    public static ErrorResponse of(ErrorCode errorCode) {
		return new ErrorResponse(errorCode);
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
    
}
