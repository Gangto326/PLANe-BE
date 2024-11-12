package com.plane.user.dto;

import java.util.Date;


public class TokenDto {
	
	private String userId;
	private String tokenType;
	private String tokenValue;
	private long issuedAt;
	private long expiresAt;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	
	public String getTokenValue() {
		return tokenValue;
	}
	
	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}
	
	public long getIssuedAt() {
		return issuedAt;
	}
	
	public void setIssuedAt(long issuedAt) {
		this.issuedAt = issuedAt;
	}
	
	public long getExpiresAt() {
		return expiresAt;
	}
	
	public void setExpiresAt(long expiresAt) {
		this.expiresAt = expiresAt;
	}
	
	public Date getDateIssuedAt() {
		return new Date(this.issuedAt);
	}
	
	public Date getDateExpiresAt() {
		return new Date(this.expiresAt);
	}
	
}