package com.plane.afterTrip.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.plane.afterTrip.domain.AfterPic;
import com.plane.afterTrip.dto.AfterTripCreateRequest;
import com.plane.afterTrip.dto.AfterTripFileDto;
import com.plane.afterTrip.dto.TripDayDto;
import com.plane.afterTrip.repository.AfterTripRepository;
import com.plane.common.exception.custom.CreationFailedException;
import com.plane.common.exception.custom.TripNotFoundException;
import com.plane.common.service.S3Service;
import com.plane.trip.repository.TripRepository;

@Service
@Transactional
public class AfterTripServiceImpl implements AfterTripService {

	private final AfterTripRepository afterTripRepository;
	private final TripRepository tripRepository;
	private final S3Service s3Service;
	
	@Autowired
	public AfterTripServiceImpl(AfterTripRepository afterTripRepository, TripRepository tripRepository, S3Service s3Service) {
		this.afterTripRepository = afterTripRepository;
		this.tripRepository = tripRepository;
		this.s3Service = s3Service;
	}
	

	@Override
	public boolean createAfterTrip(String userId, AfterTripCreateRequest afterTripCreateRequest) {
		
		List<TripDayDto> tripDayDtoList = afterTripCreateRequest.getTripDayDtoList();
		
		if (!tripRepository.existsTripByIdAndTripId(userId, afterTripCreateRequest.getTripId())) {
			throw new TripNotFoundException("해당 여행을 찾을 수 없습니다.");
		}
		
		List<AfterPic> fileList = new ArrayList<>();
		for (TripDayDto tripDayDto: tripDayDtoList) {
			
			if (afterTripRepository.insertAfterTrip(afterTripCreateRequest.getTripId(), tripDayDto) != 1) {
				throw new CreationFailedException("여행 정보 삽입 중 오류가 발생했습니다.");
			}
			
			List<MultipartFile> files = tripDayDto.getFiles();
			if (files != null && !files.isEmpty()) {
				
				List<String> urls = s3Service.uploadFiles(files);
				for (String url: urls) {
					
					AfterPic afterPic = new AfterPic();
					afterPic.setAfterTripId(tripDayDto.getAfterTripid());
					afterPic.setAfterPictureUrl(url);
					fileList.add(afterPic);
				}
			}
		}
		
		
		if (afterTripRepository.insertAfterPic(fileList) != fileList.size()) {
			
			// 오류 발생 시 올렸던 모든 사진 삭제
			for (AfterPic afterPic: fileList) {
				s3Service.deleteFile(afterPic.getAfterPictureUrl());
			}
			
			throw new CreationFailedException("여행 정보 사진 삽입 중 오류가 발생했습니다.");
		}
		
		return true;
	}
	
	
}
