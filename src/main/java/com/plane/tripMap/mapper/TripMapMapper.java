package com.plane.tripMap.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.plane.tripMap.dto.TripMapCreateRequest;
import com.plane.tripMap.dto.TripMapDetailResponse;
import com.plane.tripMap.dto.TripMapListResponse;

@Mapper
public interface TripMapMapper {

	@Insert("""
			INSERT INTO TripMap (`userId`, `tripId`, `regionId`, `mapLocate`, `mapPictureUrl`, `mapContent`)
            VALUES (#{userId}, #{request.tripId}, #{request.regionId}, ST_GeomFromText(CONCAT('POINT(', #{request.mapx}, ' ', #{request.mapy}, ')')), #{request.mapPictureUrl}, #{request.mapContent})
			""")
	int insertTripMap(@Param("userId") String userId, @Param("request") TripMapCreateRequest tripMapCreateRequest);
	

	@Select("""
		    SELECT EXISTS (
			   SELECT 1
			   FROM TripMap
			   WHERE userId = #{userId}
			   AND regionId = #{regionId}
			   AND deletedDate IS NULL
			)
			""")
	boolean existsMapByUserIdAndRegionId(@Param("userId") String userId, @Param("regionId") Integer regionId);

	
	@Select("""
			SELECT tripId, regionId, ST_X(mapLocate) AS mapx, ST_Y(mapLocate) AS mapy, mapPictureUrl, mapContent
			FROM TripMap
			WHERE userId = #{userId}
			AND deletedDate IS NULL
			""")
	List<TripMapListResponse> selectAllTripMapByUserId(@Param("userId") String userId);

	
	@Select("""
		    SELECT DISTINCT 
		        p.tripId, p.tripName, p.userId = #{userId} as isAuthor, ST_X(tp.point) AS mapx, ST_Y(tp.point) AS mapy, tp.url
		    FROM PLANe p
		    LEFT JOIN Accompany a ON p.tripId = a.tripId AND a.userId = #{userId}
		    LEFT JOIN TripPlan tp ON p.tripId = tp.tripId 
			   AND tp.tripDay = 1 
			   AND tp.tripOrder = 0
		    WHERE (p.userId = #{userId} OR a.userId = #{userId})
		    AND p.regionId = #{regionId}
		    AND p.deletedDate IS NULL
			""")
	List<TripMapDetailResponse> selectAllTripMapDetail(@Param("userId") String userId, @Param("regionId") Integer regionId);

	
}
