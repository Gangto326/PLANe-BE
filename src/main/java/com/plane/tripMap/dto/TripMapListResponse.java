package com.plane.tripMap.dto;

public class TripMapListResponse {
	
	private Long tripId;
	private Integer regionId;
	private Double mapx;
	private Double mapy;
	private String mapPictureUrl;
	private String mapContent;
	
	
	public TripMapListResponse() {}


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


	public String getMapPictureUrl() {
		return mapPictureUrl;
	}


	public void setMapPictureUrl(String mapPictureUrl) {
		this.mapPictureUrl = mapPictureUrl;
	}


	public String getMapContent() {
		return mapContent;
	}


	public void setMapContent(String mapContent) {
		this.mapContent = mapContent;
	}
	
}
