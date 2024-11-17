package com.plane.common.exception.custom;

import com.plane.common.exception.BusinessException;
import com.plane.common.exception.ErrorCode;

public class RegistNotFoundException extends BusinessException {

	public RegistNotFoundException(String message) {
		super(ErrorCode.NOT_FOUND, message);
	}
}
