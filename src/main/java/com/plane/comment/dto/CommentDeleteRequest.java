package com.plane.comment.dto;

import jakarta.validation.constraints.NotNull;

public class CommentDeleteRequest {
	
	@NotNull(message = "댓글 번호는 필수입니다.")
	private Integer commentId;
	
	
	@NotNull(message = "글 번호는 필수입니다.")
	private Integer articleId;


	public CommentDeleteRequest() {}


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
	
}
