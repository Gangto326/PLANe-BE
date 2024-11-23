package com.plane.tripMap.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.plane.tripMap.dto.TripMapCreateRequest;
import com.plane.tripMap.dto.TripMapDetailResponse;
import com.plane.tripMap.dto.TripMapListResponse;
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


	@Override
	public boolean existsMapByUserIdAndRegionId(String userId, Integer regionId) {

		return tripMapMapper.existsMapByUserIdAndRegionId(userId, regionId);
	}


	@Override
	public List<TripMapListResponse> selectAllTripMapByUserId(String userId) {
		
		return tripMapMapper.selectAllTripMapByUserId(userId);
	}


	@Override
	public List<TripMapDetailResponse> selectAllTripMapDetail(String userId, Integer regionId) {
		
		return tripMapMapper.selectAllTripMapDetail(userId, regionId);
	}


	
	
	
	
}
