package com.plane.notification.dto;

public class NotificationCreateRequest {

	private String notificationType;
	private Long contentId;
	private String title;
	private String details;
	
	private NotificationTargetType type;
	private NotificationAction action;
	
	
	public NotificationCreateRequest() {}


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
	

	public NotificationTargetType getType() {
		return type;
	}


	public void setType(NotificationTargetType type) {
		this.type = type;
	}


	public NotificationAction getAction() {
		return action;
	}


	public void setAction(NotificationAction action) {
		this.action = action;
	}
	
}
