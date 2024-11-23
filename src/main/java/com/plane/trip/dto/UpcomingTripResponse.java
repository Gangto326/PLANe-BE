package com.plane.trip.dto;

import java.time.LocalDate;

public class UpcomingTripResponse {
	
	private Long dDay;
	private Long tripId;
	private Integer regionId;
	private String tripName;
	private String thumbnailUrl;
	private LocalDate departureDate;
	
	
	public UpcomingTripResponse() {}


	public Long getdDay() {
		return dDay;
	}


	public void setdDay(Long dDay) {
		this.dDay = dDay;
	}


	public Long getTripId() {
		return tripId;
	}


	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}


	public Integer getRegionId() {
		return regionId;
	}


	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}


	public String getTripName() {
		return tripName;
	}


	public void setTripName(String tripName) {
		this.tripName = tripName;
	}

	
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}


	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}


	public LocalDate getDepartureDate() {
		return departureDate;
	}


	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}
	
	
}
