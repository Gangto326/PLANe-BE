package com.plane.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class FindPasswordRequest {

	@NotBlank(message = "아이디는 필수입니다")
	@Size(min = 4, max = 20, message = "아이디는 4~20자 사이여야 합니다")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영문과 숫자만 가능합니다")
	private String userId;
	
	
	@Pattern(regexp = "^01[016789][0-9]{7,8}$",
            message = "올바른 휴대폰 번호 형식이 아닙니다")
	private String phone;
	
	
	@Email(message = "올바른 이메일 형식이 아닙니다")
	private String email;


	public FindPasswordRequest() {
		super();
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


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
