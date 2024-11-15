package com.plane.comment.dto;

import java.time.LocalDateTime;

public class CommentResponse {

	private Integer commentId;
	
	// authorId로 Users 테이블에서 탐색
	private String authorNickName;
	
	private Integer parents;
	private String commentContents;
	private String status;
	
	// 자기 자신인지 반환하는 bool
	private boolean isMine;
	
	// 볼 수 있는지 여부를 반환하는 bool
	private boolean isHidden;
	
	private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    
    
	public CommentResponse() {}


	public Integer getCommentId() {
		return commentId;
	}


	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}


	public String getAuthorNickName() {
		return authorNickName;
	}


	public void setAuthorNickName(String authorNickName) {
		this.authorNickName = authorNickName;
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


	public boolean isMine() {
		return isMine;
	}


	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}


	public boolean isHidden() {
		return isHidden;
	}


	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
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
