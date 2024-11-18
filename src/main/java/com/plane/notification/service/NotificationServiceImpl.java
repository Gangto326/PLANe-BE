package com.plane.notification.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.common.exception.custom.ArticleNotFoundException;
import com.plane.common.exception.custom.InvalidParameterException;
import com.plane.notification.dto.NotificationDetailResponse;
import com.plane.notification.dto.NotificationResponse;
import com.plane.notification.repository.NotificationRepository;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

	private final NotificationRepository notificationRepository;

	@Autowired
	public NotificationServiceImpl(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
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
		
		NotificationDetailResponse notificationDetailResponse = notificationRepository.getNotificationDetail(userId, noId);
		return notificationDetailResponse;
	}
	
	
	
	
}
