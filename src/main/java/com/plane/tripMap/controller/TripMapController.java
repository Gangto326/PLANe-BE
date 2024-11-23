package com.plane.tripMap.controller;

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
	
	
	@PostMapping
	public ResponseEntity<ApiResponse<Boolean>> tripMapCreate(
		    @UserId String userId,
		    @Valid @ModelAttribute TripMapCreateRequest tripMapCreateRequest
			) {
		
		tripMapService.createTripMap(userId, tripMapCreateRequest);
		return ResponseEntity.ok(ApiResponse.success(true, "여행 지도 생성에 성공하였습니다."));
	}
	
	

}
