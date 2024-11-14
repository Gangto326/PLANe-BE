package com.plane.article.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ArticleInteractionRequset {

	@NotNull(message = "글 번호는 필수입니다.")
	private Integer articleId;
	
	
	@Pattern(regexp = "^(SAVE|RECOMMAND)$", message = "상호작용은 SAVE, RECOMMAND 중 하나여야 합니다")
	private String interaction;


	public ArticleInteractionRequset() {
	}


	public Integer getArticleId() {
		return articleId;
	}


	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}


	public String getInteraction() {
		return interaction;
	}


	public void setInteraction(String interaction) {
		this.interaction = interaction;
	}
	
}
