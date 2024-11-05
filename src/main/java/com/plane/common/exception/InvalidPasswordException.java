package com.plane.common.exception;

public class InvalidPasswordException extends InvalidRequestException {

	public InvalidPasswordException(String message) {
		super(ErrorCode.INVALID_PASSWORD);
	}
	
}
