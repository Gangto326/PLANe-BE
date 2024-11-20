package com.plane.trip.repository;

import com.plane.trip.dto.TripCreateRequest;
import com.plane.trip.dto.TripPlanDto;

public interface TripRepository {

	int insertTrip(String userId, TripCreateRequest tripCreateRequest);

	int insert(TripPlanDto tripPlan);

}
