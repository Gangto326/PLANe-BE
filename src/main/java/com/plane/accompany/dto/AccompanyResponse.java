package com.plane.accompany.dto;

import java.time.LocalDateTime;

public class AccompanyResponse {
	
	private Long applyId;
	private Long articleId;
	private String title;
	private String nickName;
	private boolean isOk;
	private boolean isCheck;
	private LocalDateTime createdDate;
	
	
	public AccompanyResponse() {}


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


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public boolean isOk() {
		return isOk;
	}


	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	
	public boolean isCheck() {
		return isCheck;
	}


	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
}
