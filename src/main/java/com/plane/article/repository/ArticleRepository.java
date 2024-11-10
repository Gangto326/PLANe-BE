package com.plane.article.repository;

import com.plane.article.dto.ArticleDetailResponse;

public interface ArticleRepository {

	ArticleDetailResponse selectArticleDetail(int articleId, String currentUserId);

}
