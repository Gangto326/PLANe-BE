package com.plane.article.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.article.domain.Article;
import com.plane.article.dto.ArticleDetailResponse;
import com.plane.article.dto.ArticleInteractionRequset;
import com.plane.article.dto.ArticleResponse;
import com.plane.article.dto.ArticleSearchRequest;
import com.plane.article.dto.ArticleUpdateRequest;
import com.plane.article.repository.ArticleRepository;
import com.plane.common.dto.PageInfo;
import com.plane.common.dto.PageRequest;
import com.plane.common.dto.PageResponse;
import com.plane.common.exception.ErrorCode;
import com.plane.common.exception.SystemException;
import com.plane.common.exception.custom.ArticleNotFoundException;
import com.plane.common.exception.custom.ArticleUpdateException;
import com.plane.common.exception.custom.UnauthorizedException;

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
		
		Article article = articleRepository.findArticleByUserIdAndArticleId(userId, articleUpdateRequest.getArticleId());
		
		if (article == null) {
			throw new ArticleNotFoundException("게시글이 존재하지 않습니다.");
		}
		
		if (articleRepository.updateArticle(userId, articleUpdateRequest) == 1) {
			return true;
		}
		
		throw new ArticleUpdateException("게시글 수정에 실패하였습니다.");
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


	@Override
	public boolean deleteArticle(String userId, Integer articleId) {
		
		Article article = articleRepository.findArticleByUserIdAndArticleId(userId, articleId);
		
		if (article == null) {
			throw new ArticleNotFoundException("userId: "+userId+", articleId: "+articleId+" || 해당 게시글을 찾을 수 없습니다.");
		}
		
		if (!article.getAuthorId().equals(userId)) {
			throw new UnauthorizedException("삭제 권한이 없습니다.");
		}
		
		if (articleRepository.deleteArticle(userId, articleId) != 1) {
			throw new SystemException(ErrorCode.DATABASE_ERROR, "삭제 중 오류가 발생하였습니다.");
		}
		
		return true;
	}


	@Override
	public boolean toggleInteraction(String userId, ArticleInteractionRequset articleInteractionRequset) {
		
		int result = articleRepository.deleteInteraction(userId, articleInteractionRequset);
		
		if (result == 0) {
			result = articleRepository.insertInteraction(userId, articleInteractionRequset);
		}
		
		if (result != 1) {
			throw new SystemException(ErrorCode.DATABASE_ERROR, "상호작용 중 오류가 발생하였습니다.");
		}
		
		return true;
		
	}
	
}
