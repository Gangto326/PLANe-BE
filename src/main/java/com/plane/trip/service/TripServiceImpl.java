package com.plane.trip.service;

import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.accompany.repository.AccompanyRepository;
import com.plane.common.exception.custom.CreationFailedException;
import com.plane.common.exception.custom.InvalidParameterException;
import com.plane.common.exception.custom.UpdateFailedException;
import com.plane.trip.dto.CoordinateDto;
import com.plane.trip.dto.TripCreateRequest;
import com.plane.trip.dto.TripPlanDto;
import com.plane.trip.repository.TripRepository;

@Service
@Transactional
public class TripServiceImpl implements TripService {

	private final TripRepository tripRepository;
	private final AccompanyRepository accompanyRepository;
	private final GeometryFactory geometryFactory = new GeometryFactory();
	
	@Autowired
	public TripServiceImpl(TripRepository tripRepository, AccompanyRepository accompanyRepository) {
		this.tripRepository = tripRepository;
		this.accompanyRepository = accompanyRepository;
	}

	
	@Override
	public boolean createPlane(String userId, TripCreateRequest tripCreateRequest) {
		
		// 먼저 Trip 정보 저장하고 tripId 받아오기
        if (tripRepository.insertTrip(userId, tripCreateRequest) != 1) {
        	throw new CreationFailedException("여행 생성에 실패하였습니다.");
        }
        
        // 각 일자별 좌표 저장
        saveDayPlans(tripCreateRequest.getTripId(), 1, tripCreateRequest.getDay1());
        
        if (tripCreateRequest.getTripDays() >= 2) {
            saveDayPlans(tripCreateRequest.getTripId(), 2, tripCreateRequest.getDay2());
        }
        
        if (tripCreateRequest.getTripDays() >= 3) {
            saveDayPlans(tripCreateRequest.getTripId(), 3, tripCreateRequest.getDay3());
        }
        
        if (tripCreateRequest.getTripThema() != null && !tripCreateRequest.getTripThema().isEmpty()) {
        	tripRepository.insertTripThema(userId, tripCreateRequest.getTripThema());
        }
        
        if (accompanyRepository.insertAccompany(tripCreateRequest.getTripId(), userId, "팀장") == 1) {
        	return true;
        }
        
        throw new CreationFailedException("여행 생성에 실패하였습니다.");
	}
	
	
	private void saveDayPlans(Long tripId, int day, List<CoordinateDto> coordinates) {
		
        if (coordinates == null || coordinates.isEmpty()) {
        	throw new InvalidParameterException("각 일자별 하나 이상의 계획이 존재해야합니다.");
        }

        for (CoordinateDto coord : coordinates) {
        	
            TripPlanDto tripPlanDto = new TripPlanDto();
            tripPlanDto.setTripId(tripId);
            tripPlanDto.setTripDay(day);
            tripPlanDto.setTripOrder(coord.getTripOrder());
            tripPlanDto.setTitle(coord.getTitle());
            tripPlanDto.setMemo(coord.getMemo());
            tripPlanDto.setAddress(coord.getAddr1());
            tripPlanDto.setMapx(coord.getMapx());
            tripPlanDto.setMapy(coord.getMapy());
            
            if (tripRepository.insertTripPlan(tripPlanDto) != 1) {
            	throw new UpdateFailedException("여행 계획 추가에 실패하였습니다.");
            }
        }
    }
	
	
}
