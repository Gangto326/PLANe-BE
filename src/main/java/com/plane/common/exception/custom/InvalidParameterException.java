package com.plane.common.exception.custom;

import com.plane.common.exception.ErrorCode;
import com.plane.common.exception.InvalidRequestException;

public class InvalidParameterException extends InvalidRequestException {
	
	public InvalidParameterException(String message) {
		super(ErrorCode.INVALID_INPUT, message);
	}
	
}
