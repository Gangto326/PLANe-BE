package com.plane.common.exception.custom;

import com.plane.common.exception.ErrorCode;
import com.plane.common.exception.SystemException;

public class FileUploadException extends SystemException {
	
	public FileUploadException(String message) {
		super(ErrorCode.INTERNAL_ERROR, message);
	}
}
