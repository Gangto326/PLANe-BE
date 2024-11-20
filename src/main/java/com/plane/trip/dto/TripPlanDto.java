package com.plane.trip.dto;

public class TripPlanDto {
	
	private Long tripId;
	private int tripDay;
	private int tripOrder;
	private String title;
	private String memo;
	private String address;
	private Double mapx;
	private Double mapy;
	
	
	public TripPlanDto() {}

	
	public Long getTripId() {
		return tripId;
	}

	
	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}


	public int getTripDay() {
		return tripDay;
	}


	public void setTripDay(int tripDay) {
		this.tripDay = tripDay;
	}


	public int getTripOrder() {
		return tripOrder;
	}


	public void setTripOrder(int tripOrder) {
		this.tripOrder = tripOrder;
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


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
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
	
}
