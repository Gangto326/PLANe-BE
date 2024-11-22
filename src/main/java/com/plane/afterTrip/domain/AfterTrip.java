package com.plane.afterTrip.domain;

public class AfterTrip {
	
	private Long afterTripId;
	private Long tripId;
	private int tripDay;
	private String content;
	
	
	public AfterTrip() {}


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


	public int getTripDay() {
		return tripDay;
	}


	public void setTripDay(int tripDay) {
		this.tripDay = tripDay;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
	
}
