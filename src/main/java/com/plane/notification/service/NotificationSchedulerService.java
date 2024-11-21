package com.plane.notification.service;

import java.time.LocalDate;
import java.util.List;

public interface NotificationSchedulerService {

	void scheduleTripReviewNotification(Long tripId, String title, LocalDate arrivedDate, String userId);
	
}
