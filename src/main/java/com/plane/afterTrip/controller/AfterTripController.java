package com.plane.afterTrip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plane.afterTrip.dto.AfterTripCreateRequest;
import com.plane.afterTrip.service.AfterTripService;
import com.plane.common.annotation.UserId;
import com.plane.common.response.ApiResponse;

@RestController
@RequestMapping("/api/trip/after")
public class AfterTripController {
	
	private final AfterTripService afterTripService;

	@Autowired
	public AfterTripController(AfterTripService afterTripService) {
		this.afterTripService = afterTripService;
	}
	
	
	@GetMapping("/{tripId}")
	public ResponseEntity<ApiResponse<?>> getAfterTrip(
	    @UserId String userId,
	    @PathVariable Long tripId
	) {
		
	    return ResponseEntity.ok(ApiResponse.success(true, ""));
	}
	
	
	@PostMapping("")
	public ResponseEntity<ApiResponse<Boolean>> createAfterTrip(
	    @UserId String userId,
	    @ModelAttribute AfterTripCreateRequest afterTripCreateRequest
	) {
		
		afterTripService.createAfterTrip(userId, afterTripCreateRequest);
	    return ResponseEntity.ok(ApiResponse.success(true, "여행 후기 생성에 성공하였습니다."));
	}
	
}
