package com.plane.common.exception.custom;

import com.plane.common.exception.ErrorCode;
import com.plane.common.exception.SystemException;

public class UpdateFailedException extends SystemException {
	
	public UpdateFailedException(String message) {
		super(ErrorCode.UPDATE_FAILED, message);
	}
}
