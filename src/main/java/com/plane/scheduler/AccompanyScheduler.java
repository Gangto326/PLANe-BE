package com.plane.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.plane.accompany.repository.AccompanyRepository;
import com.plane.common.dto.ScheduledNotification;
import com.plane.notification.dto.AccompanyNotificationDto;
import com.plane.notification.service.NotificationService;

@Component
public class AccompanyScheduler {
	
	private final AccompanyRepository accompanyRepository;
	private final NotificationService notificationService;
	
	@Autowired
	public AccompanyScheduler(AccompanyRepository accompanyRepository, NotificationService notificationService) {
        this.accompanyRepository = accompanyRepository;
        this.notificationService = notificationService;
    }
	
	
	// 해당 날짜에 끝나는 동행이 있다면 ScheduledNotification에 추가
	@Scheduled(cron = "0 0 22 * * *")
	@Transactional
	public void createAccompanyNotification() {
		
		List<AccompanyNotificationDto>  accompanyNotificationDtoList = accompanyRepository.findAllArrivedAccompany();
		
		for (AccompanyNotificationDto accompanyNotificationDto: accompanyNotificationDtoList) {
			ScheduledNotification notification = new ScheduledNotification();
	        notification.setTripId(accompanyNotificationDto.getTripId());
	        notification.setUserId(accompanyNotificationDto.getUserId());
	        notification.setTitle(accompanyNotificationDto.getTripName());
	        notification.setType("매너");
	        notification.setScheduledTime(accompanyNotificationDto.getArrivedDate().plusDays(1).atStartOfDay());
	    	
	    	notificationService.save(notification);
		}
	}
	
	
}
