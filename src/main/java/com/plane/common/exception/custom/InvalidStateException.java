package com.plane.common.exception.custom;

import com.plane.common.exception.ErrorCode;
import com.plane.common.exception.InvalidRequestException;

public class InvalidStateException extends InvalidRequestException {

	public InvalidStateException(String message) {
		super(ErrorCode.INVALID_INPUT, message);
	}
	
}
