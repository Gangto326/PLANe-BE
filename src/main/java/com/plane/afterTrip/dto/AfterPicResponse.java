package com.plane.afterTrip.dto;

public class AfterPicResponse {

	private Long id;
    private String afterPictureUrl;
    private String originalFilename;
    
    
	public AfterPicResponse() {}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	
	public String getAfterPictureUrl() {
		return afterPictureUrl;
	}


	public void setAfterPictureUrl(String afterPictureUrl) {
		this.afterPictureUrl = afterPictureUrl;
	}


	public String getOriginalFilename() {
		return originalFilename;
	}


	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}
	
}
