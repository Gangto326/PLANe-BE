package com.plane.trip.service;

import com.plane.trip.dto.TripCreateRequest;

public interface TripService {

	boolean createPlane(String userId, TripCreateRequest tripCreateRequest);

}
