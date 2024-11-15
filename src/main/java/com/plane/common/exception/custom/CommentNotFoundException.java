package com.plane.common.exception.custom;

import com.plane.common.exception.BusinessException;
import com.plane.common.exception.ErrorCode;

public class CommentNotFoundException extends BusinessException {

	public CommentNotFoundException(String message) {
		super(ErrorCode.NOT_FOUND, message);
	}
}
