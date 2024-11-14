package com.plane.common.exception.custom;

import com.plane.common.exception.BusinessException;
import com.plane.common.exception.ErrorCode;

public class UnauthorizedException extends BusinessException {
	
	public UnauthorizedException(String message) {
		super(ErrorCode.FORBIDDEN, message);
	}
	
}
