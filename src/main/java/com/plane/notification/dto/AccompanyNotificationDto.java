package com.plane.notification.dto;

import java.time.LocalDate;

public class AccompanyNotificationDto {
	
	private String userId;
	private Long tripId;
	private String tripName;
	private LocalDate arrivedDate;
	
	
	public AccompanyNotificationDto() {}
	
	
	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public Long getTripId() {
		return tripId;
	}


	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}


	public String getTripName() {
		return tripName;
	}


	public void setTripName(String tripName) {
		this.tripName = tripName;
	}


	public LocalDate getArrivedDate() {
		return arrivedDate;
	}


	public void setArrivedDate(LocalDate arrivedDate) {
		this.arrivedDate = arrivedDate;
	}
	
}
