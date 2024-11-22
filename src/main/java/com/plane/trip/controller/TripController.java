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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.plane.common.annotation.UserId;
import com.plane.common.dto.PageRequest;
import com.plane.common.dto.PageResponse;
import com.plane.common.response.ApiResponse;
import com.plane.trip.dto.TripCreateRequest;
import com.plane.trip.dto.TripDetailResponse;
import com.plane.trip.dto.TripResponse;
import com.plane.trip.dto.TripSearchRequest;
import com.plane.trip.dto.TripSearchResponse;
import com.plane.trip.dto.TripUpdateRequest;
import com.plane.trip.service.TripService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/api/plane")
public class TripController {
	
	private final TripService tripService;

	@Autowired
	public TripController(TripService tripService) {
		this.tripService = tripService;
	}
	
	
	@PostMapping("")
	public ResponseEntity<ApiResponse<Long>> planeCreate(
			@UserId String userId,
			@Valid @RequestBody TripCreateRequest tripCreateRequest
			) {
		
		long tripId = tripService.createPlane(userId, tripCreateRequest);
		return ResponseEntity.ok(ApiResponse.success(tripId, "여행 생성에 성공했습니다."));
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
	
	
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<PageResponse<TripSearchResponse>>> tripSearch(
			@UserId String userId,
			
		    @RequestParam(required = false, defaultValue = "1") @Min(1) int page,
		    @RequestParam(required = false, defaultValue = "12") @Min(1) int size,
		    @RequestParam(required = false, defaultValue = "createdDate") 
		    @Pattern(regexp = "^(createdDate)$") String sortBy,
		    @RequestParam(required = false, defaultValue = "DESC") 
		    @Pattern(regexp = "^(ASC|DESC)$") String sortDirection,
		   
		    @RequestParam(required = false) Long regionId,
		    @RequestParam(required = false) @Pattern(regexp = "^(임시저장|저장)$") String state,
		    @RequestParam(required = false) @Min(1) @Max(6) Integer accompanyNum,
		    @RequestParam(required = false) @Min(1) Integer tripDays,
		    @RequestParam(required = false) Boolean isLiked,
		    @RequestParam(required = false) Boolean isReviewed
	) {
		
		PageRequest pageRequest = new PageRequest();
		pageRequest.setPage(page);
		pageRequest.setSize(size);
		pageRequest.setSortBy(sortBy);
		pageRequest.setSortDirection(sortDirection);
		
		TripSearchRequest tripSearchRequest = new TripSearchRequest();
		tripSearchRequest.setRegionId(regionId);
		tripSearchRequest.setState(state);
		tripSearchRequest.setAccompanyNum(accompanyNum);
		tripSearchRequest.setTripDays(tripDays);
		tripSearchRequest.setIsLiked(isLiked);
		tripSearchRequest.setIsReviewed(isReviewed);
		tripSearchRequest.setPageRequest(pageRequest);

		PageResponse<TripSearchResponse> pageResponse = tripService.getTripList(userId, tripSearchRequest);
		return ResponseEntity.ok(ApiResponse.success(pageResponse, "여행 목록을 불러왔습니다."));
	}

	
}
