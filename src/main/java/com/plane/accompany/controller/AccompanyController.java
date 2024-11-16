package com.plane.accompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plane.accompany.dto.AccompanyRegistRequest;
import com.plane.accompany.service.AccompanyService;
import com.plane.common.annotation.UserId;
import com.plane.common.response.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/accompany")
public class AccompanyController {
	
	private final AccompanyService accompanyService;

	@Autowired
	public AccompanyController(AccompanyService accompanyService) {
		this.accompanyService = accompanyService;
	}
	
	
	@PostMapping("/regist")
	public ResponseEntity<ApiResponse<Boolean>> accompanyRegist(
			@UserId String userId,
			@Valid @RequestBody AccompanyRegistRequest accompanyRegistRequest
			) {
		
		accompanyService.sendAccompanyRegist(userId, accompanyRegistRequest);
		return ResponseEntity.ok(ApiResponse.success(true, "동행 신청을 성공적으로 보냈습니다."));
	}
	
	
	
}
