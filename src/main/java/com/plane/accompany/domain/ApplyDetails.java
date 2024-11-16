package com.plane.accompany.domain;

public class ApplyDetails {
	
	private Long id;
	private Integer askId;
	private Long applyId;
	private String details;
	
	
	public ApplyDetails() {}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Integer getAskId() {
		return askId;
	}


	public void setAskId(Integer askId) {
		this.askId = askId;
	}


	public Long getApplyId() {
		return applyId;
	}


	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}


	public String getDetails() {
		return details;
	}


	public void setDetails(String details) {
		this.details = details;
	}
	
}
