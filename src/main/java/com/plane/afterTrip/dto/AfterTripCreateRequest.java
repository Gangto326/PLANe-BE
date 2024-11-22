package com.plane.afterTrip.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public class AfterTripCreateRequest {
	
	@NotNull(message = "여행 번호는 필수입니다.")
	private Long tripId;

	private List<TripDayDto> tripDayDtoList;

	
	public AfterTripCreateRequest() {}

	
	public Long getTripId() {
		return tripId;
	}


	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}


	public List<TripDayDto> getTripDayDtoList() {
		return tripDayDtoList;
	}


	public void setTripDayDtoList(List<TripDayDto> tripDayDtoList) {
		this.tripDayDtoList = tripDayDtoList;
	}
	
}
