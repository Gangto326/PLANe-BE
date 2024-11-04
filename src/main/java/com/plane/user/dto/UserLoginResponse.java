package com.plane.user.dto;

public class UserLoginResponse {
	
	// session_token(실제 쿠키에 저장되는 무작위 토큰)으로 변경
	private String userId;
	

	public UserLoginResponse(String userId) {
		super();
		this.userId = userId;
	}

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
