package com.plane.manner.controller;

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
import com.plane.manner.dto.MannerEvaluateRequest;
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
	
	
	@GetMapping("/{tripId}")
	public ResponseEntity<ApiResponse<Boolean>> mannerDetail(
			@UserId String userId,
			@PathVariable Long tripId
			) {
		
//		mannerService.evaluateManner(userId, tripId);
		return ResponseEntity.ok(ApiResponse.success(true, "매너 평가가 완료되었습니다."));
	}
	
}
