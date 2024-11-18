package com.plane.notification.service;

import java.util.List;

import com.plane.notification.dto.NotificationResponse;

public interface NotificationService {

	List<NotificationResponse> getNotificationList(String userId, String type);

	int getUnconfirmedCount(String userId);

}
