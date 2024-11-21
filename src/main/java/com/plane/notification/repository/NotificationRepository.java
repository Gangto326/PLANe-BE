package com.plane.notification.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.plane.common.dto.ScheduledNotification;
import com.plane.notification.dto.NotificationCreateRequest;
import com.plane.notification.dto.NotificationDetailResponse;
import com.plane.notification.dto.NotificationResponse;

public interface NotificationRepository {

	List<NotificationResponse> findAllNotifications(String userId, String type);

	int countAllUnconfirmed(String userId);

	boolean existsNotificationByUserIdAndNoId(String userId, Long noId);

	NotificationDetailResponse getNotificationDetail(String userId, Long noId);

	int deleteNotification(String userId, Long noId);

	int insertNotification(String receiverId, NotificationCreateRequest notificationCreateRequest);

	List<ScheduledNotification> findByScheduledTimeBeforeAndIsSentFalseAndIsActiveTrue(LocalDateTime now);

	void updateStatus(Long tripId);
	
	void save(ScheduledNotification notification);
}
