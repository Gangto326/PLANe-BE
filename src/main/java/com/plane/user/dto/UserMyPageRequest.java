package com.plane.user.dto;

import java.util.List;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserMyPageRequest {

	@NotBlank(message = "아이디는 필수입니다")
	@Size(min = 4, max = 20, message = "아이디는 4~20자 사이여야 합니다")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영문과 숫자만 가능합니다")
	private String userId;
	
	
	@NotBlank(message = "닉네임은 필수입니다")
	@Size(max = 30, message = "닉네임은 30자를 초과할 수 없습니다")
	@Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,30}$", message = "닉네임은 2-30자의 영문, 숫자, 한글만 가능합니다")
	private String nickName;
	
	
	@URL(message = "올바른 URL 형식이 아닙니다")
	private String profileUrl;
	
	
	@Size(max = 500, message = "자기소개는 500자를 초과할 수 없습니다")
	private String introduce;
	
	private boolean isPublic;
	
	@Size(max = 3, message = "여행 스타일은 최대 3개까지 선택 가능합니다")
	private List<Integer> tripStyle;
	
	@Size(max = 3, message = "여행 테마는 최대 3개까지 선택 가능합니다")
	private List<Integer> tripThema;
	
	
	public UserMyPageRequest() {}

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public List<Integer> getTripStyle() {
		return tripStyle;
	}

	public void setTripStyle(List<Integer> tripStyle) {
		this.tripStyle = tripStyle;
	}

	public List<Integer> getTripThema() {
		return tripThema;
	}

	public void setTripThema(List<Integer> tripThema) {
		this.tripThema = tripThema;
	}
		
}
