package com.plane.article.repository;

import com.plane.article.dto.ArticleDetailResponse;
import com.plane.article.dto.ArticleUpdateRequest;

import jakarta.validation.Valid;

public interface ArticleRepository {

	ArticleDetailResponse selectArticleDetail(String currentUserId, int articleId);

	int updateArticle(String userId, ArticleUpdateRequest articleUpdateRequest);

}
