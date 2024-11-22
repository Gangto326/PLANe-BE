package com.plane.trip.dto;

public class CoordinateDto {
	
	private Integer tripOrder;
	private String addr1;
	private String title;
	private String memo;
	private Double mapx;
	private Double mapy;
	private String url;
	
	
	public CoordinateDto() {}


	public Integer getTripOrder() {
		return tripOrder;
	}


	public void setTripOrder(Integer tripOrder) {
		this.tripOrder = tripOrder;
	}


	public String getAddr1() {
		return addr1;
	}


	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}
	

	public String getMemo() {
		return memo;
	}


	public void setMemo(String memo) {
		this.memo = memo;
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
