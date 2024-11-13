package com.plane.common.exception.custom;

import com.plane.common.exception.ErrorCode;
import com.plane.common.exception.SystemException;

public class TokenCleanupException extends SystemException {
	
	public TokenCleanupException(String message) {
		super(ErrorCode.TOKEN_DELETE_ERROR, message);
	}
	
}