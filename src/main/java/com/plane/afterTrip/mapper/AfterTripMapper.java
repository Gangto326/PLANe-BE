package com.plane.afterTrip.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.plane.afterTrip.domain.AfterPic;
import com.plane.afterTrip.dto.AfterPicResponse;
import com.plane.afterTrip.dto.AfterTripResponse;
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
			INSERT INTO AfterPic (`afterTripId`, `afterPictureUrl`, `originalFilename`)
	        VALUES
	        <foreach collection='fileList' item='afterPic' separator=','>
	            (#{afterPic.afterTripId}, #{afterPic.afterPictureUrl}, #{afterPic.originalFilename})
	        </foreach>
	        </script>
			""")
	int insertAfterPic(List<AfterPic> fileList);
	
	
    @Select("""
	        SELECT afterTripId, tripId, tripDay, content
	        FROM AfterTrip
	        WHERE tripId = #{tripId}
	        ORDER BY tripDay
	        """)
    @Results({
		@Result(property = "afterTripId", column = "afterTripId", id = true),
		@Result(property = "tripId", column = "tripId"),
        @Result(property = "tripDay", column = "tripDay"),
        @Result(property = "content", column = "content"),
        @Result(property = "files", column = "afterTripId", many = @Many(select = "selectPictures"))
	})
    List<AfterTripResponse> getAfterTripWithPics(@Param("tripId") Long tripId);

	
    @Select("""
	        SELECT id, afterPictureUrl, originalFilename
	        FROM AfterPic
	        WHERE afterTripId = #{afterTripId}
	        ORDER BY id
	        """)
    @Results({
    	@Result(property = "id", column = "id", id = true),
        @Result(property = "afterPictureUrl", column = "afterPictureUrl"),
        @Result(property = "originalFilename", column = "originalFilename")
    })
    List<AfterPicResponse> selectPictures(@Param("afterTripId") Long afterTripId);
    
    
}
