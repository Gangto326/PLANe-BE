package com.plane.common.exception.custom;

import com.plane.common.exception.ErrorCode;
import com.plane.common.exception.SystemException;

public class SendNotificationException extends SystemException {
	
	public SendNotificationException(String message) {
		super(ErrorCode.INTERNAL_ERROR, message);
	}
}
