package com.plane.trip.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.plane.trip.mapper.TripMapper;

@Repository
public class TripRepositoryImpl implements TripRepository {
	
	private TripMapper tripMapper;

	@Autowired
	public TripRepositoryImpl(TripMapper tripMapper) {
		this.tripMapper = tripMapper;
	}
	
	
}
