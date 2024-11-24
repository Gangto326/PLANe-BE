package com.plane.manner.dto;

import jakarta.validation.constraints.NotNull;

public class MannerTagRequest {
	
	@NotNull(message = "매너 ID는 필수입니다.")
	private Integer mannerTagId;
	
	@NotNull(message = "매너 점수는 필수입니다.")
	private Integer score;
	
	
	public MannerTagRequest() {}


	public Integer getMannerTagId() {
		return mannerTagId;
	}


	public void setMannerTagId(Integer mannerTagId) {
		this.mannerTagId = mannerTagId;
	}


	public Integer getScore() {
		return score;
	}


	public void setScore(Integer score) {
		this.score = score;
	}
	
}
