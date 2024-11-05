package com.plane.user.dto;

import jakarta.validation.constraints.NotBlank;

public class UserLoginRequest {
	
	@NotBlank
	private String userId;
	
	@NotBlank
	private String password;
	
	
	public UserLoginRequest(String userId, String password) {
		super();
		this.userId = userId;
		this.password = password;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
}
