package com.plane.trip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plane.common.annotation.UserId;
import com.plane.common.response.ApiResponse;
import com.plane.trip.dto.TripCreateRequest;
import com.plane.trip.dto.TripDetailResponse;
import com.plane.trip.dto.TripResponse;
import com.plane.trip.dto.TripUpdateRequest;
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
	
	
	@DeleteMapping("")
	public ResponseEntity<ApiResponse<Boolean>> planeDelete(
			@UserId String userId,
			@RequestBody Long tripId
			) {
		
		tripService.deletePlane(userId, tripId);
		return ResponseEntity.ok(ApiResponse.success(true, "여행 삭제에 성공했습니다."));
	}
	
	
	@GetMapping("/{tripId}")
	public ResponseEntity<ApiResponse<TripDetailResponse>> plane(
			@UserId String userId,
			@PathVariable Long tripId
			) {
		
		TripDetailResponse tripDetailResponse = tripService.getPlane(userId, tripId);
		return ResponseEntity.ok(ApiResponse.success(tripDetailResponse, "여행 정보를 성공적으로 가져왔습니다."));
	}
	
	
	@PatchMapping("")
	public ResponseEntity<ApiResponse<Boolean>> planeUpdate(
			@UserId String userId,
			@Valid @RequestBody TripUpdateRequest tripUpdateRequest
			) {
		
		tripService.updatePlane(userId, tripUpdateRequest);
		return ResponseEntity.ok(ApiResponse.success(true, "여행 수정에 성공했습니다."));
	}
	
}
