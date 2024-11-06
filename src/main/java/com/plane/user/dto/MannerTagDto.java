package com.plane.user.dto;

import jakarta.validation.constraints.NotBlank;

public class MannerTagDto {
	
//	@NotBlank
//	private Integer id;
	
	@NotBlank
	private String mannerTagName;
	
	
	public MannerTagDto() {}

	
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}

	public String getMannerTagName() {
		return mannerTagName;
	}

	public void setMannerTagName(String mannerTagName) {
		this.mannerTagName = mannerTagName;
	}
}
