package com.plane.common.exception.custom;

import com.plane.common.exception.BusinessException;
import com.plane.common.exception.ErrorCode;

public class VerificationCodeException extends BusinessException {

	public VerificationCodeException(String message) {
		super(ErrorCode.INVALID_CODE, message);
	}
	
}
