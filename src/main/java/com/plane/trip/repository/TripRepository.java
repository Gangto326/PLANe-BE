package com.plane.trip.repository;

import java.util.List;

import com.plane.trip.domain.Plane;
import com.plane.trip.dto.TripCreateRequest;
import com.plane.trip.dto.TripResponse;
import com.plane.trip.dto.TripSearchRequest;
import com.plane.trip.dto.TripSearchResponse;
import com.plane.trip.dto.TripUpdateRequest;
import com.plane.trip.dto.UpcomingTripResponse;
import com.plane.trip.dto.TripPlanDto;

public interface TripRepository {

	int insertTrip(String userId, TripCreateRequest tripCreateRequest);

	int insertTripPlan(TripPlanDto tripPlanDto);
	
	int deleteTripStyle(String userId);

	int deleteTripThema(String userId);

	int insertTripStyle(String userId, List<Integer> tripStyle);

	int insertTripThema(String userId, List<Integer> tripThema);

	int deletePlane(String userId, Long tripId);
	
	boolean existsTripByTripId(Long tripId);

	TripResponse selectTripDetail(Long tripId);

	boolean existsTripByIdAndTripId(String userId, Long tripId);

	boolean checkUpdatePermission(String userId, Long tripId);

	int deleteTripThemaByTripId(Long tripId);

	int insertTripThemaByTripId(Long tripId, List<Integer> tripThema);

	int deleteTripPlans(Long tripId);

	int updatePlane(String userId, TripUpdateRequest tripUpdateRequest);

	Integer selectAccompanyNum(String userId, Long tripId);
	
	Plane selectPlaneByUserIdAndTripId(String userId, Long tripId);

	long countAllTrips(String userId, TripSearchRequest tripSearchRequest);
	
	List<TripSearchResponse> selectTripsByPageRequest(String userId, TripSearchRequest tripSearchRequest);

	UpcomingTripResponse selectUpcomingTrip(String userId);

	int updatePlaneIsReviewed(String userId, Long tripId);

	int updateAccompanyNum(Long tripId, Integer accompanyNum);

	long countAllAccompanyTrips(String userId, TripSearchRequest tripSearchRequest);

	List<TripSearchResponse> selectAccompanyTripsByPageRequest(String userId, TripSearchRequest tripSearchRequest);
}
