package com.plane.accompany.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class AccompanyRegistRequest {
	
	@NotNull(message = "글 번호는 필수입니다.")
	private Long articleId;
	
	
	@NotEmpty(message = "동행 신청 내용은 필수입니다.")
	private List<AccompanyDetailRequest> accompanyDetailList;


	public AccompanyRegistRequest() {}


	public Long getArticleId() {
		return articleId;
	}


	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}


	public List<AccompanyDetailRequest> getAccompanyDetailList() {
		return accompanyDetailList;
	}


	public void setAccompanyDetailList(List<AccompanyDetailRequest> accompanyDetailList) {
		this.accompanyDetailList = accompanyDetailList;
	}
	
}
