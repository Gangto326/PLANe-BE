package com.plane.notification.repository;

import java.util.List;

import com.plane.notification.dto.NotificationResponse;

public interface NotificationRepository {

	List<NotificationResponse> findAllNotifications(String userId, String type);

	int countAllUnconfirmed(String userId);

}
