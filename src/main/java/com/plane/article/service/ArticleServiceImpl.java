package com.plane.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.article.dto.ArticleDetailResponse;
import com.plane.article.dto.ArticleUpdateRequest;
import com.plane.article.repository.ArticleRepository;
import com.plane.common.exception.custom.ArticleNotFoundException;
import com.plane.common.util.JwtUtil;

import jakarta.validation.Valid;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

	private final ArticleRepository articleRepository;
	private final JwtUtil jwtUtil;
	
	@Autowired
	public ArticleServiceImpl(ArticleRepository articleRepository, JwtUtil jwtUtil) {
		this.articleRepository = articleRepository;
		this.jwtUtil = jwtUtil;
	}

	
	@Override
	public ArticleDetailResponse getArticleDetail(int articleId, String authorizationHeader) {
		
		String currentUserId = jwtUtil.getUserId(authorizationHeader, "AccessToken");
		ArticleDetailResponse articleDetailResponse = articleRepository.selectArticleDetail(articleId, currentUserId);
		
		if (articleDetailResponse == null) {
			throw new ArticleNotFoundException("해당 게시글을 찾을 수 없습니다.");
		}
		
		return articleDetailResponse;
	}

	
	@Override
	public boolean updateArticle(String authorizationHeader, ArticleUpdateRequest articleUpdateRequest) {
		
		String userId = jwtUtil.getUserId(authorizationHeader, "AccessToken");
		
		if (!articleRepository.updateArticle(userId, articleUpdateRequest)) {
			
		}
		
		return false;
	}
	
	
}
