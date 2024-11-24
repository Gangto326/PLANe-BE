package com.plane.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import com.plane.article.repository.ArticleRepository;
import com.plane.common.dto.ScheduledNotification;
import com.plane.common.util.NotificationTitleGenerator;
import com.plane.notification.dto.NotificationAction;
import com.plane.notification.dto.NotificationCreateRequest;
import com.plane.notification.dto.NotificationTarget;
import com.plane.notification.dto.NotificationTargetType;
import com.plane.notification.repository.NotificationRepository;
import com.plane.user.repository.UserRepository;

public class NotificationScheduler {

	private final NotificationRepository notificationRepository;
	private final NotificationTitleGenerator notificationTitleGenerator;
	private final UserRepository userRepository;
	private final ArticleRepository articleRepository;
	
	@Autowired
	public NotificationScheduler(NotificationRepository notificationRepository, NotificationTitleGenerator notificationTitleGenerator, UserRepository userRepository, ArticleRepository articleRepository) {
        this.notificationRepository = notificationRepository;
        this.notificationTitleGenerator = notificationTitleGenerator;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }
	
	
	@Scheduled(cron = "0 0 9 * * *")
	@Transactional
	public void checkAndSendNotifications() {
		
		LocalDateTime now = LocalDateTime.now();
		List<ScheduledNotification> notifications = notificationRepository.findByScheduledTimeBeforeAndIsSentFalseAndIsActiveTrue(now);
        
		for (ScheduledNotification notification : notifications) {
			
			// 후기 등록 알림인 경우
			if (notification.getType().equals("후기")) {
				
				try {
	                // 알림 발송 로직
	                NotificationCreateRequest notificationCreateRequest = new NotificationCreateRequest();
	                notificationCreateRequest.setContentId(notification.getTripId());
	                notificationCreateRequest.setNotificationType("여행");
	                
	                NotificationTarget target = new NotificationTarget();
	        		target.setType(NotificationTargetType.REVIEW);
	        		target.setNickname(userRepository.selectUserNicknameByUserId(notification.getUserId()));
	        		target.setContent(notification.getTitle());
	        		
	        		String title = notificationTitleGenerator.generateTitle(target, NotificationAction.REQUEST);
	        		notificationCreateRequest.setTitle(title);
	                
	                notificationRepository.insertNotification(notification.getUserId(), notificationCreateRequest);
	                notificationRepository.updateStatus(notification.getTripId());
	                
		            } catch (Exception e) {
		            	
		            }
				
			}
			// 매너 평가 관련 알림인 경우
			else if (notification.getType().equals("매너")) {
				
				try {
	                // 알림 발송 로직
	                NotificationCreateRequest notificationCreateRequest = new NotificationCreateRequest();
//	                notificationCreateRequest.setContentId(notification.getTripId());
	                
	                notificationCreateRequest.setContentId(articleRepository.selectArticleIdByTripId(notification.getTripId()));
	                notificationCreateRequest.setNotificationType("매너");
	                
	                NotificationTarget target = new NotificationTarget();
	        		target.setType(NotificationTargetType.MANNER);
	        		target.setNickname(userRepository.selectUserNicknameByUserId(notification.getUserId()));
	        		target.setContent(notification.getTitle());
	        		
	        		String title = notificationTitleGenerator.generateTitle(target, NotificationAction.REQUEST);
	        		notificationCreateRequest.setTitle(title);
	                
	                notificationRepository.insertNotification(notification.getUserId(), notificationCreateRequest);
	                notificationRepository.updateStatus(notification.getTripId());
	                
		            } catch (Exception e) {
		            	
		            }
				
			}
        }
    }
}
