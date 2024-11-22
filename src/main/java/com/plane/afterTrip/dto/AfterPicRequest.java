package com.plane.afterTrip.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;

public class AfterPicRequest {
	
	private String afterPictureUrl;
	private String originalFilename;
	private MultipartFile file;
	
	
	public AfterPicRequest() {}


	public String getAfterPictureUrl() {
		return afterPictureUrl;
	}


	public void setAfterPictureUrl(String afterPictureUrl) {
		this.afterPictureUrl = afterPictureUrl;
	}
	

	public String getOriginalFilename() {
		return originalFilename;
	}


	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}


	public MultipartFile getFile() {
		return file;
	}


	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
