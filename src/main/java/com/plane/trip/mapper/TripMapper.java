package com.plane.trip.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.plane.trip.domain.TripStyle;
import com.plane.trip.domain.TripThema;
import com.plane.trip.dto.TripCreateRequest;
import com.plane.trip.dto.TripResponse;
import com.plane.trip.dto.TripPlanDto;

@Mapper
public interface TripMapper {
	
	@Select("""
		    SELECT ts.styleId as id
		    FROM TripStyle ts
		    JOIN UsersTripStyle uts ON ts.styleId = uts.styleId
		    WHERE uts.userId = #{userId}
		    """)
	List<Integer> selectTripStylesByUserId(@Param("userId") String userId);
	
	
	@Select("""
		    SELECT tt.themaId as id
		    FROM TripThema tt
		    JOIN UsersTripThema utt ON tt.themaId = utt.themaId
		    WHERE utt.userId = #{userId}
		    """)
	List<Integer> selectTripThemasByUserId(@Param("userId") String userId);
	
	
	@Select("""
		    SELECT tt.themaId as id
		    FROM TripThema tt
		    JOIN PLANeTripThema ptt ON tt.themaId = ptt.themaId
		    WHERE ptt.tripId = #{tripId}
		    """)
	List<Integer> selectTripThemasByTripId(@Param("tripId") int tripId);
	
	
	@Delete("""
			DELETE
			FROM UsersTripStyle
			WHERE userId = #{userId}
			""")
	int deleteTripStyle(String userId);

	
	@Delete("""
			DELETE
			FROM UsersTripThema
			WHERE userId = #{userId}
			""")
	int deleteTripThema(String userId);


	@Insert("""
			<script>
			INSERT INTO UsersTripStyle (userId, styleId)
	        VALUES
	        <foreach collection='tripStyle' item='styleId' separator=','>
	            (#{userId}, #{styleId})
	        </foreach>
	        </script>
			""")
	int insertTripStyle(@Param("userId") String userId, @Param("tripStyle") List<Integer> tripStyle);


	@Insert("""
			<script>
			INSERT INTO UsersTripThema (userId, themaId)
	        VALUES
	        <foreach collection="tripThema" item="themaId" separator=",">
	            (#{userId}, #{themaId})
	        </foreach>
	        </script>
			""")
	int insertTripThema(@Param("userId") String userId, @Param("tripThema") List<Integer> tripThema);

	
	@Insert("""
			INSERT INTO PLANe (`userId`, `regionId`, `tripName`, `departureDate`, `arrivedDate`, `state`, `accompanyNum`, `tripDays`)
            VALUES (#{userId}, #{trip.regionId}, #{trip.tripName}, #{trip.departureDate}, #{trip.arrivedDate}, #{trip.state}, #{trip.accompanyNum}, #{trip.tripDays})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "trip.tripId")
	int insertTrip(@Param("userId") String userId, @Param("trip") TripCreateRequest tripCreateRequest);


	@Insert("""
			INSERT INTO TripPlan (`tripId`, `tripDay`, `tripOrder`, `title`, `memo`, `point`, `address`)
			VALUES (#{tripId}, #{tripDay}, #{tripOrder}, #{title}, #{memo}, ST_GeomFromText(CONCAT('POINT(', #{mapx}, ' ', #{mapy}, ')')), #{address})
			""")
	int insertTripPlan(TripPlanDto tripPlanDto);


	@Update("""
			UPDATE PLANe
			SET deletedDate = NOW()
			WHERE userId = #{userId}
			AND tripId = #{tripId}
			""")
	int updatePlaneDeletedByTripId(String userId, Long tripId);
	
	
	@Select("""
			SELECT EXISTS (
	            SELECT 1
	            FROM PLANe
	            WHERE tripId = #{tripId}
	            AND deletedDate IS NULL
	        )
			""")
	boolean existsTripByTripId(@Param("tripId") Long tripId);

	
	@Select("""
    		SELECT tripId, tripName, departureDate, arrivedDate, state, accompanyNum, tripDays, isLiked, isPublic, isReviewed
    		FROM PLANe
    		WHERE tripId = #{tripId}
    		AND deletedDate IS NULL
    		""")
	@Results({
		@Result(property = "tripId", column = "tripId"),
        @Result(property = "tripName", column = "tripName"),
        @Result(property = "departureDate", column = "departureDate"),
        @Result(property = "arrivedDate", column = "arrivedDate"),
        @Result(property = "state", column = "state"),
        @Result(property = "accompanyNum", column = "accompanyNum"),
        @Result(property = "tripDays", column = "tripDays"),
        @Result(property = "isLiked", column = "isLiked"),
        @Result(property = "isPublic", column = "isPublic"),
        @Result(property = "isReviewed", column = "isReviewed"),
        @Result(property = "themaList", column = "tripId",
            many = @Many(select = "selectTripThemasByTripId")),
        @Result(property = "planList", column = "tripId",
            many = @Many(select = "selectTripPlans"))
    })
	TripResponse selectTripDetail(@Param("tripId") Long tripId);
	
	
	@Select("""
			SELECT id, tripId, tripDay, tripOrder, title, memo, ST_X(point) AS mapx, ST_Y(point) AS mapy, address
			FROM TripPlan
			WHERE tripId = #{tripId}
			ORDER BY tripOrder ASC
			""")
	List<TripPlanDto> selectTripPlans(@Param("tripId") Long tripId);
	
}
