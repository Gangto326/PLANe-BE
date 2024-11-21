package com.plane.accompany.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.plane.accompany.dto.AccompanyAcceptRequest;
import com.plane.accompany.dto.AccompanyArticleDetailRequest;
import com.plane.accompany.dto.AccompanyDetailDto;
import com.plane.accompany.dto.AccompanyDetailResponse;
import com.plane.accompany.dto.AccompanyRegistRequest;
import com.plane.accompany.dto.AccompanyResponse;
import com.plane.accompany.dto.AccompanyUpdateRequest;
import com.plane.accompany.service.AccompanyService;
import com.plane.common.annotation.UserId;
import com.plane.common.response.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accompany")
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
	
	@GetMapping("/list")
	public ResponseEntity<ApiResponse<List<AccompanyResponse>>> getAccompanyApplications(
	    @UserId String userId,
	    @RequestParam String type
	) {
		
	    List<AccompanyResponse> accompanyList = accompanyService.getAccompanyList(userId, type);
	    return ResponseEntity.ok(ApiResponse.success(accompanyList, type+" 목록 조회 성공"));
	}
	
	@PatchMapping("")
	public ResponseEntity<ApiResponse<Boolean>> accompanyUpdate(
			@UserId String userId,
			@Valid @RequestBody AccompanyUpdateRequest accompanyUpdateRequest) {
		
		accompanyService.updateAccompany(userId, accompanyUpdateRequest);
		return ResponseEntity.ok(ApiResponse.success(true, "동행을 성공적으로 수정했습니다."));
	}
	
	@DeleteMapping("")
	public ResponseEntity<ApiResponse<Boolean>> accompanyDelete(
			@UserId String userId,
			@RequestBody Long applyId) {
		
		accompanyService.deleteAccompany(userId, applyId);
		return ResponseEntity.ok(ApiResponse.success(true, "동행을 성공적으로 취소했습니다."));
	}
	
	@PostMapping("/detail")
	public ResponseEntity<ApiResponse<AccompanyDetailResponse>> accompanyDetail(
			@UserId String userId,
			@Valid @RequestBody AccompanyArticleDetailRequest accompanyArticleDetailRequest
			) {
		
		AccompanyDetailResponse accompanyDetailResponse = accompanyService.getAccompanyDetail(userId, accompanyArticleDetailRequest);
		return ResponseEntity.ok(ApiResponse.success(accompanyDetailResponse, "동행 상세 페이지 반환 성공."));
	}
	
	@PostMapping("/accept")
	public ResponseEntity<ApiResponse<Boolean>> accompanyAccept(
			@UserId String userId,
			@Valid @RequestBody AccompanyAcceptRequest accompanyAcceptRequest
			) {
		
		accompanyService.acceptAccompany(userId, accompanyAcceptRequest);
		return ResponseEntity.ok(ApiResponse.success(true, "동행 수락 요청 성공."));
	}
	
}
