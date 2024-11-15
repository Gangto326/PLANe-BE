package com.plane.common.exception.custom;

import com.plane.common.exception.ErrorCode;
import com.plane.common.exception.SystemException;

public class CommentUpdateException extends SystemException {
	
	public CommentUpdateException(String message) {
		super(ErrorCode.INTERNAL_ERROR, message);
	}
}
