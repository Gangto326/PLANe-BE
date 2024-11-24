package com.plane.accompany.dto;

import java.util.List;

public class AccompanyDetailResponse {
	
	private String userId;
	private Long articleId;
	private String nickName;
	private List<AccompanyDetailDto> detailList;
	private String status;
	
	
	public AccompanyDetailResponse() {}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	public Long getArticleId() {
		return articleId;
	}


	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public List<AccompanyDetailDto> getDetailList() {
		return detailList;
	}


	public void setDetailList(List<AccompanyDetailDto> detailList) {
		this.detailList = detailList;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	
}
