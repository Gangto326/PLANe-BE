package com.plane.common.exception;

public class InvalidRequestException extends BaseException {
	
    public InvalidRequestException(ErrorCode errorCode) {
        super(errorCode);
    }

}