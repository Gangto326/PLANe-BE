package com.plane.common.exception.custom;

import com.plane.common.exception.ErrorCode;
import com.plane.common.exception.InvalidRequestException;

public class DuplicateReportException extends InvalidRequestException {

	public DuplicateReportException(String message) {
		super(ErrorCode.DUPLICATE_REPORT, message);
	}
}
