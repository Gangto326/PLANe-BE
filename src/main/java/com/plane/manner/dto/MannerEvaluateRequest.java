package com.plane.manner.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class MannerEvaluateRequest {
	
	@NotNull(message = "여행 ID는 필수입니다.")
	private Long tripId;
	
	
	@NotBlank(message = "평가 대상자 ID는 필수입니다.")
	private String evaluatorId;
	
	
	@NotEmpty(message = "설문 내용은 필수입니다.")
	private List<MannerTagRequest> tagList;

	
	public MannerEvaluateRequest() {}
	
	
	public Long getTripId() {
		return tripId;
	}
	

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}


	public String getEvaluatorId() {
		return evaluatorId;
	}


	public void setEvaluatorId(String evaluatorId) {
		this.evaluatorId = evaluatorId;
	}


	public List<MannerTagRequest> getTagList() {
		return tagList;
	}


	public void setTagList(List<MannerTagRequest> tagList) {
		this.tagList = tagList;
	}
	
	
}
