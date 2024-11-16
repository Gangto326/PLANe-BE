package com.plane.article.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.plane.article.domain.Article;
import com.plane.article.dto.ArticleDetailResponse;
import com.plane.article.dto.ArticleInteractionRequset;
import com.plane.article.dto.ArticleReportRequest;
import com.plane.article.dto.ArticleResponse;
import com.plane.article.dto.ArticleSearchRequest;
import com.plane.article.dto.ArticleUpdateRequest;
import com.plane.article.mapper.ArticleMapper;
import com.plane.common.dto.PageRequest;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {
	
	private final ArticleMapper articleMapper;
	
	@Autowired
	public ArticleRepositoryImpl(ArticleMapper articleMapper) {
		this.articleMapper = articleMapper;
	}

	@Override
	public ArticleDetailResponse selectArticleDetail(String currentUserId, Long articleId) {
		
		ArticleDetailResponse articleDetailResponse = articleMapper.selectArticleDetail(currentUserId, articleId);
		return articleDetailResponse;
	}

	@Override
	public int updateArticle(String userId, ArticleUpdateRequest articleUpdateRequest) {
		
		return articleMapper.updateArticle(userId, articleUpdateRequest);
	}

	@Override
	public long countAllArticles(String userId, ArticleSearchRequest articleSearchRequest) {
		
		return articleMapper.countAll(userId, articleSearchRequest);
	}

	@Override
	public List<ArticleResponse> findAllArticles(String userId, ArticleSearchRequest articleSearchRequest) {
		
		return articleMapper.selectArticlesByPageRequest(userId, articleSearchRequest);
	}

	@Override
	public Article findArticleByUserIdAndArticleId(String userId, Long articleId) {
		
		return articleMapper.selectArticleByUserIdAndArticleId(userId, articleId);
	}

	@Override
	public int deleteArticle(String userId, Long articleId) {
		
		return articleMapper.deleteArticle(userId, articleId);
	}

	@Override
	public int deleteInteraction(String userId, ArticleInteractionRequset articleInteractionRequset) {
		
		return articleMapper.deleteInteractionByUserId(userId, articleInteractionRequset);
	}

	@Override
	public int insertInteraction(String userId, ArticleInteractionRequset articleInteractionRequset) {

		return articleMapper.insertInteractionByUserId(userId, articleInteractionRequset);
	}

	@Override
	public boolean existsArticleByArticleId(Long articleId) {

		return articleMapper.existsArticleByArticleId(articleId);
	}
	
	@Override
	public boolean existsReportByUserIdAndArticleId(String userId, Long articleId) {
		
		return articleMapper.existsReportByUserIdAndArticleId(userId, articleId);
	}

	@Override
	public int insertReport(String userId, ArticleReportRequest articleReportRequest) {
		
		return articleMapper.insertReport(userId, articleReportRequest);
	}
	
}
