package com.plane.afterTrip.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AfterTripUpdateRequest {	
	
	@NotNull(message = "여행 번호는 필수입니다.")
	private Long tripId;
	
	@Size(max = 3, message = "여행 일은 최대 3일까지 가능합니다")
	private List<TripDayUpdateDto> tripDayDtoList;

	
	public AfterTripUpdateRequest() {}


	public Long getTripId() {
		return tripId;
	}


	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}


	public List<TripDayUpdateDto> getTripDayDtoList() {
		return tripDayDtoList;
	}


	public void setTripDayDtoList(List<TripDayUpdateDto> tripDayDtoList) {
		this.tripDayDtoList = tripDayDtoList;
	}
	
}
