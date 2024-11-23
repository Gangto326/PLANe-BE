package com.plane.tripMap.repository;

import com.plane.tripMap.dto.TripMapCreateRequest;

public interface TripMapRepository {

	int insertTripMap(String userId, TripMapCreateRequest tripMapCreateRequest);

}
