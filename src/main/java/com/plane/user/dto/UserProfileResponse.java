package com.plane.user.dto;

import java.util.List;

import com.plane.trip.domain.TripStyle;

public class UserProfileResponse {
	
	private String userId;
	private String nickName;
	private String role;
	private Double manner;
	private String profileUrl;
	private String introduce;
	private boolean isPublic;
	private List<TripStyle> tripStyle;
	private List<MannerTagDto> mannerTags;
	
	
	public UserProfileResponse() {}

	
	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	

	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public Double getManner() {
		return manner;
	}


	public void setManner(Double manner) {
		this.manner = manner;
	}


	public String getProfileUrl() {
		return profileUrl;
	}


	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}


	public String getIntroduce() {
		return introduce;
	}


	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}


	public boolean isPublic() {
		return isPublic;
	}


	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}


	public List<TripStyle> getTripStyle() {
		return tripStyle;
	}


	public void setTripStyle(List<TripStyle> tripStyle) {
		this.tripStyle = tripStyle;
	}


	public List<MannerTagDto> getMannerTags() {
		return mannerTags;
	}


	public void setMannerTags(List<MannerTagDto> mannerTags) {
		this.mannerTags = mannerTags;
	}
	
}
