package com.plane.ai.dto;

import java.util.List;

import com.plane.trip.dto.CoordinateDto;

public class AiGenerateRequest {
	
	private String content;
	private List<CoordinateDto> coordinateDtoList;
	
	public AiGenerateRequest() {}

	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public List<CoordinateDto> getCoordinateDtoList() {
		return coordinateDtoList;
	}


	public void setCoordinateDtoList(List<CoordinateDto> coordinateDtoList) {
		this.coordinateDtoList = coordinateDtoList;
	}
	
	
}
