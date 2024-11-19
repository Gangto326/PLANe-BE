package com.plane.common.exception.custom;

import com.plane.common.exception.ErrorCode;
import com.plane.common.exception.SystemException;

public class FileDeleteException extends SystemException {
	
	public FileDeleteException(String message) {
		super(ErrorCode.INTERNAL_ERROR, message);
	}
}
