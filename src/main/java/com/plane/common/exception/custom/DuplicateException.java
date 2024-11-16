package com.plane.common.exception.custom;

import com.plane.common.exception.ErrorCode;
import com.plane.common.exception.InvalidRequestException;

public class DuplicateException extends InvalidRequestException {

	public DuplicateException(String message) {
		super(ErrorCode.DUPLICATE_ERROR, message);
	}
}
