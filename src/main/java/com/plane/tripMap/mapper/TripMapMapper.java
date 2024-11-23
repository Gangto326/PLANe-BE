package com.plane.tripMap.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.plane.tripMap.dto.TripMapCreateRequest;

@Mapper
public interface TripMapMapper {

	@Insert("""
			INSERT INTO TripMap (`userId`, `tripId`, `mapLocate`, `mapPictureUrl`, `mapContent`)
            VALUES (#{userId}, #{request.tripId}, ST_GeomFromText(CONCAT('POINT(', #{request.mapx}, ' ', #{request.mapy}, ')')), #{request.mapPictureUrl}, #{request.mapContent})
			""")
	int insertTripMap(@Param("userId") String userId, @Param("request") TripMapCreateRequest tripMapCreateRequest);

	
}
