package com.plane.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.article.dto.ArticleDetailResponse;
import com.plane.article.dto.ArticleUpdateRequest;
import com.plane.article.repository.ArticleRepository;
import com.plane.common.exception.custom.ArticleNotFoundException;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

	private final ArticleRepository articleRepository;
	
	@Autowired
	public ArticleServiceImpl(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	
	@Override
	public ArticleDetailResponse getArticleDetail(String userId, int articleId) {
		
		ArticleDetailResponse articleDetailResponse = articleRepository.selectArticleDetail(userId, articleId);
		
		if (articleDetailResponse == null) {
			throw new ArticleNotFoundException("해당 게시글을 찾을 수 없습니다.");
		}
		
		return articleDetailResponse;
	}

	
	@Override
	public boolean updateArticle(String userId, ArticleUpdateRequest articleUpdateRequest) {
		
		if (articleRepository.updateArticle(userId, articleUpdateRequest) == 1) {
			
			return true;
		}
		
		throw new ArticleNotFoundException("게시글 수정에 실패하였습니다.");
	}
	
}
