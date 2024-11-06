package com.plane.common.exception;

public class SystemException extends BaseException {
	
    public SystemException(ErrorCode errorCode) {
        super(errorCode);
    }
}