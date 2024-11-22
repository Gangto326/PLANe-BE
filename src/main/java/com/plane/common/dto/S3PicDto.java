package com.plane.common.dto;

public class S3PicDto {
	
	private String url;
	private String originalFileName;
	
	
	public S3PicDto() {}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getOriginalFileName() {
		return originalFileName;
	}


	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	
}
