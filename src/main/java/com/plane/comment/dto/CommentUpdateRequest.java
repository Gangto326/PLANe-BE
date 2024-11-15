package com.plane.comment.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CommentUpdateRequest {
	
	@NotNull(message = "댓글 번호는 필수입니다.")
	private Integer commentId;
	
	@NotNull(message = "글 번호는 필수입니다.")
	private Integer articleId;
	
	@NotBlank(message = "내용은 필수입니다.")
	@Size(min = 1, max = 255, message = "내용은 1~255자 사이여야 합니다.")
	private String commentContents;
	
	@Pattern(regexp = "^(공개|비공개)$", message = "상태는 공개, 비공개 중 하나여야 합니다.")
	private String status;

	
	public CommentUpdateRequest() {}

	
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
	
}
