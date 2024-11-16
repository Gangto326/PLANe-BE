package com.plane.article.dto;

//import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ArticleUpdateRequest {
	
	@NotNull(message = "글 번호는 필수입니다.")
	private Long articleId;
	
	
	@Size(max = 100, message = "제목은 100자를 초과할 수 없습니다")
	private String title;
	
	
	@NotBlank(message = "내용은 필수입니다.")
	private String content;
	
	
//	private LocalDateTime updatedDate;


	public ArticleUpdateRequest() {}


	public Long getArticleId() {
		return articleId;
	}


	public void setArticleId(Long articleId) {
		this.articleId = articleId;
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
	
}
