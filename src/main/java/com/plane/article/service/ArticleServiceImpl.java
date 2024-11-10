package com.plane.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.article.dto.ArticleDetailResponse;
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
	public ArticleDetailResponse getArticleDetail(int articleId, String currentUserId) {
		
		ArticleDetailResponse articleDetailResponse = articleRepository.selectArticleDetail(articleId, currentUserId);
		
		if (articleDetailResponse == null) {
			throw new ArticleNotFoundException("해당 게시글을 찾을 수 없습니다.");
		}
		
		return articleDetailResponse;
	}
	
	
}
