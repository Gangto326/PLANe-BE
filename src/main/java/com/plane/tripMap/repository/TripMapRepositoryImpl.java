package com.plane.tripMap.repository;

import org.springframework.stereotype.Repository;

import com.plane.tripMap.dto.TripMapCreateRequest;
import com.plane.tripMap.mapper.TripMapMapper;

@Repository
public class TripMapRepositoryImpl implements TripMapRepository {

	private final TripMapMapper tripMapMapper;

	public TripMapRepositoryImpl(TripMapMapper tripMapMapper) {
		this.tripMapMapper = tripMapMapper;
	}

	
	@Override
	public int insertTripMap(String userId, TripMapCreateRequest tripMapCreateRequest) {
		
		return tripMapMapper.insertTripMap(userId, tripMapCreateRequest);
	}
	
	
	
}
