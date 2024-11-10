package com.plane.trip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plane.trip.service.TripService;

@RestController
@RequestMapping("/api/plane")
public class TripController {
	
	private TripService tripService;

	@Autowired
	public TripController(TripService tripService) {
		this.tripService = tripService;
	}
	
	
	
}
