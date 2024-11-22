package com.plane.afterTrip.repository;

import java.util.List;

import com.plane.afterTrip.domain.AfterPic;
import com.plane.afterTrip.dto.TripDayDto;

public interface AfterTripRepository {

	int insertAfterTrip(Long tripId, TripDayDto tripDayDto);

	int insertAfterPic(List<AfterPic> fileList);

}
