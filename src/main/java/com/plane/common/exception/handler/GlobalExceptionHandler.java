package com.plane.common.exception.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.plane.common.exception.BusinessException;
import com.plane.common.exception.ErrorCode;
import com.plane.common.exception.InvalidRequestException;
import com.plane.common.exception.SystemException;
import com.plane.common.response.ApiResponse;
import com.plane.common.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    // 클라이언트 요청 오류 처리
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleInvalidRequestException(InvalidRequestException e) {
    	
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ApiResponse.error(e.getErrorCode(), e.getMessage()));
    }
    
    
    // 비즈니스 로직 오류 처리
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleBusinessException(
            BusinessException e) {
    	
    	return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ApiResponse.error(e.getErrorCode(), e.getMessage()));
    }
    
    
    // 서버 오류 처리
    @ExceptionHandler(SystemException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleSystemException(
            SystemException e) {

    	return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(ApiResponse.error(e.getErrorCode(), e.getMessage()));
    }
    
    
    // Validation 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleValidationException(
            MethodArgumentNotValidException e) {
    	
    	List<String> errorMessages = new ArrayList<>();
    	
    	// 에러 목록 가져오기
    	List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
    	
    	for (FieldError error: fieldErrors) {
    	    String message = error.getField() + ": " + error.getDefaultMessage();
    	    errorMessages.add(message);
    	}
    	
    	String errorMessage = String.join(", ", errorMessages);
    	
    	return ResponseEntity
                .status(1000)
                .body(ApiResponse.error(ErrorCode.VALIDATION_ERROR, errorMessage));
    }
}