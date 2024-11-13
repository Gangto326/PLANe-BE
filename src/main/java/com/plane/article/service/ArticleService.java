package com.plane.article.service;

import com.plane.article.domain.Article;
import com.plane.article.dto.ArticleDetailResponse;
import com.plane.article.dto.ArticleResponse;
import com.plane.article.dto.ArticleUpdateRequest;
import com.plane.common.dto.PageRequest;
import com.plane.common.dto.PageResponse;

import jakarta.validation.Valid;

public interface ArticleService {

	ArticleDetailResponse getArticleDetail(String authorizationHeader, int articleId);

	boolean updateArticle(String authorizationHeader, ArticleUpdateRequest articleUpdateRequest);

	PageResponse<ArticleResponse> getList(String userId, PageRequest pageRequest);

}
