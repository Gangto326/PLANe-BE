package com.plane.common.exception;

public enum ErrorCode {
	USER_NOT_FOUND("USER_001", "사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD("USER_002", "잘못된 비밀번호입니다."),
    DUPLICATE_USER("USER_003", "이미 존재하는 사용자입니다.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
