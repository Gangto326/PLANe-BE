package com.plane.afterTrip.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import com.plane.afterTrip.domain.AfterPic;
import com.plane.afterTrip.dto.TripDayDto;

@Mapper
public interface AfterTripMapper {
	
	@Insert("""
			INSERT INTO AfterTrip (`tripId`, `tripDay`, `content`)
            VALUES (#{tripId}, #{dayDto.tripDay}, #{dayDto.content})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "dayDto.afterTripid")
	int insertAfterTrip(@Param("tripId") Long tripId, @Param("dayDto") TripDayDto tripDayDto);

	
	@Insert("""
			<script>
			INSERT INTO AfterPic (`afterTripId`, `afterPictureUrl`)
	        VALUES
	        <foreach collection='fileList' item='afterPic' separator=','>
	            (#{afterPic.afterTripId}, #{afterPic.afterPictureUrl})
	        </foreach>
	        </script>
			""")
	int insertAfterPic(List<AfterPic> fileList);
	
	
}
