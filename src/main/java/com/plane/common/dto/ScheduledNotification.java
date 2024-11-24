package com.plane.common.dto;

import java.time.LocalDateTime;

public class ScheduledNotification {
	
	private Long id;
    private Long tripId;
    private String userId;
    private String type;
    private String title;
    private LocalDateTime scheduledTime;
    private boolean isSent;
    private boolean isActive;
    
    
	public ScheduledNotification() {}

	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getTripId() {
		return tripId;
	}


	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}
	

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public LocalDateTime getScheduledTime() {
		return scheduledTime;
	}


	public void setScheduledTime(LocalDateTime scheduledTime) {
		this.scheduledTime = scheduledTime;
	}


	public boolean isSent() {
		return isSent;
	}


	public void setSent(boolean isSent) {
		this.isSent = isSent;
	}


	public boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
}
