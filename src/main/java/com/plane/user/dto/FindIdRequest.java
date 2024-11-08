package com.plane.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public class FindIdRequest {

	@Pattern(regexp = "^01[016789][0-9]{7,8}$",
            message = "올바른 휴대폰 번호 형식이 아닙니다")
	private String phone;
	
	
	@Email(message = "올바른 이메일 형식이 아닙니다")
	private String email;


	public FindIdRequest() {}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
}
