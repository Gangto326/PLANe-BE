package com.plane.notification.domain;

import java.time.LocalDateTime;

public class Notification {
	
	private Long noId;
	private String userId;
	private boolean isRead;
	private String notificationType;
	private Long contentId;
	private String title;
	private String details;
	private LocalDateTime createdDate;
	private LocalDateTime deletedDate;
	
	
	public Notification() {}


	public Long getNoId() {
		return noId;
	}


	public void setNoId(Long noId) {
		this.noId = noId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
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


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
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


	public LocalDateTime getDeletedDate() {
		return deletedDate;
	}


	public void setDeletedDate(LocalDateTime deletedDate) {
		this.deletedDate = deletedDate;
	}

}
