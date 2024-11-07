package com.plane.common.exception.custom;

import com.plane.common.exception.ErrorCode;
import com.plane.common.exception.InvalidRequestException;

public class EmailException extends InvalidRequestException {

	public EmailException(String message) {
		super(ErrorCode.EMAIL_SEND_ERROR);

	}
	
}
