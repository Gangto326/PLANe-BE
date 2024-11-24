package com.plane.trip.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Plane {
	
	private Long tripId;
	private String userId;
	private Integer regionId;
	private String tripName;
	private LocalDate departureDate;
	private LocalDate arrivedDate;
	private String state;
	private Integer accompanyNum;
	private Integer tripDays;
	private boolean isLiked;
	private boolean isReviewed;
	private LocalDateTime deletedDate;
	
	
	public Plane() {}


	public Long getTripId() {
		return tripId;
	}


	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
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


	public LocalDate getDepartureDate() {
		return departureDate;
	}


	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}


	public LocalDate getArrivedDate() {
		return arrivedDate;
	}


	public void setArrivedDate(LocalDate arrivedDate) {
		this.arrivedDate = arrivedDate;
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


	public boolean isLiked() {
		return isLiked;
	}


	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}


	public boolean isReviewed() {
		return isReviewed;
	}


	public void setReviewed(boolean isReviewed) {
		this.isReviewed = isReviewed;
	}


	public LocalDateTime getDeletedDate() {
		return deletedDate;
	}


	public void setDeletedDate(LocalDateTime deletedDate) {
		this.deletedDate = deletedDate;
	}
	
}
