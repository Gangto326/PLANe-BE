package com.plane.notification.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.plane.notification.dto.NotificationDetailResponse;
import com.plane.notification.dto.NotificationResponse;
import com.plane.notification.mapper.NotificationMapper;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {

	private final NotificationMapper notificationMapper;

	@Autowired
	public NotificationRepositoryImpl(NotificationMapper notificationMapper) {
		this.notificationMapper = notificationMapper;
	}

	@Override
	public List<NotificationResponse> findAllNotifications(String userId, String type) {

		return notificationMapper.selectNotificationsByType(userId, type);
	}

	@Override
	public int countAllUnconfirmed(String userId) {

		return notificationMapper.countAllUnconfirmed(userId);
	}

	@Override
	public boolean existsNotificationByUserIdAndNoId(String userId, Long noId) {

		return notificationMapper.existsNotificationByUserIdAndNoId(userId, noId);
	}

	@Override
	public NotificationDetailResponse getNotificationDetail(String userId, Long noId) {

		return notificationMapper.getNotificationDetail(userId, noId);
	}

	@Override
	public int deleteNotification(String userId, Long noId) {

		return notificationMapper.updateNotificationDelete(userId, noId);
	}
	
	
	
}
