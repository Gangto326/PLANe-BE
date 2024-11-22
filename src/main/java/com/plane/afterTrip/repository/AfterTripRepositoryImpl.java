package com.plane.afterTrip.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.plane.afterTrip.domain.AfterPic;
import com.plane.afterTrip.dto.AfterTripResponse;
import com.plane.afterTrip.dto.TripDayDto;
import com.plane.afterTrip.mapper.AfterTripMapper;

@Repository
public class AfterTripRepositoryImpl implements AfterTripRepository {
	
	private final AfterTripMapper afterTripMapper;
	
	@Autowired
	public AfterTripRepositoryImpl(AfterTripMapper afterTripMapper) {
		this.afterTripMapper = afterTripMapper;
	}

	
	@Override
	public int insertAfterTrip(Long tripId, TripDayDto tripDayDto) {
		
		return afterTripMapper.insertAfterTrip(tripId, tripDayDto);
	}


	@Override
	public int insertAfterPic(List<AfterPic> fileList) {

		return afterTripMapper.insertAfterPic(fileList);
	}


	@Override
	public List<AfterTripResponse> getAfterTripWithPics(Long tripId) {

		return afterTripMapper.getAfterTripWithPics(tripId);
	}
	
	
	
	
}
