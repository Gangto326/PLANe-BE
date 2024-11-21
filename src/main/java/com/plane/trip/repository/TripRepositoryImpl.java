package com.plane.trip.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.plane.trip.dto.TripCreateRequest;
import com.plane.trip.dto.TripResponse;
import com.plane.trip.dto.TripUpdateRequest;
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

		return tripMapper.insertTrip(userId, tripCreateRequest);
	}
	
	@Override
	public int insertTripPlan(TripPlanDto tripPlanDto) {

		return tripMapper.insertTripPlan(tripPlanDto);
	}
	
	@Override
	public int deleteTripStyle(String userId) {
		
		return tripMapper.deleteTripStyle(userId);
	}

	@Override
	public int deleteTripThema(String userId) {
		
		return tripMapper.deleteTripThema(userId);
	}

	@Override
	public int insertTripStyle(String userId, List<Integer> tripStyle) {
		
		return tripMapper.insertTripStyle(userId, tripStyle);
	}

	@Override
	public int insertTripThema(String userId, List<Integer> tripThema) {
		
		return tripMapper.insertTripThema(userId, tripThema);
	}


	@Override
	public int deletePlane(String userId, Long tripId) {

		return tripMapper.updatePlaneDeletedByTripId(userId, tripId);
	}


	@Override
	public boolean existsTripByTripId(Long tripId) {

		return tripMapper.existsTripByTripId(tripId);
	}


	@Override
	public TripResponse selectTripDetail(Long tripId) {

		return tripMapper.selectTripDetail(tripId);
	}


	@Override
	public boolean existsUserByIdAndTripId(String userId, Long tripId) {

		return tripMapper.existsUserByIdAndTripId(userId, tripId);
	}


	@Override
	public boolean checkUpdatePermission(String userId, Long tripId) {

		return tripMapper.checkUpdatePermission(userId, tripId);
	}


	@Override
	public int deleteTripThemaByTripId(Long tripId) {

		return tripMapper.deleteTripThemaByTripId(tripId);
	}


	@Override
	public int insertTripThemaByTripId(Long tripId, List<Integer> tripThema) {

		return tripMapper.insertTripThemaByTripId(tripId, tripThema);
	}


	@Override
	public int deleteTripPlans(Long tripId) {

		return tripMapper.deleteTripPlans(tripId);
	}


	@Override
	public int updatePlane(String userId, TripUpdateRequest tripUpdateRequest) {

		return tripMapper.updatePlane(userId, tripUpdateRequest);
	}


	@Override
	public Integer selectAccompanyNum(String userId, Long tripId) {
		
		return tripMapper.selectAccompanyNum(userId, tripId);
	}
	
}
