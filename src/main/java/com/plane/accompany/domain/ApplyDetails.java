package com.plane.accompany.domain;

import java.time.LocalDateTime;

public class ApplyDetails {
	
	private Long id;
	private Long applyId;
	private Integer askId;
	private String details;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;
	
	
	public ApplyDetails() {}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getApplyId() {
		return applyId;
	}


	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}


	public Integer getAskId() {
		return askId;
	}


	public void setAskId(Integer askId) {
		this.askId = askId;
	}


	public String getDetails() {
		return details;
	}


	public void setDetails(String details) {
		this.details = details;
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
