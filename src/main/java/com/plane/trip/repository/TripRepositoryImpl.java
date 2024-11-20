package com.plane.trip.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.plane.trip.dto.TripCreateRequest;
import com.plane.trip.dto.TripPlanDto;
import com.plane.trip.mapper.TripMapper;

@Repository
public class TripRepositoryImpl implements TripRepository {
	
	private final TripMapper tripMapper;

	@Autowired
	public TripRepositoryImpl(TripMapper tripMapper) {
		this.tripMapper = tripMapper;
	}

	@Override
	public int insertTrip(String userId, TripCreateRequest tripCreateRequest) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(TripPlanDto tripPlan) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
