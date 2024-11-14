package com.plane.article.service;

import com.plane.article.domain.Article;
import com.plane.article.dto.ArticleDetailResponse;
import com.plane.article.dto.ArticleInteractionRequset;
import com.plane.article.dto.ArticleResponse;
import com.plane.article.dto.ArticleSearchRequest;
import com.plane.article.dto.ArticleUpdateRequest;
import com.plane.common.dto.PageRequest;
import com.plane.common.dto.PageResponse;

import jakarta.validation.Valid;

public interface ArticleService {

	ArticleDetailResponse getArticleDetail(String authorizationHeader, int articleId);

	boolean updateArticle(String authorizationHeader, ArticleUpdateRequest articleUpdateRequest);

	PageResponse<ArticleResponse> getArticleList(String userId, ArticleSearchRequest articleSearchRequest);

	boolean deleteArticle(String userId, Integer articleId);

	boolean toggleInteraction(String userId, ArticleInteractionRequset articleInteractionRequset);

}
