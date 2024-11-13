package com.plane.article.repository;

import com.plane.article.dto.ArticleDetailResponse;
import com.plane.article.dto.ArticleUpdateRequest;

import jakarta.validation.Valid;

public interface ArticleRepository {

	ArticleDetailResponse selectArticleDetail(int articleId, String currentUserId);

	boolean updateArticle(String userId, ArticleUpdateRequest articleUpdateRequest);

}
