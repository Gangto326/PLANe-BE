package com.plane.trip.dto;

import org.locationtech.jts.geom.Point;

public class TripPlanDto {
	
	private Long tripId;
	private int tripDay;
	private int tripOrder;
	private String title;
	private String comment;
	private String address;
	private Point point;
	
	
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


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Point getPoint() {
		return point;
	}


	public void setPoint(Point point) {
		this.point = point;
	}
	
}
