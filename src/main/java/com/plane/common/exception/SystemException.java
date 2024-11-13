package com.plane.common.exception;

public class SystemException extends BaseException {
	
    public SystemException(ErrorCode errorCode) {
        super(errorCode);
    }
    
    public SystemException(ErrorCode errorCode, String message) {
		super(errorCode, message);
	}
    
}