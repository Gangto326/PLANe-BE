package com.plane.article.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.plane.article.dto.ArticleDetailResponse;
import com.plane.article.dto.ArticleUpdateRequest;
import com.plane.article.service.ArticleService;
import com.plane.common.annotation.UserId;
import com.plane.common.response.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
	
	private final ArticleService articleService;

	@Autowired
	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	
	@GetMapping("/{articleId}")
	public ResponseEntity<ApiResponse<ArticleDetailResponse>> articleDetail(
			@UserId String userId,
			@PathVariable int articleId
			) {
		
		ArticleDetailResponse articleDetailResponse = null;
		articleDetailResponse = articleService.getArticleDetail(userId, articleId);
		
		return ResponseEntity.ok(ApiResponse.success(articleDetailResponse, "게시글을 불러왔습니다."));
		
	}
	
	
	@PatchMapping("/update")
	public ResponseEntity<ApiResponse<Boolean>> articleUpdate(
			@UserId String userId,
			@Valid @RequestBody ArticleUpdateRequest articleUpdateRequest
			) {
		
		articleService.updateArticle(userId, articleUpdateRequest);
		return ResponseEntity.ok(ApiResponse.success(true, "게시글 수정을 성공적으로 완료했습니다."));
		
	}
	
}
