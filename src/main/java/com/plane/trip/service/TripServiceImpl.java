package com.plane.trip.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.accompany.repository.AccompanyRepository;
import com.plane.article.dto.ArticleResponse;
import com.plane.common.dto.PageInfo;
import com.plane.common.dto.PageRequest;
import com.plane.common.dto.PageResponse;
import com.plane.common.dto.ScheduledNotification;
import com.plane.common.exception.custom.CreationFailedException;
import com.plane.common.exception.custom.InvalidParameterException;
import com.plane.common.exception.custom.TripNotFoundException;
import com.plane.common.exception.custom.UnauthorizedException;
import com.plane.common.exception.custom.UpdateFailedException;
import com.plane.common.exception.custom.UserNotFoundException;
import com.plane.common.service.S3Service;
import com.plane.notification.service.NotificationSchedulerService;
import com.plane.notification.service.NotificationService;
import com.plane.trip.domain.TripThema;
import com.plane.trip.dto.CoordinateDto;
import com.plane.trip.dto.TripCreateRequest;
import com.plane.trip.dto.TripDetailResponse;
import com.plane.trip.dto.TripResponse;
import com.plane.trip.dto.TripSearchRequest;
import com.plane.trip.dto.TripSearchResponse;
import com.plane.trip.dto.TripPlanDto;
import com.plane.trip.dto.TripUpdateRequest;
import com.plane.trip.repository.TripRepository;
import com.plane.user.repository.AuthRepository;

import jakarta.validation.Valid;

@Service
@Transactional
public class TripServiceImpl implements TripService {

	private final TripRepository tripRepository;
	private final AccompanyRepository accompanyRepository;
	private final AuthRepository authRepository;
	private final NotificationSchedulerService notificationSchedulerService;
	private final NotificationService notificationService;
	private final S3Service s3Service;
	private final GeometryFactory geometryFactory = new GeometryFactory();
	
	@Autowired
	public TripServiceImpl(TripRepository tripRepository, AccompanyRepository accompanyRepository, AuthRepository authRepository, NotificationSchedulerService notificationSchedulerService, NotificationService notificationService, S3Service s3Service) {
		this.tripRepository = tripRepository;
		this.accompanyRepository = accompanyRepository;
		this.authRepository = authRepository;
		this.notificationSchedulerService = notificationSchedulerService;
		this.notificationService = notificationService;
		this.s3Service = s3Service;
	}

	
	@Override
	public long createPlane(String userId, TripCreateRequest tripCreateRequest) {
		
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
        	tripRepository.insertTripThemaByTripId(tripCreateRequest.getTripId(), tripCreateRequest.getTripThema());
        }
        
        // 동행 정보에 팀장 정보 추가
        if (accompanyRepository.insertAccompany(tripCreateRequest.getTripId(), userId, "팀장") == 1) {
        	
        	// 후기 알림 발송 예약
//        	notificationSchedulerService.scheduleTripReviewNotification(tripCreateRequest.getTripId(), tripCreateRequest.getTripName(), tripCreateRequest.getArrivedDate(), userId);
        	
        	ScheduledNotification notification = new ScheduledNotification();
            notification.setTripId(tripCreateRequest.getTripId());
            notification.setUserId(userId);
            notification.setTitle(tripCreateRequest.getTripName());
            notification.setScheduledTime(tripCreateRequest.getArrivedDate().plusDays(1).atStartOfDay());
        	
        	notificationService.save(notification);
        	
        	return tripCreateRequest.getTripId();
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
            tripPlanDto.setUrl(s3Service.uploadFile(coord.getFile()));
            
            if (tripRepository.insertTripPlan(tripPlanDto) != 1) {
            	throw new UpdateFailedException("여행 계획 추가에 실패하였습니다.");
            }
        }
    }


	@Override
	public boolean deletePlane(String userId, Long tripId) {
		
		if (tripRepository.deletePlane(userId, tripId) == 1) {
			return true;
		}
		
		throw new UpdateFailedException("여행 삭제에 실패했습니다.");
	}


	@Override
	public boolean updatePlane(String userId, TripUpdateRequest tripUpdateRequest) {
		
		if (!tripRepository.existsTripByTripId(tripUpdateRequest.getTripId())) {
			throw new TripNotFoundException("해당 여행이 존재하지 않습니다.");
		}
		
		// 여행 수정 권한 확인 ('일반'은 권한 없음)
		if (!tripRepository.checkUpdatePermission(userId, tripUpdateRequest.getTripId())) {
			throw new UnauthorizedException("수정 권한이 없습니다.");
		}
		
		// PLANe 업데이트
		tripRepository.updatePlane(userId, tripUpdateRequest);
		
		// Delete & Insert
		tripRepository.deleteTripPlans(tripUpdateRequest.getTripId());
		
		// 각 일자별 좌표 저장
        saveDayPlans(tripUpdateRequest.getTripId(), 1, tripUpdateRequest.getDay1());
        
        if (tripUpdateRequest.getTripDays() >= 2) {
            saveDayPlans(tripUpdateRequest.getTripId(), 2, tripUpdateRequest.getDay2());
        }
        
        if (tripUpdateRequest.getTripDays() >= 3) {
            saveDayPlans(tripUpdateRequest.getTripId(), 3, tripUpdateRequest.getDay3());
        }
        
        if (tripUpdateRequest.getTripThema() != null && !tripUpdateRequest.getTripThema().isEmpty()) {
        	
        	tripRepository.deleteTripThemaByTripId(tripUpdateRequest.getTripId());
        	tripRepository.insertTripThemaByTripId(tripUpdateRequest.getTripId(), tripUpdateRequest.getTripThema());
        }
        
        try {
        	ScheduledNotification notification = new ScheduledNotification();
            notification.setTripId(tripUpdateRequest.getTripId());
            notification.setUserId(userId);
            notification.setTitle(tripUpdateRequest.getTripName());
            notification.setScheduledTime(tripUpdateRequest.getArrivedDate().plusDays(1).atStartOfDay());
        	
        	notificationService.save(notification);
        	notificationService.updateStatus(tripUpdateRequest.getTripId());
        } catch (Exception e) {
        	return false;
		}
    	
		return true;
	}


	@Override
	public TripDetailResponse getPlane(String userId, Long tripId) {
		
		if (!authRepository.existsUserById(userId)) {
			throw new UserNotFoundException("유저가 존재하지 않습니다.");
		}
		
		TripResponse tripResponse = tripRepository.selectTripDetail(tripId);
		
		if (tripResponse == null) {
			throw new TripNotFoundException("해당 여행을 찾을 수 없습니다.");
		}
		
		TripDetailResponse tripDetailResponse = new TripDetailResponse();
		
		tripDetailResponse.setTripId(tripResponse.getTripId());
		tripDetailResponse.setRegionId(tripResponse.getRegionId());
		tripDetailResponse.setTripName(tripResponse.getTripName());
		tripDetailResponse.setDepartureDate(tripResponse.getDepartureDate());
		tripDetailResponse.setArrivedDate(tripResponse.getArrivedDate());
		tripDetailResponse.setState(tripResponse.getState());
		tripDetailResponse.setAccompanyNum(tripResponse.getAccompanyNum());
		tripDetailResponse.setTripDays(tripResponse.getTripDays());
		tripDetailResponse.setLiked(tripResponse.isLiked());
		tripDetailResponse.setPublic(tripResponse.isPublic());
		tripDetailResponse.setReviewed(tripResponse.isReviewed());
		tripDetailResponse.setThemaList(tripResponse.getThemaList());
		
		if (tripResponse.getPlanList() == null) {
		    throw new TripNotFoundException("해당 여행의 정보를 찾을 수 없습니다.");
		}
		
		List<TripPlanDto> planList = tripResponse.getPlanList();
		
		List<TripPlanDto> day1 = new ArrayList<>();
		List<TripPlanDto> day2 = new ArrayList<>();
		List<TripPlanDto> day3 = new ArrayList<>();
		
		for (TripPlanDto plan: planList) {
			switch (plan.getTripDay()) {
				case 1 -> day1.add(plan);
				case 2 -> day2.add(plan);
				case 3 -> day3.add(plan);
			}
		}
		
		tripDetailResponse.setDay1(day1);
		tripDetailResponse.setDay2(day2);
		tripDetailResponse.setDay3(day3);
		
		return tripDetailResponse;
	}


	@Override
	public PageResponse<TripSearchResponse> getTripList(String userId, TripSearchRequest tripSearchRequest) {
		
		long total = tripRepository.countAllTrips(userId, tripSearchRequest);
		
		List<TripSearchResponse> tripList = tripRepository.selectTripsByPageRequest(userId, tripSearchRequest);
        
		PageRequest pageRequest = tripSearchRequest.getPageRequest();
        // 페이지 정보 생성
        int totalPages = (int) Math.ceil((double) total / pageRequest.getSize());
        
        // Front를 위한 메타 데이터 생성
        PageInfo pageInfo = new PageInfo(
            pageRequest.getPage(),
            pageRequest.getSize(),
            totalPages,
            total
        );
        
        return new PageResponse<>(tripList, pageInfo);
	}
	
	
}
