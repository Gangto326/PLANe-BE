package com.plane.notification.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.plane.common.annotation.UserId;
import com.plane.common.response.ApiResponse;
import com.plane.notification.dto.NotificationResponse;
import com.plane.notification.service.NotificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
	
	private final NotificationService notificationService;

	
	public NotificationController(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
	
	
	@GetMapping("")
	public ResponseEntity<ApiResponse<List<NotificationResponse>>> NotificationList(
			@UserId String userId,
			@RequestParam String type
			) {
		
		List<NotificationResponse> notificationList = notificationService.getNotificationList(userId, type);
		return ResponseEntity.ok(ApiResponse.success(notificationList, "알림 목록을 성공적으로 반환했습니다."));
	}
	
	
	
}
