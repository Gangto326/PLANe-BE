package com.plane.common.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {	
	
	// 클라이언트 요청 오류
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "해당 요청에 맞는 데이터를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다."),
    INVALID_CODE(HttpStatus.BAD_REQUEST, "잘못된 코드입니다."),
    
    // 비즈니스 서비스 오류
    DUPLICATE_USER(HttpStatus.CONFLICT, "이미 존재하는 사용자입니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    
    // 서버 오류
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "데이터베이스 오류가 발생했습니다."),
    EMAIL_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 발송 중 문제가 발생했습니다."),
    
	VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR"),
	TOKEN_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "토큰 삭제중 오류가 발생했습니다.");

	
    private final HttpStatus status;
    private final String message;

    
    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }


	public HttpStatus getStatus() {
		return status;
	}


	public String getMessage() {
		return message;
	}

	
}
