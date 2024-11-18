package com.plane.notification.repository;

import java.util.List;

import com.plane.notification.dto.NotificationDetailResponse;
import com.plane.notification.dto.NotificationResponse;

public interface NotificationRepository {

	List<NotificationResponse> findAllNotifications(String userId, String type);

	int countAllUnconfirmed(String userId);

	boolean existsNotificationByUserIdAndNoId(String userId, Long noId);

	NotificationDetailResponse getNotificationDetail(String userId, Long noId);

}
