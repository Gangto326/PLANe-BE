package com.plane.trip.repository;

import java.util.List;

import com.plane.trip.dto.TripCreateRequest;
import com.plane.trip.dto.TripPlanDto;

public interface TripRepository {

	int insertTrip(String userId, TripCreateRequest tripCreateRequest);

	int insertTripPlan(TripPlanDto tripPlanDto);
	
	int deleteTripStyle(String userId);

	int deleteTripThema(String userId);

	int insertTripStyle(String userId, List<Integer> tripStyle);

	int insertTripThema(String userId, List<Integer> tripThema);

}
