package com.plane.tripMap.repository;

import java.util.List;

import com.plane.tripMap.dto.TripMapCreateRequest;
import com.plane.tripMap.dto.TripMapListResponse;

public interface TripMapRepository {

	int insertTripMap(String userId, TripMapCreateRequest tripMapCreateRequest);

	boolean existsMapByUserIdAndRegionId(String userId, Integer regionId);

	List<TripMapListResponse> selectAllTripMapByUserId(String userId);

}
