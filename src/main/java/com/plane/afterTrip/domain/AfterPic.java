package com.plane.afterTrip.domain;

public class AfterPic {

	private Long id;
	private Long afterTripId;
	private String afterPictureUrl;
	private String originalFilename;
	
	
	public AfterPic() {}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	
	public Long getAfterTripId() {
		return afterTripId;
	}


	public void setAfterTripId(Long afterTripId) {
		this.afterTripId = afterTripId;
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
