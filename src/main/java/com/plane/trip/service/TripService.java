package com.plane.trip.service;

import com.plane.trip.dto.TripCreateRequest;
import com.plane.trip.dto.TripDetailResponse;
import com.plane.trip.dto.TripResponse;
import com.plane.trip.dto.TripUpdateRequest;

import jakarta.validation.Valid;

public interface TripService {

	long createPlane(String userId, TripCreateRequest tripCreateRequest);

	boolean deletePlane(String userId, Long tripId);

	boolean updatePlane(String userId, @Valid TripUpdateRequest tripUpdateRequest);

	TripDetailResponse getPlane(String userId, Long tripId);

}
