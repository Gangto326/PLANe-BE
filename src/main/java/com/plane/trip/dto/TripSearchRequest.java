package com.plane.trip.dto;

import com.plane.common.dto.PageRequest;

public class TripSearchRequest {
	
	private Integer regionId;
	private String state;
	private Integer accompanyNum;
	private Integer tripDays;
	private Boolean isLiked;
	private Boolean isReviewed;
	private String type;
	
	private PageRequest pageRequest;

	
	public TripSearchRequest() {}


	public Integer getRegionId() {
		return regionId;
	}


	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public Integer getAccompanyNum() {
		return accompanyNum;
	}


	public void setAccompanyNum(Integer accompanyNum) {
		this.accompanyNum = accompanyNum;
	}


	public Integer getTripDays() {
		return tripDays;
	}


	public void setTripDays(Integer tripDays) {
		this.tripDays = tripDays;
	}


	public Boolean getIsLiked() {
		return isLiked;
	}


	public void setIsLiked(Boolean isLiked) {
		this.isLiked = isLiked;
	}


	public Boolean getIsReviewed() {
		return isReviewed;
	}


	public void setIsReviewed(Boolean isReviewed) {
		this.isReviewed = isReviewed;
	}
	

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public PageRequest getPageRequest() {
		return pageRequest;
	}


	public void setPageRequest(PageRequest pageRequest) {
		this.pageRequest = pageRequest;
	}
	
}
