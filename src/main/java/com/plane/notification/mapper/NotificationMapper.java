package com.plane.notification.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.plane.notification.dto.NotificationDetailResponse;
import com.plane.notification.dto.NotificationResponse;

@Mapper
public interface NotificationMapper {

	@Select("""
			SELECT n.noId, n.isRead, n.notificationType, n.contentId,
			CASE 
				WHEN n.notificationType IN ('comment', 'article') 
				THEN (
					SELECT title
					FROM Board
					WHERE articleId = n.contentId
					)
				WHEN n.notificationType = 'accompany'
				THEN (
					SELECT b.title 
					FROM AccompanyApply a 
					JOIN Board b ON a.articleId = b.articleId 
					WHERE a.applyId = n.contentId
					)
			ELSE NULL
			END as title,
			n.createdDate
			FROM Notification n
			WHERE n.userId = #{userId}
			AND n.deletedDate IS NULL
			<if test="type == 'UNCONFIRMED'">
				AND n.isRead = false
			</if>
			ORDER BY n.createdDate DESC
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
			SELECT noId, notificationType, contentId, details, createdDate
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

	
}
