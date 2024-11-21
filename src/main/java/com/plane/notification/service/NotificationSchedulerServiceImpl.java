package com.plane.notification.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import com.plane.notification.dto.NotificationAction;
import com.plane.notification.dto.NotificationCreateRequest;
import com.plane.notification.dto.NotificationTargetType;

@Service
public class NotificationSchedulerServiceImpl implements NotificationSchedulerService {
	
	private final TaskScheduler taskScheduler;
    private final NotificationService notificationService;
	
    @Autowired
    public NotificationSchedulerServiceImpl(TaskScheduler taskScheduler, NotificationService notificationService) {
        this.taskScheduler = taskScheduler;
        this.notificationService = notificationService;
    }
    
    
    public void scheduleTripReviewNotification(Long tripId, String title, LocalDate arrivedDate, String userId) {
    	
    	Instant notificationTime = arrivedDate.plusDays(1)
			    								.atTime(0, 0)
								                .atZone(ZoneId.systemDefault())
								                .toInstant();
        
        taskScheduler.schedule(() -> {
        	NotificationCreateRequest request = new NotificationCreateRequest();
        	request.setContentId(tripId);
        	request.setNotificationType("trip");
            request.setDetails(title);
            request.setType(NotificationTargetType.REVIEW);
            request.setAction(NotificationAction.REQUEST);
            
            notificationService.createNotificationTripReview(userId, request);
        }, notificationTime);
        
    }
    
}
