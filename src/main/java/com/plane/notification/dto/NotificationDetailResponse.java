package com.plane.notification.dto;

import java.time.LocalDateTime;

public class NotificationDetailResponse {

	private Long noId;
	private String notificationType;
	private Long contentId;
	private String details;
	private LocalDateTime createdDate;
	
	
	public NotificationDetailResponse() {}


	public Long getNoId() {
		return noId;
	}


	public void setNoId(Long noId) {
		this.noId = noId;
	}


	public String getNotificationType() {
		return notificationType;
	}


	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}


	public Long getContentId() {
		return contentId;
	}


	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}


	public String getDetails() {
		return details;
	}


	public void setDetails(String details) {
		this.details = details;
	}
	

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
}
