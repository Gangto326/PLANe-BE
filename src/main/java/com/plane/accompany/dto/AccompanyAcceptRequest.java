package com.plane.accompany.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AccompanyAcceptRequest {
	
	@NotNull
	private Long applyId;
	
	
	@Pattern(regexp = "^(일반|동행원)$", message = "역할은 일반, 동행원 중 하나여야 합니다")
	private String role;
	
	
	@Pattern(regexp = "^(거절|수락)$", message = "요청은 거절, 수락 중 하나여야 합니다")
	private String status;
	

	public AccompanyAcceptRequest() {}


	public Long getApplyId() {
		return applyId;
	}


	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	
}
