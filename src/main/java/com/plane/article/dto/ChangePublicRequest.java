package com.plane.article.dto;

public class ChangePublicRequest {
	
	private Long articleId;
	private boolean isPublic;
	
	
	public ChangePublicRequest() {}


	public Long getArticleId() {
		return articleId;
	}


	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}


	public boolean isPublic() {
		return isPublic;
	}


	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	
}
