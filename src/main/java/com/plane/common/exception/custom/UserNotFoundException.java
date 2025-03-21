package com.plane.common.exception.custom;

import com.plane.common.exception.BusinessException;
import com.plane.common.exception.ErrorCode;

public class UserNotFoundException extends BusinessException {

	public UserNotFoundException(String message) {
		super(ErrorCode.NOT_FOUND, message);
	}
	
}
