package com.plane.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class FindIdRequest {
	
	@Email(message = "올바른 이메일 형식이 아닙니다")
	private String email;
	
	
	@NotBlank
	private String verificationCode;


	public FindIdRequest() {
		super();
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getVerificationCode() {
		return verificationCode;
	}


	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	
}
