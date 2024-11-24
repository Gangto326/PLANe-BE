package com.plane.trip.dto;

import java.util.List;

public class TripMakeResponse {

	private List<CoordinateDto> day1;
	private List<CoordinateDto> day2;
	private List<CoordinateDto> day3;
	
	
	public TripMakeResponse() {}


	public List<CoordinateDto> getDay1() {
		return day1;
	}


	public void setDay1(List<CoordinateDto> day1) {
		this.day1 = day1;
	}


	public List<CoordinateDto> getDay2() {
		return day2;
	}


	public void setDay2(List<CoordinateDto> day2) {
		this.day2 = day2;
	}


	public List<CoordinateDto> getDay3() {
		return day3;
	}


	public void setDay3(List<CoordinateDto> day3) {
		this.day3 = day3;
	}
	
}
