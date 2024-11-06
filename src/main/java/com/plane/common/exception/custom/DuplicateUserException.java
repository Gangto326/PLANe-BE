package com.plane.common.exception.custom;

import com.plane.common.exception.ErrorCode;
import com.plane.common.exception.InvalidRequestException;

public class DuplicateUserException extends InvalidRequestException {

	public DuplicateUserException(String message) {
		super(ErrorCode.DUPLICATE_USER);

	}
	
}
