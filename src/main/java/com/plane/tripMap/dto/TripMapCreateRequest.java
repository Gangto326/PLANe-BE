package com.plane.tripMap.dto;

import org.springframework.web.multipart.MultipartFile;

public class TripMapCreateRequest {
	
	private Long tripId;
	private Double mapx;
	private Double mapy;
	private MultipartFile file;
	private String mapContent;
	
	private String mapPictureUrl;
	
	
	public TripMapCreateRequest() {}


	public Long getTripId() {
		return tripId;
	}


	public void setTripId(Long tripId) {
		this.tripId = tripId;
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


	public MultipartFile getFile() {
		return file;
	}


	public void setFile(MultipartFile file) {
		this.file = file;
	}


	public String getMapContent() {
		return mapContent;
	}


	public void setMapContent(String mapContent) {
		this.mapContent = mapContent;
	}


	public String getMapPictureUrl() {
		return mapPictureUrl;
	}


	public void setMapPictureUrl(String mapPictureUrl) {
		this.mapPictureUrl = mapPictureUrl;
	}
	
}
