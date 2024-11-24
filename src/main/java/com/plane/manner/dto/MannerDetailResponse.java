package com.plane.manner.dto;

import java.util.List;

public class MannerDetailResponse {

	private Long tripId;
	private List<MannerUserResponse> userList;
	
	
	public MannerDetailResponse() {}


	public Long getTripId() {
		return tripId;
	}


	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}


	public List<MannerUserResponse> getUserList() {
		return userList;
	}


	public void setUserList(List<MannerUserResponse> userList) {
		this.userList = userList;
	}
	
}
