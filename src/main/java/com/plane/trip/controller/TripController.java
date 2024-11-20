package com.plane.trip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plane.common.annotation.UserId;
import com.plane.common.response.ApiResponse;
import com.plane.trip.dto.TripCreateRequest;
import com.plane.trip.service.TripService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/plane")
public class TripController {
	
	private final TripService tripService;

	@Autowired
	public TripController(TripService tripService) {
		this.tripService = tripService;
	}
	
	
	@PostMapping("")
	public ResponseEntity<ApiResponse<Boolean>> planeCreate(
			@UserId String userId,
			@Valid @RequestBody TripCreateRequest tripCreateRequest
			) {
		
		tripService.createPlane(userId, tripCreateRequest);
		return ResponseEntity.ok(ApiResponse.success(true, "여행 생성에 성공했습니다."));
	}
	
}
