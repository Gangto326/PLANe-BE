package com.plane.comment.domain;

import java.time.LocalDateTime;

public class Comment {
	
	private Integer commentId;
	private Integer articleId;
	private String authorId;
	private Integer parents;
	private String commentContents;
	private String status;
	private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    
    
	public Comment() {}


	public Integer getCommentId() {
		return commentId;
	}


	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}


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


	public Integer getParents() {
		return parents;
	}


	public void setParents(Integer parents) {
		this.parents = parents;
	}


	public String getCommentContents() {
		return commentContents;
	}


	public void setCommentContents(String commentContents) {
		this.commentContents = commentContents;
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


	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}


	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}
	
}
