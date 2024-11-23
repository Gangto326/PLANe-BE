package com.plane.tripMap.service;

import com.plane.tripMap.dto.TripMapCreateRequest;

public interface TripMapService {

	boolean createTripMap(String userId, TripMapCreateRequest tripMapCreateRequest);

}
