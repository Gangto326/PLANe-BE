package com.plane.tripMap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
