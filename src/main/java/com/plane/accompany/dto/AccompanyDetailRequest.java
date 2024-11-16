package com.plane.accompany.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AccompanyDetailRequest {

	@NotNull(message = "질문 ID는 필수입니다.")
	private Integer askId;
	
	
	@NotBlank(message = "답변은 필수입니다.")
	private String answer;


	public AccompanyDetailRequest() {}


	public Integer getAskId() {
		return askId;
	}


	public void setAskId(Integer askId) {
		this.askId = askId;
	}


	public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
