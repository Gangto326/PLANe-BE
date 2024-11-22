package com.plane.afterTrip.dto;

import java.util.List;

public class AfterTripResponse {
	
	private Long afterTripId;
	private Long tripId;
	private Integer tripDay;
	private String content;
	private List<AfterPicResponse> files;
	
	
	public AfterTripResponse() {}


	public Long getAfterTripId() {
		return afterTripId;
	}


	public void setAfterTripId(Long afterTripId) {
		this.afterTripId = afterTripId;
	}


	public Long getTripId() {
		return tripId;
	}


	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}


	public Integer getTripDay() {
		return tripDay;
	}


	public void setTripDay(Integer tripDay) {
		this.tripDay = tripDay;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public List<AfterPicResponse> getFiles() {
		return files;
	}


	public void setFiles(List<AfterPicResponse> files) {
		this.files = files;
	}
	
}
