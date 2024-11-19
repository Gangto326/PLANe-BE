package com.plane.notification.dto;

public class NotificationTarget {
	
	private NotificationTargetType type;
    private String nickname;
    private String content;
    
    
	public NotificationTarget() {}


	public NotificationTargetType getType() {
		return type;
	}


	public void setType(NotificationTargetType type) {
		this.type = type;
	}
	

	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
    
}
