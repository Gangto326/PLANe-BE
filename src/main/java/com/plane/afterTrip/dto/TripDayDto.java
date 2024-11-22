package com.plane.afterTrip.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TripDayDto {
	
	private Long afterTripid;
	
	@NotNull(message = "여행 일차는 필수입니다.")
	private Integer tripDay;
	
	
	@NotBlank(message = "내용은 필수입니다.")
	private String content;
	
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
