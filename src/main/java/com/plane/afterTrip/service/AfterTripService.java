package com.plane.afterTrip.service;

import java.util.List;

import com.plane.afterTrip.dto.AfterTripCreateRequest;
import com.plane.afterTrip.dto.AfterTripResponse;

public interface AfterTripService {

	boolean createAfterTrip(String userId, AfterTripCreateRequest afterTripCreateRequest);

	List<AfterTripResponse> getAfterTrip(String userId, Long tripId);

}
