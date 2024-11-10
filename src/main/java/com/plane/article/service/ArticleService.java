package com.plane.article.service;

import com.plane.article.dto.ArticleDetailResponse;

public interface ArticleService {

	ArticleDetailResponse getArticleDetail(int articleId, String currentUserId);

}
