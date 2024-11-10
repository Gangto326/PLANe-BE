package com.plane.common.exception.custom;

import com.plane.common.exception.BusinessException;
import com.plane.common.exception.ErrorCode;

public class ArticleNotFoundException extends BusinessException {

	public ArticleNotFoundException(String message) {
		super(ErrorCode.NOT_FOUND, message);
	}
}
