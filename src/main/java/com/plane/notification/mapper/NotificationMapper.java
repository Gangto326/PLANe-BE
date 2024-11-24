package com.plane.notification.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.plane.common.dto.ScheduledNotification;
import com.plane.notification.dto.NotificationCreateRequest;
import com.plane.notification.dto.NotificationDetailResponse;
import com.plane.notification.dto.NotificationResponse;

@Mapper
public interface NotificationMapper {

	@Select("""
			<script>
			SELECT n.noId, n.isRead, n.notificationType, n.contentId, n.title, n.createdDate
			FROM Notification n
			WHERE n.userId = #{userId}
			AND n.deletedDate IS NULL
			<if test="type == 'UNCONFIRMED'">
				AND n.isRead = false
			</if>
			ORDER BY n.createdDate DESC
			</script>
			""")
	List<NotificationResponse> selectNotificationsByType(@Param("userId") String userId, @Param("type") String type);
	
	
	@Select("""
			SELECT COUNT(*)
			FROM Notification
			WHERE userId = #{userId}
			AND deletedDate IS NULL
			AND isRead = false
			""")
	int countAllUnconfirmed(@Param("userId") String userId);


	@Select("""
			SELECT EXISTS (
	            SELECT 1
	            FROM Notification
	            WHERE userId = #{userId}
	            AND noId = #{noId}
	        )
			""")
	boolean existsNotificationByUserIdAndNoId(@Param("userId") String userId, @Param("noId") Long noId);
	
	
	@Select("""
			SELECT noId, notificationType, contentId, title, createdDate
			FROM Notification
			WHERE userId = #{userId}
			AND noId = #{noId}
			AND deletedDate IS NULL
			""")
	NotificationDetailResponse getNotificationDetail(@Param("userId") String userId, @Param("noId") Long noId);


	@Update("""
			UPDATE Notification
			SET deletedDate = NOW()
			WHERE userId = #{userId}
			AND noId = #{noId}
			""")
	int updateNotificationDelete(String userId, Long noId);
	
	
	@Update("""
			UPDATE Notification
			SET isRead = true
			WHERE userId = #{userId}
			AND noId = #{noId}
			""")
	void updateNotificationRead(String userId, Long noId);

	
	@Insert("""
			INSERT INTO Notification (`userId`, `notificationType`, `contentId`, `title`)
			VALUES (#{receiverId}, #{request.notificationType}, #{request.contentId}, #{request.title})
			""")
	int insertNotification(@Param("receiverId") String receiverId, @Param("request") NotificationCreateRequest notificationCreateRequest);


	@Select("""
			SELECT *
			FROM ScheduledNotification 
			WHERE scheduledTime <= #{now}
			AND isSent = false 
			AND isActive = true
			""")
	List<ScheduledNotification> findByScheduledTimeBeforeAndIsSentFalseAndIsActiveTrue(@Param("now") LocalDateTime now);

	
	@Update("""
	        UPDATE ScheduledNotification
	        SET isSent = true,
	        	isActive = false
	        WHERE tripId = #{tripId}
	        """)
	void updateStatus(@Param("tripId") Long tripId);


	@Insert("""
	        INSERT INTO ScheduledNotification (tripId, userId, title, scheduledTime)
	        VALUES (#{notification.tripId}, #{notification.userId}, #{notification.title}, #{notification.scheduledTime})
	        """)
	void save(@Param("notification") ScheduledNotification notification);
}
