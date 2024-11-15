package com.plane.common.exception.custom;

import com.plane.common.exception.ErrorCode;
import com.plane.common.exception.SystemException;

public class CommentCreationFailedException extends SystemException {
	
	public CommentCreationFailedException(String message) {
		super(ErrorCode.COMMENT_CREATION_FAILED, message);
	}
}
