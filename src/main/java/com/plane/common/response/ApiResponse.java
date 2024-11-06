package com.plane.common.response;

import com.plane.common.exception.ErrorCode;

public class ApiResponse<T> {
	private static final boolean SUCCESS_STATUS = true;
    private static final boolean ERROR_STATUS = false;
	
	private final boolean success;
    private final T data;
    private final String message;

    // 성공 응답용 정적 팩토리 메서드
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(SUCCESS_STATUS, data, null);
    }
    
    // 성공 응답 + 메시지
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(SUCCESS_STATUS, data, message);
    }

    // 에러 응답용 정적 팩토리 메서드
    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return new ApiResponse<>(ERROR_STATUS, (T) new ErrorResponse(errorCode) , errorCode.getMessage());
    }

    // 생성자는 private으로 설정하여 팩토리 메서드 사용 유도
    private ApiResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

	public boolean isSuccess() {
		return success;
	}

	public T getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}

}
