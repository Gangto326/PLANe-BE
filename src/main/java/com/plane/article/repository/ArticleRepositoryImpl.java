package com.plane.article.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.plane.article.dto.ArticleDetailResponse;
import com.plane.article.dto.ArticleUpdateRequest;
import com.plane.article.mapper.ArticleMapper;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {
	
	private final ArticleMapper articleMapper;
	
	@Autowired
	public ArticleRepositoryImpl(ArticleMapper articleMapper) {
		this.articleMapper = articleMapper;
	}

	@Override
	public ArticleDetailResponse selectArticleDetail(String currentUserId, int articleId) {
		
		ArticleDetailResponse articleDetailResponse = articleMapper.selectArticleDetail(currentUserId, articleId);
		return articleDetailResponse;
	}

	@Override
	public int updateArticle(String userId, ArticleUpdateRequest articleUpdateRequest) {
		
		return articleMapper.updateArticle(userId, articleUpdateRequest);
	}
	
}
