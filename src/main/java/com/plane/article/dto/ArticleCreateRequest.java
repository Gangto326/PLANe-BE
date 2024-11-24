package com.plane.article.dto;

import org.hibernate.validator.constraints.URL;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ArticleCreateRequest {

	@NotNull(message = "여행 번호는 필수입니다.")
	private Long tripId;
	
	
	@Pattern(regexp = "^(동행|후기)$", message = "글 분류는 동행, 후기 중 하나여야 합니다")
	private String articleType;
	
	
	@Size(max = 100, message = "제목은 100자를 초과할 수 없습니다")
	private String title;
	
	
	@NotBlank(message = "내용은 필수입니다.")
	private String content;
	
	
	@URL(message = "올바른 URL 형식이 아닙니다")
	private String articlePictureUrl;
	
	private MultipartFile file;
	
	private boolean isPublic;
	
	
	public ArticleCreateRequest() {}


	public Long getTripId() {
		return tripId;
	}


	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}


	public String getArticleType() {
		return articleType;
	}


	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getArticlePictureUrl() {
		return articlePictureUrl;
	}


	public void setArticlePictureUrl(String articlePictureUrl) {
		this.articlePictureUrl = articlePictureUrl;
	}


	public MultipartFile getFile() {
		return file;
	}


	public void setFile(MultipartFile file) {
		this.file = file;
	}


	public boolean isPublic() {
		return isPublic;
	}


	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	
	
}
