package com.plane.accompany.domain;

import java.time.LocalDateTime;

public class AccompanyApply {
	
	private Long applyId;
	private Long articleId;
	private String userId;
	private boolean isOk;
	private String status;
	private LocalDateTime createdDate;
	
	
	public AccompanyApply() {}


	public Long getApplyId() {
		return applyId;
	}


	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}


	public Long getArticleId() {
		return articleId;
	}


	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public boolean isOk() {
		return isOk;
	}


	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public LocalDateTime getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

}
