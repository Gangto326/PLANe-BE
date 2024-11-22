package com.plane.tripMap.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.tripMap.repository.TripMapRepository;

@Service
@Transactional
public class TripMapServiceImpl implements TripMapService {

	private final TripMapRepository tripMapRepository;

	public TripMapServiceImpl(TripMapRepository tripMapRepository) {
		this.tripMapRepository = tripMapRepository;
	}
	
	
	
	
}
