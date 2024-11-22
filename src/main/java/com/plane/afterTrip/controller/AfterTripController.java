package com.plane.afterTrip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plane.afterTrip.dto.AfterTripCreateRequest;
import com.plane.afterTrip.dto.AfterTripResponse;
import com.plane.afterTrip.dto.AfterTripUpdateRequest;
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
	public ResponseEntity<ApiResponse<List<AfterTripResponse>>> afterTripDetail(
	    @UserId String userId,
	    @PathVariable Long tripId
	) {
		
		List<AfterTripResponse> afterTripResponseList = afterTripService.getAfterTrip(userId, tripId);
	    return ResponseEntity.ok(ApiResponse.success(afterTripResponseList, "여행 정보를 성공적으로 가져왔습니다."));
	}
	
	
	@PostMapping("")
	public ResponseEntity<ApiResponse<Boolean>> afterTripCreate(
	    @UserId String userId,
	    @ModelAttribute AfterTripCreateRequest afterTripCreateRequest
	) {
		
		afterTripService.createAfterTrip(userId, afterTripCreateRequest);
	    return ResponseEntity.ok(ApiResponse.success(true, "여행 후기 생성에 성공하였습니다."));
	}
	
	
	@PatchMapping("/update")
	public ResponseEntity<ApiResponse<Boolean>> afterTripUpdate(
		    @UserId String userId,
		    @ModelAttribute AfterTripUpdateRequest afterTripUpdateRequest
		) {
			
			afterTripService.updateAfterTrip(userId, afterTripUpdateRequest);
		    return ResponseEntity.ok(ApiResponse.success(true, "여행 후기 수정에 성공하였습니다."));
		}
	
	
}
