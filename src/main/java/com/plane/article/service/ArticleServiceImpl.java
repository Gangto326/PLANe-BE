package com.plane.article.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.article.domain.Article;
import com.plane.article.dto.ArticleDetailResponse;
import com.plane.article.dto.ArticleResponse;
import com.plane.article.dto.ArticleSearchRequest;
import com.plane.article.dto.ArticleUpdateRequest;
import com.plane.article.repository.ArticleRepository;
import com.plane.common.dto.PageInfo;
import com.plane.common.dto.PageRequest;
import com.plane.common.dto.PageResponse;
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


	@Override
	public PageResponse<ArticleResponse> getArticleList(String userId, ArticleSearchRequest articleSearchRequest) {
		
		// 검색 조건에 맞는 모든 게시글의 수 반환
		long total = articleRepository.countAllArticles(userId, articleSearchRequest);
		
		List<ArticleResponse> articleList = articleRepository.findAllArticles(userId, articleSearchRequest);
        
		PageRequest pageRequest = articleSearchRequest.getPageRequest();
        // 페이지 정보 생성
        int totalPages = (int) Math.ceil((double) total / pageRequest.getSize());
        
        // Front를 위한 메타 데이터 생성
        PageInfo pageInfo = new PageInfo(
            pageRequest.getPage(),
            pageRequest.getSize(),
            totalPages,
            total
        );
        
        return new PageResponse<>(articleList, pageInfo);
	}
	
}
