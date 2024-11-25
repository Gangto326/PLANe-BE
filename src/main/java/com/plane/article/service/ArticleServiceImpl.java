package com.plane.article.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.plane.article.repository.ArticleRepository;
import com.plane.comment.dto.CommentNotificationInfo;
import com.plane.common.dto.PageInfo;
import com.plane.common.dto.PageRequest;
import com.plane.common.dto.PageResponse;
import com.plane.common.exception.ErrorCode;
import com.plane.common.exception.SystemException;
import com.plane.common.exception.custom.ArticleCreateException;
import com.plane.common.exception.custom.ArticleNotFoundException;
import com.plane.common.exception.custom.ArticleUpdateException;
import com.plane.common.exception.custom.CreationFailedException;
import com.plane.common.exception.custom.DuplicateException;
import com.plane.common.exception.custom.InvalidParameterException;
import com.plane.common.exception.custom.UnauthorizedException;
import com.plane.common.exception.custom.UpdateFailedException;
import com.plane.common.exception.custom.UserNotFoundException;
import com.plane.common.service.S3Service;
import com.plane.notification.dto.NotificationAction;
import com.plane.notification.dto.NotificationCreateRequest;
import com.plane.notification.dto.NotificationTargetType;
import com.plane.notification.service.NotificationService;
import com.plane.trip.domain.Plane;
import com.plane.trip.repository.TripRepository;
import com.plane.user.domain.User;

import jakarta.validation.Valid;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

	private final ArticleRepository articleRepository;
	private final TripRepository tripRepository;
	private final NotificationService notificationService;
	private final S3Service s3Service;
	
	@Autowired
	public ArticleServiceImpl(ArticleRepository articleRepository, TripRepository tripRepository, NotificationService notificationService, S3Service s3Service) {
		this.articleRepository = articleRepository;
		this.tripRepository = tripRepository;
		this.notificationService = notificationService;
		this.s3Service = s3Service;
	}

	
	@Override
	public ArticleDetailResponse getArticleDetail(String userId, Long articleId) {
		
		ArticleDetailResponse articleDetailResponse = articleRepository.selectArticleDetail(userId, articleId);
		
		if (articleDetailResponse == null) {
			throw new ArticleNotFoundException("해당 게시글을 찾을 수 없습니다.");
		}
		
		if (articleDetailResponse.getDeletedDate() != null) {
			throw new ArticleNotFoundException("삭제된 게시글입니다.");
		}
		
		return articleDetailResponse;
	}
	
	
	@Override
	public ArticleDetailResponse getArticleWithViewCount(String userId, Long articleId) {
		
		ArticleDetailResponse articleDetailResponse = articleRepository.selectArticleDetailAndIncrementViewCount(userId, articleId);
		
		if (articleDetailResponse == null) {
			throw new ArticleNotFoundException("해당 게시글을 찾을 수 없습니다.");
		}
		
		if (articleDetailResponse.getDeletedDate() != null) {
			throw new ArticleNotFoundException("삭제된 게시글입니다.");
		}
		
		return articleDetailResponse;
	}

	
	@Override
	public boolean updateArticle(String userId, ArticleUpdateRequest articleUpdateRequest) {
		
		Article article = articleRepository.findArticleByUserIdAndArticleId(userId, articleUpdateRequest.getArticleId());
		
		if (article == null) {
			throw new ArticleNotFoundException("게시글이 존재하지 않습니다.");
		}
		
		if (article.getDeletedDate() != null) {
			throw new ArticleNotFoundException("삭제된 게시글입니다.");
		}
		
		// 사진 추가
		if (articleUpdateRequest.getFile() != null && !articleUpdateRequest.getFile().isEmpty()) {
			s3Service.validateImageFile(articleUpdateRequest.getFile());
			
			if (article.getArticlePictureUrl() != null) {
				s3Service.deleteFile(article.getArticlePictureUrl());
            }
			
			String imageUrl = s3Service.uploadFile(articleUpdateRequest.getFile());
			articleUpdateRequest.setArticlePictureUrl(imageUrl);
		}
		
//		if (articleUpdateRequest.getArticleType().equals("동행") && articleUpdateRequest.getAccompanyNum() != null &&
//				articleUpdateRequest.getAccompanyNum() > 1 && articleUpdateRequest.getAccompanyNum() <= 6) {
//			
//			
//			articleRepository.updateAccompanyNum(articleUpdateRequest.getArticleId(), articleUpdateRequest.getAccompanyNum());
//		}
		
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
	public boolean deleteArticle(String userId, Long articleId) {
		
		Article article = articleRepository.findArticleByUserIdAndArticleId(userId, articleId);
		
		if (article == null) {
			throw new ArticleNotFoundException("userId: "+userId+", articleId: "+articleId+" || 해당 게시글을 찾을 수 없습니다.");
		}
		
		if (article.getDeletedDate() != null) {
			throw new ArticleNotFoundException("이미 삭제된 게시글입니다.");
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
		
		if (!articleRepository.existsArticleByArticleId(articleInteractionRequset.getArticleId())) {
			throw new ArticleNotFoundException("게시글을 찾을 수 없습니다.");
		}
		
		int result = articleRepository.deleteInteraction(userId, articleInteractionRequset);
		
		if (result == 0) {
			if (articleRepository.insertInteraction(userId, articleInteractionRequset) != 1) {
				throw new SystemException(ErrorCode.DATABASE_ERROR, "상호작용 중 오류가 발생하였습니다.");
			}
		}
		
		return true;
	}


	@Override
	public boolean reportArticle(String userId, ArticleReportRequest articleReportRequest) {
		
		if (!articleRepository.existsArticleByArticleId(articleReportRequest.getArticleId())) {
			throw new ArticleNotFoundException("게시글을 찾을 수 없습니다.");
		}
		
		if (articleRepository.existsReportByUserIdAndArticleId(userId, articleReportRequest.getArticleId())) {
			throw new DuplicateException("이미 신고한 글입니다.");
		}
		
		if (articleRepository.insertReport(userId, articleReportRequest) == 1) {
			
			try {
				ArticleNotificationInfo notificationInfo = articleRepository.selectArticleNotificationInfo(articleReportRequest.getArticleId());
				
				NotificationCreateRequest notificationCreateRequest = new NotificationCreateRequest();
				notificationCreateRequest.setNotificationType("accompany");
				notificationCreateRequest.setContentId(articleReportRequest.getArticleId());
				notificationCreateRequest.setDetails(notificationInfo.getTitle());
				notificationCreateRequest.setType(NotificationTargetType.ARTICLE);
				notificationCreateRequest.setAction(NotificationAction.REPORT);
				
				notificationService.createNotification(notificationInfo.getAuthorId(), notificationCreateRequest);
			} catch (Exception e) {
				
				return false;
			}
			
			return true;
		}
		
		throw new CreationFailedException("신고글 생성에 실패하였습니다");
	}


	@Override
	public boolean createArticle(String userId, ArticleCreateRequest articleCreateRequest) {
		
		Plane plane = tripRepository.selectPlaneByUserIdAndTripId(userId, articleCreateRequest.getTripId());
		
		if (plane == null) {
			throw new InvalidParameterException("여행을 찾을 수 없습니다.");
		}
		
		if (articleCreateRequest.getArticleType().equals("동행")) {
			
			if (articleCreateRequest.getAccompanyNum() <= 1) {
				throw new InvalidParameterException("동행 글에는 동행 인원이 있어야 합니다.");
			}
			
			else if (articleCreateRequest.getAccompanyNum() <= 6) {
				tripRepository.updateAccompanyNum(plane.getTripId(), articleCreateRequest.getAccompanyNum());
			}
		}
		
		if (articleCreateRequest.getArticleType().equals("후기")) {
			
			if (plane.getArrivedDate().isAfter(LocalDate.now())) {
				throw new InvalidParameterException("후기 글은 여행이 끝난 이후여야 합니다.");
			}
			
			
		}
		
		if (articleCreateRequest.getFile() != null && !articleCreateRequest.getFile().isEmpty()) {
			s3Service.validateImageFile(articleCreateRequest.getFile());
			
			String imageUrl = s3Service.uploadFile(articleCreateRequest.getFile());
			articleCreateRequest.setArticlePictureUrl(imageUrl);
		}
		
		if (articleRepository.insertArticle(userId, articleCreateRequest) == 1) {
			return true;
		}
		
		throw new ArticleCreateException("게시글 생성 중 오류가 발생했습니다.");
	}


	@Override
	public boolean changeArticlePublic(String userId, ChangePublicRequest changePublicRequest) {
		
		if (!articleRepository.existsArticleByArticleId(changePublicRequest.getArticleId())) {
			throw new ArticleNotFoundException("게시글을 찾을 수 없습니다.");
		}
		
		if (articleRepository.updateArticlePublic(userId, changePublicRequest) == 1) {
			return true;
		}
		
		throw new ArticleUpdateException("공개여부 전환 중 오류가 발생했습니다.");
	}

	
}
