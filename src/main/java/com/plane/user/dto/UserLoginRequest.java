package com.plane.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserLoginRequest {
	
	@NotBlank(message = "아이디는 필수입니다")
	@Size(min = 4, max = 20, message = "아이디는 4~20자 사이여야 합니다")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영문과 숫자만 가능합니다")
	private String userId;
	
	
	@NotBlank(message = "비밀번호는 필수입니다")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "비밀번호는 8자 이상의 영문, 숫자, 특수문자 조합이어야 합니다")
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
