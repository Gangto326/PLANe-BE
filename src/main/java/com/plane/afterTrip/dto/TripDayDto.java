package com.plane.afterTrip.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TripDayDto {
	
	private Long afterTripid;
	
	@NotNull(message = "여행 일차는 필수입니다.")
	private Integer tripDay;
	
	
	private String content;
	
	
	@Size(max = 5, message = "각 일차별 사진은 5개까지 가능합니다")
	private List<MultipartFile> files;
	

	public TripDayDto() {}


	public Long getAfterTripid() {
		return afterTripid;
	}

	
	public void setAfterTripid(Long afterTripid) {
		this.afterTripid = afterTripid;
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


	public List<MultipartFile> getFiles() {
		return files;
	}


	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
	
}
