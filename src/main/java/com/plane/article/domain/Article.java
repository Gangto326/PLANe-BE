package com.plane.article.domain;

import java.time.LocalDateTime;

public class Article {
	
	private Integer articleId;
    private String authorId;
    private Integer tripId;
    private String articleType;
    private String title;
    private String content;
    private String articlePictureUrl;
    private Integer likeCount;
    private Integer viewCount;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    
    
	public Article() {}


	public Integer getArticleId() {
		return articleId;
	}


	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}


	public String getAuthorId() {
		return authorId;
	}


	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}


	public Integer getTripId() {
		return tripId;
	}


	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}


	public String getArticleType() {
		return articleType;
	}


	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getArticlePictureUrl() {
		return articlePictureUrl;
	}


	public void setArticlePictureUrl(String articlePictureUrl) {
		this.articlePictureUrl = articlePictureUrl;
	}


	public Integer getLikeCount() {
		return likeCount;
	}


	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}


	public Integer getViewCount() {
		return viewCount;
	}


	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}


	public LocalDateTime getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}


	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}


	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}
	
}
