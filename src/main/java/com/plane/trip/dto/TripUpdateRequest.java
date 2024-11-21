package com.plane.trip.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TripUpdateRequest {

	@NotNull(message = "여행 번호는 필수입니다.")
	private Long tripId;
	
	
	@NotBlank
	private String tripName;
	
	
	@NotNull(message = "출발일은 필수입니다.")
    @Future(message = "출발일은 현재 날짜 이후여야 합니다.")
	private LocalDate departureDate;
	
	
	@NotNull(message = "도착일은 필수입니다.")
    @Future(message = "도착일은 현재 날짜 이후여야 합니다.")
	private LocalDate arrivedDate;
	
	
	@Pattern(regexp = "^(임시저장|저장)$", message = "글 상태는 임시저장, 저장 중 하나여야 합니다")
	private String state;
	
	
	@Min(value = 1, message = "여행 인원은 1 이상이어야 합니다")
	private Long accompanyNum;
	
	
	@Min(value = 1, message = "여행 일수는 1 이상이어야 합니다")
	private Integer tripDays;
	
	
	@NotNull(message = "1일차 일정은 필수입니다.")
    @Size(min = 1, message = "1일차 일정에는 최소 1개 이상의 장소가 필요합니다.")
	private List<CoordinateDto> day1;
	
	private List<CoordinateDto> day2;
	
	private List<CoordinateDto> day3;
	
	
	@Size(max = 3, message = "여행 테마는 최대 3개까지 선택 가능합니다")
	private List<Integer> tripThema;


	public TripUpdateRequest() {}


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


	public LocalDate getDepartureDate() {
		return departureDate;
	}


	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}


	public LocalDate getArrivedDate() {
		return arrivedDate;
	}


	public void setArrivedDate(LocalDate arrivedDate) {
		this.arrivedDate = arrivedDate;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public Long getAccompanyNum() {
		return accompanyNum;
	}


	public void setAccompanyNum(Long accompanyNum) {
		this.accompanyNum = accompanyNum;
	}


	public Integer getTripDays() {
		return tripDays;
	}


	public void setTripDays(Integer tripDays) {
		this.tripDays = tripDays;
	}


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


	public List<Integer> getTripThema() {
		return tripThema;
	}


	public void setTripThema(List<Integer> tripThema) {
		this.tripThema = tripThema;
	}
	
}
