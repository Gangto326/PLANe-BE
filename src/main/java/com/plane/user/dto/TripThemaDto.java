package com.plane.user.dto;

import jakarta.validation.constraints.NotBlank;

public class TripThemaDto {

	@NotBlank
	private Integer id;
	
	@NotBlank
	private String themaName;
	
	
	public TripThemaDto() {}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getThemaName() {
		return themaName;
	}


	public void setThemaName(String themaName) {
		this.themaName = themaName;
	}
	
}
