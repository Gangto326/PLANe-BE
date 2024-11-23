package com.plane.tripMap.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.common.exception.custom.CreationFailedException;
import com.plane.common.exception.custom.DuplicateException;
import com.plane.common.exception.custom.TripNotFoundException;
import com.plane.common.service.S3Service;
import com.plane.trip.repository.TripRepository;
import com.plane.tripMap.dto.TripMapCreateRequest;
import com.plane.tripMap.dto.TripMapDetailResponse;
import com.plane.tripMap.dto.TripMapListResponse;
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
		
		if (tripMapRepository.existsMapByUserIdAndRegionId(userId, tripMapCreateRequest.getRegionId())) {
			throw new DuplicateException("해당 지역을 대표하는 저장된 지도가 존재합니다.");
		}
		
		if (tripMapCreateRequest.getFile() != null && !tripMapCreateRequest.getFile().isEmpty()) {
			tripMapCreateRequest.setMapPictureUrl(s3Service.uploadFile(tripMapCreateRequest.getFile()));
		}
		
		if (tripMapRepository.insertTripMap(userId, tripMapCreateRequest) == 1) {
			return true;
		}
		
		throw new CreationFailedException("여행 지도 생성에 실패하였습니다.");
	}


	@Override
	public List<TripMapListResponse> getTripMapList(String userId) {
		
		return tripMapRepository.selectAllTripMapByUserId(userId);
	}


	@Override
	public List<TripMapDetailResponse> getTripMapDetail(String userId, Integer regionId) {
		
		if (!tripMapRepository.existsMapByUserIdAndRegionId(userId, regionId)) {
			throw new TripNotFoundException("해당 지역의 여행 정보가 없습니다.");
		}
		
		return tripMapRepository.selectAllTripMapDetail(userId, regionId);
	}
	
	
	
	
}
