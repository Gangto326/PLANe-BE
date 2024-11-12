package com.plane.article.dto;

//import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ArticleUpdateRequest {
		
	@NotBlank(message = "아이디는 필수입니다")
	@Size(min = 4, max = 20, message = "아이디는 4~20자 사이여야 합니다")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영문과 숫자만 가능합니다")
	private String userId;
	
	
	@NotBlank(message = "글 번호는 필수입니다.")
	private Integer articleId;
	
	
	@Size(max = 100, message = "제목은 100자를 초과할 수 없습니다")
	private String title;
	
	
	@NotBlank(message = "내용은 필수입니다.")
	private String content;
	
	
//	private LocalDateTime updatedDate;


	public ArticleUpdateRequest() {}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public Integer getArticleId() {
		return articleId;
	}


	public void setArticleId(Integer articleId) {
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
