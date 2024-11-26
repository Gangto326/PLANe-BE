package com.plane.notification.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.plane.common.dto.ScheduledNotification;
import com.plane.common.exception.custom.ArticleNotFoundException;
import com.plane.common.exception.custom.ArticleUpdateException;
import com.plane.common.exception.custom.InvalidParameterException;
import com.plane.common.exception.custom.SendNotificationException;
import com.plane.common.util.NotificationTitleGenerator;
import com.plane.notification.dto.NotificationCreateRequest;
import com.plane.notification.dto.NotificationDetailResponse;
import com.plane.notification.dto.NotificationResponse;
import com.plane.notification.dto.NotificationTarget;
import com.plane.notification.dto.NotificationTargetType;
import com.plane.notification.repository.NotificationRepository;
import com.plane.trip.repository.TripRepository;
import com.plane.user.repository.UserRepository;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

	private final NotificationRepository notificationRepository;
	private final UserRepository userRepository;
	private final TripRepository tripRepository;
	private final NotificationTitleGenerator notificationTitleGenerator;

	@Autowired
	public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository, TripRepository tripRepository, NotificationTitleGenerator notificationTitleGenerator) {
		this.notificationRepository = notificationRepository;
		this.userRepository = userRepository;
		this.tripRepository = tripRepository;
		this.notificationTitleGenerator = notificationTitleGenerator;
	}

	
	@Override
	public List<NotificationResponse> getNotificationList(String userId, String type) {
		
		if (!type.equals("ALL") && !type.equals("UNCONFIRMED")) {
			throw new InvalidParameterException("타입은 미확인, 전체 중 하나여야 합니다.");
		}
		
		List<NotificationResponse> notificationList = notificationRepository.findAllNotifications(userId, type);
		return notificationList;
	}


	@Override
	public int getUnconfirmedCount(String userId) {

		return notificationRepository.countAllUnconfirmed(userId);
	}


	@Override
	public NotificationDetailResponse getNotificationDetail(String userId, Long noId) {

		if (!notificationRepository.existsNotificationByUserIdAndNoId(userId, noId)) {
			throw new ArticleNotFoundException("해당 알림을 찾을 수 없습니다.");
		}
		
		notificationRepository.updateNotificationRead(userId, noId);
		
		NotificationDetailResponse notificationDetailResponse = notificationRepository.getNotificationDetail(userId, noId);
		return notificationDetailResponse;
	}


	@Override
	public boolean deleteNotification(String userId, Long noId) {
		
		if (!notificationRepository.existsNotificationByUserIdAndNoId(userId, noId)) {
			throw new ArticleNotFoundException("해당 알림을 찾을 수 없습니다.");
		}
		
		if (notificationRepository.deleteNotification(userId, noId) == 1) {
			return true;
		}
		
		throw new ArticleUpdateException("알림 삭제에 실패했습니다.");
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW) // 알림이 메인 서비스 로직에 영향을 주지 않도록 새로운 트랜잭션으로 처리
	public boolean createNotification(String receiverId, NotificationCreateRequest notificationCreateRequest) {
		
		NotificationTarget target = new NotificationTarget();
		target.setType(notificationCreateRequest.getType());
		target.setNickname(userRepository.selectUserNicknameByUserId(receiverId));
		target.setContent(notificationCreateRequest.getDetails());
		
		String title = notificationTitleGenerator.generateTitle(target, notificationCreateRequest.getAction());
		notificationCreateRequest.setTitle(title);
		
		if (notificationRepository.insertNotification(receiverId, notificationCreateRequest) == 1) {
			return true;
		}
		
		return false;
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean createNotificationTripReview(String receiverId, NotificationCreateRequest notificationCreateRequest) {
		
		NotificationTarget target = new NotificationTarget();
		target.setType(notificationCreateRequest.getType());
		target.setNickname(userRepository.selectUserNicknameByUserId(receiverId));
		target.setContent(notificationCreateRequest.getDetails());
		
		String title = notificationTitleGenerator.generateTitle(target, notificationCreateRequest.getAction());
		notificationCreateRequest.setTitle(title);
		
		// 여행이 취소되었으면 후기 알림을 보내지 않음
		if (!tripRepository.existsTripByTripId(notificationCreateRequest.getContentId())) {
			return false;
		}
		
		if (notificationRepository.insertNotification(receiverId, notificationCreateRequest) == 1) {
			return true;
		}
		
		return false;
	}


	@Override
	@Async
	@Transactional
	public void save(ScheduledNotification notification) {
		
		notificationRepository.save(notification);
	}


	@Override
	@Async
	@Transactional
	public void updateStatus(Long tripId) {

		notificationRepository.updateStatus(tripId);
	}
	
	
	
	
}
