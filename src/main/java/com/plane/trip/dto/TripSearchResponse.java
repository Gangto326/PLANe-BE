package com.plane.trip.dto;

import java.time.LocalDate;
import java.util.List;

import com.plane.trip.domain.TripThema;

public class TripSearchResponse {
	
	private Long tripId;
	private Integer regionId;
	private String tripName;
    private LocalDate departureDate;
    private LocalDate arrivedDate;
    private String state;
    private int accompanyNum;
    private int tripDays;
    private boolean isLiked;
    private boolean isReviewed;
    private List<Integer> tripThema;
    private String thumbnailUrl;
    
    
	public TripSearchResponse() {}


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


	public int getAccompanyNum() {
		return accompanyNum;
	}


	public void setAccompanyNum(int accompanyNum) {
		this.accompanyNum = accompanyNum;
	}


	public int getTripDays() {
		return tripDays;
	}


	public void setTripDays(int tripDays) {
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


	public List<Integer> getTripThema() {
		return tripThema;
	}


	public void setTripThema(List<Integer> tripThema) {
		this.tripThema = tripThema;
	}


	public String getThumbnailUrl() {
		return thumbnailUrl;
	}


	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	
    
}
