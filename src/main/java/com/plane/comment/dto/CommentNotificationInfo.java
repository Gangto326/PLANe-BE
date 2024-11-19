package com.plane.comment.dto;

public class CommentNotificationInfo {

	private String commentContents;
    private String authorId;
    private Long articleId;
    
    
	public CommentNotificationInfo() {}


	public String getCommentContents() {
		return commentContents;
	}

	
	public void setCommentContents(String commentContents) {
		this.commentContents = commentContents;
	}


	public String getAuthorId() {
		return authorId;
	}


	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}


	public Long getArticleId() {
		return articleId;
	}


	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
    
}
