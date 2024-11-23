package com.plane.tripMap.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.common.exception.custom.CreationFailedException;
import com.plane.common.exception.custom.TripNotFoundException;
import com.plane.common.service.S3Service;
import com.plane.trip.repository.TripRepository;
import com.plane.tripMap.dto.TripMapCreateRequest;
import com.plane.tripMap.repository.TripMapRepository;

@Service
@Transactional
public class TripMapServiceImpl implements TripMapService {

	private final TripMapRepository tripMapRepository;
	private final TripRepository tripRepository;
	private final S3Service s3Service;

	public TripMapServiceImpl(TripMapRepository tripMapRepository, TripRepository tripRepository, S3Service s3Service) {
		this.tripMapRepository = tripMapRepository;
		this.tripRepository = tripRepository;
		this.s3Service = s3Service;
	}

	
	@Override
	public boolean createTripMap(String userId, TripMapCreateRequest tripMapCreateRequest) {
		
		if (!tripRepository.existsTripByTripId(tripMapCreateRequest.getTripId())) {
			throw new TripNotFoundException("해당하는 여행을 찾을 수 없습니다.");
		}
		
		tripMapCreateRequest.setMapPictureUrl(s3Service.uploadFile(tripMapCreateRequest.getFile()));
		
		if (tripMapRepository.insertTripMap(userId, tripMapCreateRequest) == 1) {
			return true;
		}
		
		throw new CreationFailedException("여행 지도 생성에 실패하였습니다.");
	}
	
	
	
	
}
