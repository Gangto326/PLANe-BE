package com.plane.accompany.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class AccompanyUpdateRequest {

	@NotNull(message = "동행ID는 필수입니다.")
	private Long applyId;
	
	
	@NotEmpty(message = "동행 신청 내용은 필수입니다.")
	private List<AccompanyDetailRequest> accompanyDetailList;


	public AccompanyUpdateRequest() {}


	public Long getApplyId() {
		return applyId;
	}


	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}


	public List<AccompanyDetailRequest> getAccompanyDetailList() {
		return accompanyDetailList;
	}


	public void setAccompanyDetailList(List<AccompanyDetailRequest> accompanyDetailList) {
		this.accompanyDetailList = accompanyDetailList;
	}
	
}
