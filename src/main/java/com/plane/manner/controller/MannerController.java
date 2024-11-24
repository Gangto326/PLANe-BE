package com.plane.manner.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plane.common.annotation.UserId;
import com.plane.common.response.ApiResponse;
import com.plane.manner.dto.MannerDetailResponse;
import com.plane.manner.dto.MannerEvaluateRequest;
import com.plane.manner.dto.MannerUserResponse;
import com.plane.manner.service.MannerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/manner")
public class MannerController {

	private final MannerService mannerService;

	@Autowired
	public MannerController(MannerService mannerService) {
		this.mannerService = mannerService;
	}
	
	
	@PostMapping("")
	public ResponseEntity<ApiResponse<Boolean>> manner(
			@UserId String userId,
			@Valid @RequestBody MannerEvaluateRequest mannerEvaluateRequest
			) {
		
		mannerService.evaluateManner(userId, mannerEvaluateRequest);
		return ResponseEntity.ok(ApiResponse.success(true, "매너 평가가 완료되었습니다."));
	}
	
	
	@GetMapping("/{articleId}")
	public ResponseEntity<ApiResponse<MannerDetailResponse>> mannerDetail(
			@UserId String userId,
			@PathVariable Long articleId
			) {
		
		List<MannerUserResponse> userList = mannerService.getMannerDetail(userId, articleId);
		
		MannerDetailResponse mannerDetailResponse = new MannerDetailResponse();
		mannerDetailResponse.setArticleId(articleId);
		mannerDetailResponse.setUserList(userList);
		
		return ResponseEntity.ok(ApiResponse.success(mannerDetailResponse, "매너 상세페이지를 반환하였습니다."));
	}
	
}
