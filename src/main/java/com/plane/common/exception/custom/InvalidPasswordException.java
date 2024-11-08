package com.plane.common.exception.custom;

import com.plane.common.exception.ErrorCode;
import com.plane.common.exception.InvalidRequestException;

public class InvalidPasswordException extends InvalidRequestException {

	public InvalidPasswordException(String message) {
		super(ErrorCode.INVALID_PASSWORD, message);
	}
	
}
