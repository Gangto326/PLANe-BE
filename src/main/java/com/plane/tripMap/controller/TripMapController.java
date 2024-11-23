package com.plane.tripMap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plane.afterTrip.dto.AfterTripCreateRequest;
import com.plane.common.annotation.UserId;
import com.plane.common.response.ApiResponse;
import com.plane.trip.dto.UpcomingTripResponse;
import com.plane.tripMap.dto.TripMapCreateRequest;
import com.plane.tripMap.dto.TripMapDetailResponse;
import com.plane.tripMap.dto.TripMapListResponse;
import com.plane.tripMap.service.TripMapService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tripmap")
public class TripMapController {
	
	private final TripMapService tripMapService;

	@Autowired
	public TripMapController(TripMapService tripMapService) {
		this.tripMapService = tripMapService;
	}
	
	
	@PostMapping("")
	public ResponseEntity<ApiResponse<Boolean>> tripMapCreate(
		    @UserId String userId,
		    @Valid @ModelAttribute TripMapCreateRequest tripMapCreateRequest
			) {
		
		tripMapService.createTripMap(userId, tripMapCreateRequest);
		return ResponseEntity.ok(ApiResponse.success(true, "여행 지도 생성에 성공하였습니다."));
	}
	
	
	@GetMapping("/list")
	public ResponseEntity<ApiResponse<List<TripMapListResponse>>> tripMapList(
		    @UserId String userId
			) {
		
		List<TripMapListResponse> tripMapList = tripMapService.getTripMapList(userId);
		return ResponseEntity.ok(ApiResponse.success(tripMapList, "여행 지도를 성공적으로 가져왔습니다."));
	}
	
	
	@GetMapping("/{mapId}")
	public ResponseEntity<ApiResponse<List<TripMapDetailResponse>>> tripMapDetail(
		    @UserId String userId,
		    @PathVariable Integer regionId
			) {
		
		List<TripMapDetailResponse> tripMapDetailList = tripMapService.getTripMapDetail(userId, regionId);
		return ResponseEntity.ok(ApiResponse.success(tripMapDetailList, "해당 지역의 정보를 성공적으로 가져왔습니다."));
	}
	
}
