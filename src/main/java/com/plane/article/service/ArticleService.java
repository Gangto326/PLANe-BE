package com.plane.article.service;

import com.plane.article.dto.ArticleDetailResponse;
import com.plane.article.dto.ArticleUpdateRequest;

import jakarta.validation.Valid;

public interface ArticleService {

	ArticleDetailResponse getArticleDetail(String authorizationHeader, int articleId);

	boolean updateArticle(String authorizationHeader, ArticleUpdateRequest articleUpdateRequest);

}
