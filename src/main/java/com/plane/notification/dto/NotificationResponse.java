package com.plane.notification.dto;

import java.time.LocalDateTime;

public class NotificationResponse {

	private Long noId;
	private boolean isRead;
	private String notificationType;
	private Long contentId;
	private LocalDateTime createdDate;
	
	
	public NotificationResponse() {}


	public Long getNoId() {
		return noId;
	}


	public void setNoId(Long noId) {
		this.noId = noId;
	}


	public boolean isRead() {
		return isRead;
	}


	public void setRead(boolean isRead) {
		this.isRead = isRead;
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


	public LocalDateTime getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
}
