package com.plane.common.exception;

public class UserNotFoundException extends BusinessException {

	public UserNotFoundException(String message) {
		super(ErrorCode.USER_NOT_FOUND);
	}
	
}
