package com.plane.tripMap.dto;

public class TripMapDetailResponse {
	
	private Long tripId;
	private String tripName;
	private boolean isAuthor;
	private Double mapx;
	private Double mapy;
	private String url;
	
	
	public TripMapDetailResponse() {}


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


	public boolean isAuthor() {
		return isAuthor;
	}


	public void setAuthor(boolean isAuthor) {
		this.isAuthor = isAuthor;
	}


	public Double getMapx() {
		return mapx;
	}


	public void setMapx(Double mapx) {
		this.mapx = mapx;
	}


	public Double getMapy() {
		return mapy;
	}


	public void setMapy(Double mapy) {
		this.mapy = mapy;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}
	
}
