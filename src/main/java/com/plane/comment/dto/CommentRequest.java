package com.plane.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CommentRequest {
	
	@NotNull(message = "글 번호는 필수입니다.")
	private Long articleId;
	
	private Integer parents;
	
	
	@NotBlank(message = "내용은 필수입니다.")
	@Size(min = 1, max = 255, message = "내용은 1~255자 사이여야 합니다.")
	private String commentContents;
	
	
	@Pattern(regexp = "^(공개|비공개)$", message = "상태는 공개, 비공개 중 하나여야 합니다.")
	private String status;


	public CommentRequest() {}


	public Long getArticleId() {
		return articleId;
	}


	public void setArticleId(Long articleId) {
		this.articleId = articleId;
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
	
}
