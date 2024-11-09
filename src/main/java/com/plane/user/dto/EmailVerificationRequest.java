package com.plane.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class EmailVerificationRequest {
	
	@Email(message = "올바른 이메일 형식이 아닙니다")
	private String email;


	public EmailVerificationRequest() {}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
}
