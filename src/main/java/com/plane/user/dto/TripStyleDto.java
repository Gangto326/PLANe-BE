package com.plane.user.dto;

import jakarta.validation.constraints.NotBlank;

public class TripStyleDto {
	
	@NotBlank
	private Integer id;
	
	@NotBlank
	private String styleName;
	
	
	public TripStyleDto() {}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStyleName() {
		return styleName;
	}
	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}
	
}
