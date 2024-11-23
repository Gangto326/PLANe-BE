package com.plane.trip.service;

import com.plane.common.dto.PageResponse;
import com.plane.trip.dto.TripCreateRequest;
import com.plane.trip.dto.TripDetailResponse;
import com.plane.trip.dto.TripResponse;
import com.plane.trip.dto.TripSearchRequest;
import com.plane.trip.dto.TripSearchResponse;
import com.plane.trip.dto.TripUpdateRequest;
import com.plane.trip.dto.UpcomingTripResponse;

import jakarta.validation.Valid;

public interface TripService {

	long createPlane(String userId, TripCreateRequest tripCreateRequest);

	boolean deletePlane(String userId, Long tripId);

	boolean updatePlane(String userId, TripUpdateRequest tripUpdateRequest);

	TripDetailResponse getPlane(String userId, Long tripId);

	PageResponse<TripSearchResponse> getTripList(String userId, TripSearchRequest tripSearchRequest);

	UpcomingTripResponse getUpcomingTrip(String userId);

}
