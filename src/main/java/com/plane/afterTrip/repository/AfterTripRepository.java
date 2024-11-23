package com.plane.afterTrip.repository;

import java.util.List;

import com.plane.afterTrip.domain.AfterPic;
import com.plane.afterTrip.dto.AfterTripResponse;
import com.plane.afterTrip.dto.TripDayDto;
import com.plane.afterTrip.dto.TripDayUpdateDto;

public interface AfterTripRepository {

	int insertAfterTrip(Long tripId, TripDayDto tripDayDto);

	int insertAfterPic(List<AfterPic> fileList);

	List<AfterTripResponse> getAfterTripWithPics(Long tripId);

	int deleteAllPic(List<Long> afterTripIdList);

	int deleteAfterTrip(Long tripId);

	int updateAfterTrip(Long tripId, TripDayUpdateDto tripDayUpdateDto);

}
