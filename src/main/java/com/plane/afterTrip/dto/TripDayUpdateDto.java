package com.plane.afterTrip.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TripDayUpdateDto {

	@NotNull(message = "여행 후기 ID는 필수입니다.")
	private Long afterTripId;
	
	@NotNull(message = "여행 일차는 필수입니다.")
	private Integer tripDay;
	
	private String content;
	
	@Size(max = 5, message = "각 일차별 사진은 5개까지 가능합니다.")
	private List<AfterPicRequest> files;

	
	public TripDayUpdateDto() {}


	public Long getAfterTripId() {
		return afterTripId;
	}


	public void setAfterTripId(Long afterTripId) {
		this.afterTripId = afterTripId;
	}


	public Integer getTripDay() {
		return tripDay;
	}


	public void setTripDay(Integer tripDay) {
		this.tripDay = tripDay;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public List<AfterPicRequest> getFiles() {
		return files;
	}


	public void setFiles(List<AfterPicRequest> files) {
		this.files = files;
	}
	
}
