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
	public ArticleDetailResponse selectArticleDetail(int articleId, String currentUserId) {
		
		ArticleDetailResponse articleDetailResponse = articleMapper.selectArticleDetail(articleId, currentUserId);
		return articleDetailResponse;
	}

	@Override
	public boolean updateArticle(String userId, ArticleUpdateRequest articleUpdateRequest) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
