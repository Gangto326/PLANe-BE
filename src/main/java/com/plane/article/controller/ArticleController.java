package com.plane.article.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.plane.article.domain.Article;
import com.plane.article.dto.ArticleCreateRequest;
import com.plane.article.dto.ArticleDetailResponse;
import com.plane.article.dto.ArticleInteractionRequset;
import com.plane.article.dto.ArticleReportRequest;
import com.plane.article.dto.ArticleResponse;
import com.plane.article.dto.ArticleSearchRequest;
import com.plane.article.dto.ArticleUpdateRequest;
import com.plane.article.dto.ChangePublicRequest;
import com.plane.article.service.ArticleService;
import com.plane.common.annotation.UserId;
import com.plane.common.dto.PageRequest;
import com.plane.common.dto.PageResponse;
import com.plane.common.response.ApiResponse;
import com.plane.common.util.CookieUtil;
import com.plane.trip.domain.TripThema;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
	
	private final ArticleService articleService;
	
	private final CookieUtil cookieUtil;

	@Autowired
	public ArticleController(ArticleService articleService, CookieUtil cookieUtil) {
		this.articleService = articleService;
		this.cookieUtil = cookieUtil;
	}
	
	
	@GetMapping("/{articleId}")
	public ResponseEntity<ApiResponse<ArticleDetailResponse>> articleDetail(
			@UserId String userId,
			@PathVariable Long articleId,
			HttpServletRequest request
			) {
		
		// 게시글 접속 로그 확인
		boolean hasViewed = cookieUtil.checkViewCookie(articleId, request);
		
		// 접속 로그 유무에 따라 다른 메소드 호출
		ArticleDetailResponse articleDetailResponse = hasViewed? articleService.getArticleDetail(userId, articleId): articleService.getArticleWithViewCount(userId, articleId);
		
		return ResponseEntity.ok(ApiResponse.success(articleDetailResponse, "게시글을 불러왔습니다."));
	}
	
	
	@PatchMapping("/update")
	public ResponseEntity<ApiResponse<Boolean>> articleUpdate(
			@UserId String userId,
			@Valid @ModelAttribute ArticleUpdateRequest articleUpdateRequest
			) {
		
		articleService.updateArticle(userId, articleUpdateRequest);
		return ResponseEntity.ok(ApiResponse.success(true, "게시글 수정을 성공적으로 완료했습니다."));
	}
	
	
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<PageResponse<ArticleResponse>>> articleSearch(
			@UserId String userId,
			
			// PageRequest 관련 파라미터
	        @RequestParam(required = false, defaultValue = "1") @Min(1) int page,
	        @RequestParam(required = false, defaultValue = "12") @Min(1) int size,
	        @RequestParam(required = false, defaultValue = "createdDate") 
	        @Pattern(regexp = "^(createdDate|likeCount|viewCount)$") String sortBy,
	        @RequestParam(required = false, defaultValue = "DESC") 
	        @Pattern(regexp = "^(ASC|DESC)$") String sortDirection,
	        
	        // 검색 조건 파라미터
	        @RequestParam @Pattern(regexp = "^(동행|후기)$") String articleType,
	        @RequestParam(required = false) Integer regionId,
	        @RequestParam(required = false) String tripThema,
	        @RequestParam(required = false)
	        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tripPeriod,
	        @RequestParam(required = false) @Min(0) Integer tripDays,
	        @RequestParam(required = false) boolean saved,
	        @RequestParam(required = false) boolean recommend,
	        @RequestParam(required = false) String searchTitle
			) {
		
		// 페이지네이션 관련 정보 삽입 무조건 필수 정보들이어야 함.
		PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(page);
        pageRequest.setSize(size);
        pageRequest.setSortBy(sortBy);
        pageRequest.setSortDirection(sortDirection);
        
        
        // Thema 추출
        List<Integer> themaList = new ArrayList<>();
        
        if (tripThema != null && !tripThema.isEmpty()) {
            String[] themas = tripThema.split(",");
            
            for (String thema : themas) {                
                themaList.add(Integer.parseInt(thema));
            }
        }
        
        
        ArticleSearchRequest articleSearchRequest = new ArticleSearchRequest();
        articleSearchRequest.setPageRequest(pageRequest);
        articleSearchRequest.setArticleType(articleType);
        articleSearchRequest.setRegionId(regionId);
        articleSearchRequest.setTripThema(themaList);
        articleSearchRequest.setTripPeriod(tripPeriod);
        articleSearchRequest.setTripDays(tripDays);
        articleSearchRequest.setSaved(saved);
        articleSearchRequest.setRecommand(recommend);
        articleSearchRequest.setSearchTitle(searchTitle);
        
		
		PageResponse<ArticleResponse> pageResponse = articleService.getArticleList(userId, articleSearchRequest);		
		return ResponseEntity.ok(ApiResponse.success(pageResponse, "게시글 목록을 불러왔습니다."));
	}
	
	
	@DeleteMapping("/delete/{articleId}")
	public ResponseEntity<ApiResponse<Boolean>> articleDelete(
			@UserId String userId,
			@PathVariable Long articleId
			) {
		
		articleService.deleteArticle(userId, articleId);
		return ResponseEntity.ok(ApiResponse.success(true, "게시글 삭제를 성공적으로 완료했습니다."));
	}
	
	
	@PostMapping("/interaction")
	public ResponseEntity<ApiResponse<Boolean>> articleInteraction(
			@UserId String userId,
			@Valid @RequestBody ArticleInteractionRequset articleInteractionRequset
			) {
		
		articleService.toggleInteraction(userId, articleInteractionRequset);
		return ResponseEntity.ok(ApiResponse.success(true, "좋아요 또는, 보관하기 상호작용에 성공했습니다."));
	}
	
	
	@PostMapping("/report")
	public ResponseEntity<ApiResponse<Boolean>> articleReport(
			@UserId String userId,
			@Valid @RequestBody ArticleReportRequest articleReportRequest
			) {
		
		articleService.reportArticle(userId, articleReportRequest);
		return ResponseEntity.ok(ApiResponse.success(true, "게시글 신고가 완료되었습니다."));
	}
	
	
	@PostMapping("/create")
	public ResponseEntity<ApiResponse<Boolean>> articleCreate(
			@UserId String userId,
			@Valid @ModelAttribute ArticleCreateRequest articleCreateRequest
			) {
		
		articleService.createArticle(userId, articleCreateRequest);
		return ResponseEntity.ok(ApiResponse.success(true, "게시글 생성이 완료되었습니다."));
	}
	
	
	@PostMapping("/changePublic")
	public ResponseEntity<ApiResponse<Boolean>> changeArticlePublic(
			@UserId String userId,
			@Valid @RequestBody ChangePublicRequest changePublicRequest
			) {
		
		articleService.changeArticlePublic(userId, changePublicRequest);
		return ResponseEntity.ok(ApiResponse.success(true, "게시글 전환이 완료되었습니다."));
	}
	
}
