package com.plane.trip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.trip.repository.TripRepository;

@Service
@Transactional
public class TripServiceImpl implements TripService {

	private TripRepository tripRepository;
	
	@Autowired
	public TripServiceImpl(TripRepository tripRepository) {
		this.tripRepository = tripRepository;
	}
	
	
}
