package com.plane.article.repository;

import java.util.List;

import com.plane.article.domain.Article;
import com.plane.article.dto.ArticleDetailResponse;
import com.plane.article.dto.ArticleResponse;
import com.plane.article.dto.ArticleUpdateRequest;
import com.plane.common.dto.PageRequest;

import jakarta.validation.Valid;

public interface ArticleRepository {

	ArticleDetailResponse selectArticleDetail(String currentUserId, int articleId);

	int updateArticle(String userId, ArticleUpdateRequest articleUpdateRequest);

	long countAllArticles(String articleType);

	List<ArticleResponse> findAllArticles(String userId, PageRequest pageRequest);

	

}
