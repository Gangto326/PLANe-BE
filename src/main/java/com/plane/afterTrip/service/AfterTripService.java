package com.plane.afterTrip.service;

import java.util.List;

import com.plane.afterTrip.dto.AfterTripCreateRequest;
import com.plane.afterTrip.dto.AfterTripResponse;
import com.plane.afterTrip.dto.AfterTripUpdateRequest;

public interface AfterTripService {

	boolean createAfterTrip(String userId, AfterTripCreateRequest afterTripCreateRequest);

	List<AfterTripResponse> getAfterTrip(String userId, Long tripId);

	boolean updateAfterTrip(String userId, AfterTripUpdateRequest afterTripUpdateRequest);

}
