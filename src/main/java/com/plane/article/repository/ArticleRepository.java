package com.plane.article.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.plane.article.domain.Article;
import com.plane.article.dto.ArticleCreateRequest;
import com.plane.article.dto.ArticleDetailResponse;
import com.plane.article.dto.ArticleInteractionRequset;
import com.plane.article.dto.ArticleNotificationInfo;
import com.plane.article.dto.ArticleReportRequest;
import com.plane.article.dto.ArticleResponse;
import com.plane.article.dto.ArticleSearchRequest;
import com.plane.article.dto.ArticleUpdateRequest;
import com.plane.article.dto.ChangePublicRequest;
import com.plane.common.dto.PageRequest;

import jakarta.validation.Valid;

public interface ArticleRepository {

	ArticleDetailResponse selectArticleDetail(String currentUserId, Long articleId);
	
	ArticleDetailResponse selectArticleDetailAndIncrementViewCount(String userId, Long articleId);

	int updateArticle(String userId, ArticleUpdateRequest articleUpdateRequest);

	long countAllArticles(String userId, ArticleSearchRequest articleSearchRequest);

	List<ArticleResponse> findAllArticles(String userId, ArticleSearchRequest articleSearchRequest);

	Article findArticleByUserIdAndArticleId(String userId, Long articleId);

	int deleteArticle(String userId, Long articleId);

	int deleteInteraction(String userId, ArticleInteractionRequset articleInteractionRequset);

	int insertInteraction(String userId, ArticleInteractionRequset articleInteractionRequset);

	boolean existsArticleByArticleId(Long articleId);

	boolean existsReportByUserIdAndArticleId(String userId, Long articleId);
	
	int insertReport(String userId, ArticleReportRequest articleReportRequest);

	ArticleNotificationInfo selectArticleNotificationInfo(Long articleId);

	int insertArticle(String userId, ArticleCreateRequest articleCreateRequest);

	int updateArticlePublic(String userId, ChangePublicRequest changePublicRequest);

}
