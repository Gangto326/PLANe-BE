package com.plane.common.exception;

public class DuplicatePhoneException extends InvalidRequestException {

	public DuplicatePhoneException(String message) {
		super(ErrorCode.DUPLICATE_USER);

	}
	
}
