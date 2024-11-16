package com.plane.accompany.dto;

public class AccompanyApplyDto {

	private String userId;
	private Long articleId;
	private Long applyId;
	
	
	public AccompanyApplyDto() {}


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


	public Long getApplyId() {
		return applyId;
	}


	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}
	
}
