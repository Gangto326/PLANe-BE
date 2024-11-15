package com.plane.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CommentReportRequest {
	
	@NotNull(message = "댓글 번호는 필수입니다.")
	private Integer commentId;
	
	
	@NotNull(message = "신고 아이디는 필수입니다.")
	private Integer reportId;
	
	
	@NotBlank(message = "내용은 필수입니다.")
	@Size(min = 1, max = 255, message = "내용은 1~255자 사이여야 합니다.")
	private String details;


	public CommentReportRequest() {}


	public Integer getCommentId() {
		return commentId;
	}


	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}


	public Integer getReportId() {
		return reportId;
	}


	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}


	public String getDetails() {
		return details;
	}


	public void setDetails(String details) {
		this.details = details;
	}
	
}
