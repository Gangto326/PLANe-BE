package com.plane.article.service;

import com.plane.article.dto.ArticleDetailResponse;
import com.plane.article.dto.ArticleUpdateRequest;

import jakarta.validation.Valid;

public interface ArticleService {

	ArticleDetailResponse getArticleDetail(int articleId, String authorizationHeader);

	boolean updateArticle(String authorizationHeader, @Valid ArticleUpdateRequest articleUpdateRequest);

}
