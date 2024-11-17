package com.plane.comment.dto;

import jakarta.validation.constraints.NotNull;

public class CommentDeleteRequest {
	
	@NotNull(message = "댓글 번호는 필수입니다.")
	private Integer commentId;
	
	
	@NotNull(message = "글 번호는 필수입니다.")
	private Long articleId;


	public CommentDeleteRequest() {}


	public Integer getCommentId() {
		return commentId;
	}


	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}


	public Long getArticleId() {
		return articleId;
	}


	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}
	
}
