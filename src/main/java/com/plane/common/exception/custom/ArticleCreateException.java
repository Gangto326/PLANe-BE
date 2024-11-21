package com.plane.common.exception.custom;

import com.plane.common.exception.ErrorCode;
import com.plane.common.exception.SystemException;

public class ArticleCreateException extends SystemException {
	
	public ArticleCreateException(String message) {
		super(ErrorCode.INTERNAL_ERROR, message);
	}
}
