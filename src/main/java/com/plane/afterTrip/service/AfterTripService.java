package com.plane.afterTrip.service;

import com.plane.afterTrip.dto.AfterTripCreateRequest;

public interface AfterTripService {

	boolean createAfterTrip(String userId, AfterTripCreateRequest afterTripCreateRequest);

}
