package com.plane.article.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.plane.article.dto.ArticleDetailResponse;
import com.plane.article.service.ArticleService;
import com.plane.common.response.ApiResponse;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
	
	private final ArticleService articleService;

	@Autowired
	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	@GetMapping("/{articleId}")
	public ResponseEntity<ApiResponse<ArticleDetailResponse>> articleDetail(@PathVariable int articleId, @RequestParam String currentUserId) {
		
		ArticleDetailResponse articleDetailResponse = null;
		
		articleDetailResponse = articleService.getArticleDetail(articleId, currentUserId);
		
		return ResponseEntity.ok(ApiResponse.success(articleDetailResponse, "게시글을 불러왔습니다."));
	}
	
	
	
}
