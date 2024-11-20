package com.plane.trip.service;

import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.trip.dto.CoordinateDto;
import com.plane.trip.dto.TripCreateRequest;
import com.plane.trip.dto.TripPlanDto;
import com.plane.trip.repository.TripRepository;

@Service
@Transactional
public class TripServiceImpl implements TripService {

	private final TripRepository tripRepository;
	private final GeometryFactory geometryFactory = new GeometryFactory();
	
	@Autowired
	public TripServiceImpl(TripRepository tripRepository) {
		this.tripRepository = tripRepository;
	}

	
	@Override
	public boolean createPlane(String userId, TripCreateRequest tripCreateRequest) {
		
		// 먼저 Trip 정보 저장하고 tripId 받아오기
        if (tripRepository.insertTrip(userId, tripCreateRequest) != 1) {
        	
        }
        
        // 각 일자별 좌표 저장
        saveDayPlans(tripCreateRequest.getTripId(), 1, tripCreateRequest.getDay1());
        
        if (tripCreateRequest.getTripDays() >= 2) {
            saveDayPlans(tripCreateRequest.getTripId(), 2, tripCreateRequest.getDay2());
        }
        
        if (tripCreateRequest.getTripDays() >= 3) {
            saveDayPlans(tripCreateRequest.getTripId(), 3, tripCreateRequest.getDay3());
        }
        
        return true;
	}
	
	
	private void saveDayPlans(Long tripId, int day, List<CoordinateDto> coordinates) {
		
        if (coordinates == null || coordinates.isEmpty()) {
            return;
        }

        for (CoordinateDto coord : coordinates) {
        	
            TripPlanDto tripPlanDto = new TripPlanDto();
            tripPlanDto.setTripId(tripId);
            tripPlanDto.setTripDay(day);
            tripPlanDto.setTripOrder(coord.getTripOrder());
            tripPlanDto.setTitle(coord.getTitle());
            tripPlanDto.setAddress(coord.getAddr1());
            
            // Point 객체 생성
            Point point = geometryFactory.createPoint(
                new Coordinate(coord.getMapx(), coord.getMapy())
            );
            
            tripPlanDto.setPoint(point);
            
            if (tripRepository.insert(tripPlanDto) != 1) {
            	
            }
        }
    }
}
