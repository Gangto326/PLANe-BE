package com.plane.notification.service;

import java.util.List;

import com.plane.notification.dto.NotificationDetailResponse;
import com.plane.notification.dto.NotificationResponse;

public interface NotificationService {

	List<NotificationResponse> getNotificationList(String userId, String type);

	int getUnconfirmedCount(String userId);

	NotificationDetailResponse getNotificationDetail(String userId, Long noId);

	boolean deleteNotification(String userId, Long noId);

}
