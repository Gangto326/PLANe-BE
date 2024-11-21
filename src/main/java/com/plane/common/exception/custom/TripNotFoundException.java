package com.plane.common.exception.custom;

import com.plane.common.exception.BusinessException;
import com.plane.common.exception.ErrorCode;

public class TripNotFoundException extends BusinessException {

	public TripNotFoundException(String message) {
		super(ErrorCode.NOT_FOUND, message);
	}
}
