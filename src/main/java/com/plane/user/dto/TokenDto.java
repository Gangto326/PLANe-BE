package com.plane.user.dto;

import java.util.Date;

/**
 * 토큰의 정보를 전달하기 위한 클래스.
 */
public class TokenDto {
	
	private String userId;
	private String token;
	private String hashedToken;
	private long issuedAt;
	private long expiresAt;
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getHashedToken() {
		return hashedToken;
	}
	
	public void setHashedToken(String hashedToken) {
		this.hashedToken = hashedToken;
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