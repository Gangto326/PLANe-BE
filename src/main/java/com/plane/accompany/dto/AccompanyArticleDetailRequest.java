package com.plane.accompany.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AccompanyArticleDetailRequest {

	@NotNull
	private Long applyId;
	
	
	@Pattern(regexp = "^(RECEIVED|SENT)$", message = "타입은 RECEIVED, SENT 중 하나여야 합니다")
	private String type;


	public AccompanyArticleDetailRequest() {}


	public Long getApplyId() {
		return applyId;
	}


	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	
}
