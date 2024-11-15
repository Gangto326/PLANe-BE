package com.plane.common.exception.custom;

import com.plane.common.exception.ErrorCode;
import com.plane.common.exception.SystemException;

public class CreationFailedException extends SystemException {
	
	public CreationFailedException(String message) {
		super(ErrorCode.CREATION_FAILED, message);
	}
}
