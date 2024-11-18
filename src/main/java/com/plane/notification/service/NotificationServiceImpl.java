package com.plane.notification.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.common.exception.custom.InvalidParameterException;
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
		
		if (type.equals("ALL") || type.equals("UNCONFIRMED")) {
			return notificationRepository.findAllNotifications(userId, type);
		}
		
		throw new InvalidParameterException("타입은 미확인, 전체 중 하나여야 합니다.");
	}


	@Override
	public int getUnconfirmedCount(String userId) {

		return notificationRepository.countAllUnconfirmed(userId);
	}
	
	
	
	
}
