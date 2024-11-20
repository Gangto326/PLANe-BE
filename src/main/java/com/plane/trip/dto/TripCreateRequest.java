package com.plane.trip.dto;

import java.util.Date;
import java.util.List;

import com.plane.trip.domain.TripThema;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TripCreateRequest {
	
	private Long tripId;
	
	@NotNull(message = "지역 코드는 필수입니다.")
    @Min(value = 1, message = "올바르지 않은 지역 코드입니다.")
    @Max(value = 39, message = "올바르지 않은 지역 코드입니다.")
	private Integer regionId;
	
	
	@NotBlank
	private String tripName;
	
	
	@NotNull(message = "출발일은 필수입니다.")
    @Future(message = "출발일은 현재 날짜 이후여야 합니다.")
	private Date departureDate;
	
	
	@NotNull(message = "도착일은 필수입니다.")
    @Future(message = "도착일은 현재 날짜 이후여야 합니다.")
	private Date arrivedDate;
	
	
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
	private List<TripThema> tripThema;
	
	
	public TripCreateRequest() {}

	
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


	public String getTripName() {
		return tripName;
	}


	public void setTripName(String tripName) {
		this.tripName = tripName;
	}


	public Date getDepartureDate() {
		return departureDate;
	}


	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}


	public Date getArrivedDate() {
		return arrivedDate;
	}


	public void setArrivedDate(Date arrivedDate) {
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


	public List<TripThema> getTripThema() {
		return tripThema;
	}


	public void setTripThema(List<TripThema> tripThema) {
		this.tripThema = tripThema;
	}
	
}
