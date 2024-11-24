package com.plane.manner.dto;

import java.util.List;

public class MannerDetailResponse {

	private Long articleId;
	private List<MannerUserResponse> userList;
	
	
	public MannerDetailResponse() {}


	public Long getArticleId() {
		return articleId;
	}


	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}


	public List<MannerUserResponse> getUserList() {
		return userList;
	}


	public void setUserList(List<MannerUserResponse> userList) {
		this.userList = userList;
	}
	
}
