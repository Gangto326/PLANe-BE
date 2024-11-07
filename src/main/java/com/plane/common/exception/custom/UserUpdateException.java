package com.plane.common.exception.custom;

import com.plane.common.exception.ErrorCode;
import com.plane.common.exception.SystemException;

public class UserUpdateException extends SystemException {
	
	public UserUpdateException(String message) {
		super(ErrorCode.INTERNAL_ERROR);
	}
	
}
