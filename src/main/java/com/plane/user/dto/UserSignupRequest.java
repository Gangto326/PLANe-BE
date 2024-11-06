package com.plane.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// 꺽쇠 날리기 적용 XSS 검색
public class UserSignupRequest {
	
	@NotBlank(message = "아이디는 필수입니다")
	@Size(min = 4, max = 20, message = "아이디는 4~20자 사이여야 합니다")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영문과 숫자만 가능합니다")
	private String userId;
	
	
	@NotBlank(message = "비밀번호는 필수입니다")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "비밀번호는 8자 이상의 영문, 숫자, 특수문자 조합이어야 합니다")
	private String password;
	
	
	@NotBlank(message = "비밀번호 확인은 필수입니다")
	private String confirmPassword;
	
	
	@Pattern(regexp = "^01[016789][0-9]{7,8}$",
            message = "올바른 휴대폰 번호 형식이 아닙니다")
	private String phone;
	
	
	@Email(message = "올바른 이메일 형식이 아닙니다")
	private String email;
	
	
	@NotBlank(message = "닉네임은 필수입니다")
	private String nickName;
	
	private String hashedPassword;
	private String hashedPhone;
	
	
	public UserSignupRequest() {}
	
	public UserSignupRequest(String userId, String password, String confirmPassword, String nickName, String phone,
			String email) {
		super();
		this.userId = userId;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.nickName = nickName;
		this.phone = phone;
		this.email = email;
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
	
	
	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
	public String getNickName() {
		return nickName;
	}
	
	
	public void setNickName(String nickName) {
		this.nickName = nickName;
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


	public String getHashedPassword() {
		return hashedPassword;
	}


	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}


	public String getHashedPhone() {
		return hashedPhone;
	}


	public void setHashedPhone(String hashedPhone) {
		this.hashedPhone = hashedPhone;
	}
	
	
}
