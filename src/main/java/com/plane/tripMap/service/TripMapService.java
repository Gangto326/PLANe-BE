package com.plane.tripMap.service;

import java.util.List;

import com.plane.tripMap.dto.TripMapCreateRequest;
import com.plane.tripMap.dto.TripMapDetailResponse;
import com.plane.tripMap.dto.TripMapListResponse;

public interface TripMapService {

	boolean createTripMap(String userId, TripMapCreateRequest tripMapCreateRequest);

	List<TripMapListResponse> getTripMapList(String userId);

	List<TripMapDetailResponse> getTripMapDetail(String userId, Integer regionId);

}
