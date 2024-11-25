package com.plane.tripMap.dto;

public class TripMapListResponse {
	
	private Integer regionId;
	private String afterPictureUrl;
	
	
	public TripMapListResponse() {}


	public Integer getRegionId() {
		return regionId;
	}


	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}


	public String getAfterPictureUrl() {
		return afterPictureUrl;
	}


	public void setAfterPictureUrl(String afterPictureUrl) {
		this.afterPictureUrl = afterPictureUrl;
	}
	
	
}
