package com.plane.tripMap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plane.common.annotation.UserId;
import com.plane.common.response.ApiResponse;
import com.plane.trip.dto.UpcomingTripResponse;
import com.plane.tripMap.service.TripMapService;

@RestController
@RequestMapping("/api/tripmap")
public class TripMapController {
	
	private final TripMapService tripMapService;

	@Autowired
	public TripMapController(TripMapService tripMapService) {
		this.tripMapService = tripMapService;
	}
	
	
	
	

}
