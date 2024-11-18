package com.plane.notification.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.plane.notification.dto.NotificationResponse;

@Mapper
public interface NotificationMapper {

	@Select("""
			SELECT noId, isRead, notificationType, contentId, createdDate
			FROM Notification
			WHERE userId = #{userId}
			AND deletedDate IS NULL
			<if test="type == 'UNCONFIRMED'">
				AND isRead = false
			</if>
			ORDER BY createdDate DESC
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

}
