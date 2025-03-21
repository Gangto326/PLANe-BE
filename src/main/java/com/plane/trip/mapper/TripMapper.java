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

import com.plane.trip.domain.Plane;
import com.plane.trip.domain.TripStyle;
import com.plane.trip.domain.TripThema;
import com.plane.trip.dto.TripCreateRequest;
import com.plane.trip.dto.TripResponse;
import com.plane.trip.dto.TripSearchRequest;
import com.plane.trip.dto.TripSearchResponse;
import com.plane.trip.dto.TripUpdateRequest;
import com.plane.trip.dto.UpcomingTripResponse;
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
			INSERT INTO TripPlan (`tripId`, `tripDay`, `tripOrder`, `title`, `memo`, `point`, `address`, `url`)
			VALUES (#{tripId}, #{tripDay}, #{tripOrder}, #{title}, #{memo}, ST_GeomFromText(CONCAT('POINT(', #{mapx}, ' ', #{mapy}, ')')), #{address}, #{url})
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
    		SELECT tripId, regionId, tripName, departureDate, arrivedDate, state, accompanyNum, tripDays, isLiked, isReviewed
    		FROM PLANe
    		WHERE tripId = #{tripId}
    		AND deletedDate IS NULL
    		""")
	@Results({
		@Result(property = "tripId", column = "tripId"),
		@Result(property = "regionId", column = "regionId"),
        @Result(property = "tripName", column = "tripName"),
        @Result(property = "departureDate", column = "departureDate"),
        @Result(property = "arrivedDate", column = "arrivedDate"),
        @Result(property = "state", column = "state"),
        @Result(property = "accompanyNum", column = "accompanyNum"),
        @Result(property = "tripDays", column = "tripDays"),
        @Result(property = "isLiked", column = "isLiked"),
        @Result(property = "isReviewed", column = "isReviewed"),
        @Result(property = "themaList", column = "tripId", many = @Many(select = "selectTripThemasByTripId")),
        @Result(property = "planList", column = "tripId", many = @Many(select = "selectTripPlans"))
    })
	TripResponse selectTripDetail(@Param("tripId") Long tripId);
	
	
	@Select("""
			SELECT id, tripId, tripDay, tripOrder, title, memo, ST_X(point) AS mapx, ST_Y(point) AS mapy, address, url
			FROM TripPlan
			WHERE tripId = #{tripId}
			ORDER BY tripOrder ASC
			""")
	List<TripPlanDto> selectTripPlans(@Param("tripId") Long tripId);


	@Select("""
			SELECT EXISTS (
	            SELECT 1
	            FROM PLANe
	            WHERE userId = #{userId}
	            AND tripId = #{tripId}
	            AND deletedDate IS NULL
	        )
			""")
	boolean existsTripByIdAndTripId(@Param("userId") String userId, @Param("tripId") Long tripId);

	
	@Select("""
			SELECT EXISTS (
	            SELECT 1
	            FROM Accompany
	            WHERE userId = #{userId}
	            AND tripId = #{tripId}
	            AND role != '일반'
	        )
			""")
	boolean checkUpdatePermission(String userId, Long tripId);


	@Delete("""
			DELETE
			FROM PLANeTripThema
			WHERE tripId = #{tripId}
			""")
	int deleteTripThemaByTripId(@Param("tripId") Long tripId);
	
	
	@Insert("""
			<script>
			INSERT INTO PLANeTripThema (tripId, themaId)
	        VALUES
	        <foreach collection="tripThema" item="themaId" separator=",">
	            (#{tripId}, #{themaId})
	        </foreach>
	        </script>
			""")
	int insertTripThemaByTripId(@Param("tripId") Long tripId, @Param("tripThema") List<Integer> tripThema);


	@Delete("""
			DELETE
			FROM TripPlan
			WHERE tripId = #{tripId}
			""")
	int deleteTripPlans(@Param("tripId") Long tripId);

	
	@Update("""
			UPDATE PLANe
		    SET tripName = #{tripUpdateRequest.tripName},
		        departureDate = #{tripUpdateRequest.departureDate},
		        arrivedDate = #{tripUpdateRequest.arrivedDate},
		        state = #{tripUpdateRequest.state},
		        accompanyNum = #{tripUpdateRequest.accompanyNum},
		        tripDays = #{tripUpdateRequest.tripDays}
		    WHERE tripId = #{tripUpdateRequest.tripId}
		    AND userId = #{userId}
			""")
	int updatePlane(@Param("userId") String userId, @Param("tripUpdateRequest") TripUpdateRequest tripUpdateRequest);

	
	@Select("""
			SELECT accompanyNum
			FROM PLANe
			WHERE tripId = #{tripId}
			AND userId = #{userId}
			""")
	Integer selectAccompanyNum(@Param("userId") String userId, @Param("tripId") Long tripId);
	
	
	@Select("""
    		SELECT *
    		FROM PLANe
    		WHERE tripId = #{tripId}
    		AND userId = #{userId}
    		AND deletedDate IS NULL
    		""")
	Plane selectPlaneByUserIdAndTripId(@Param("userId") String userId, @Param("tripId") Long tripId);

	
	@Select("""
			<script>
			SELECT p.tripId, p.userId, p.regionId, p.tripName, p.departureDate, p.arrivedDate,
			       p.state, p.accompanyNum, p.tripDays, p.isLiked, p.isReviewed,
			       (
			         SELECT url 
			         FROM (
			           SELECT tp.tripId, tp.url,
			                  ROW_NUMBER() OVER (PARTITION BY tp.tripId ORDER BY RAND()) as rn
			           FROM TripPlan tp
			           WHERE tp.url IS NOT NULL
			         ) ranked
			         WHERE ranked.tripId = p.tripId 
			         AND ranked.rn = 1
			       ) as thumbnailUrl
			FROM PLANe p
			<where>
			   p.deletedDate IS NULL
			   AND p.userId = #{userId}
			   <if test="tripSearchRequest.regionId != null">
			       AND p.regionId = #{tripSearchRequest.regionId}
			   </if>
			   <if test="tripSearchRequest.state != null">
			       AND p.state = #{tripSearchRequest.state}
			   </if>
			   <if test="tripSearchRequest.accompanyNum != null">
			       AND p.accompanyNum = #{tripSearchRequest.accompanyNum}
			   </if>
			   <if test="tripSearchRequest.tripDays != null">
			       AND p.tripDays = #{tripSearchRequest.tripDays}
			   </if>
			   <if test="tripSearchRequest.isLiked != null">
			       AND p.isLiked = #{tripSearchRequest.isLiked}
			   </if>
			   <if test="tripSearchRequest.isReviewed != null">
			       AND p.isReviewed = #{tripSearchRequest.isReviewed}
			       AND p.arrivedDate &lt; CURDATE()
			   </if>
			</where>
			ORDER BY ${tripSearchRequest.pageRequest.sortBy} ${tripSearchRequest.pageRequest.sortDirection}
			LIMIT #{tripSearchRequest.pageRequest.offset}, #{tripSearchRequest.pageRequest.size}
			</script>
			""")
	@Results({
		@Result(property = "tripId", column = "tripId"),
		@Result(property = "tripThema", column = "tripId", many = @Many(select = "com.plane.trip.mapper.TripMapper.selectTripThemasByTripId"))
	})
	List<TripSearchResponse> selectTripsByPageRequest(@Param("userId") String userId, @Param("tripSearchRequest") TripSearchRequest tripSearchRequest);
	
	
	@Select("""
			<script>
			SELECT COUNT(*)
			FROM PLANe
			<where>
			   deletedDate IS NULL
			   AND userId = #{userId}
			   <if test="tripSearchRequest.regionId != null">
			       AND regionId = #{tripSearchRequest.regionId}
			   </if>
			   <if test="tripSearchRequest.state != null">
			       AND state = #{tripSearchRequest.state}
			   </if>
			   <if test="tripSearchRequest.accompanyNum != null">
			       AND accompanyNum = #{tripSearchRequest.accompanyNum}
			   </if>
			   <if test="tripSearchRequest.tripDays != null">
			       AND tripDays = #{tripSearchRequest.tripDays}
			   </if>
			   <if test="tripSearchRequest.isLiked != null">
			       AND isLiked = true
			   </if>
			   <if test="tripSearchRequest.isReviewed != null">
			       AND isReviewed = #{tripSearchRequest.isReviewed}
			       AND arrivedDate &lt; CURDATE()
			   </if>
			</where>
			</script>
			""")
	long countAllTrips(@Param("userId") String userId, @Param("tripSearchRequest") TripSearchRequest tripSearchRequest);


	@Select("""
		    SELECT p.tripId, p.regionId, p.tripName, p.departureDate, 
			      (
			      	SELECT url
			      	FROM TripPlan tp
			      	WHERE tp.tripId = p.tripId 
			      	AND tp.url IS NOT NULL
			      	ORDER BY RAND() 
			      	LIMIT 1
			      ) as thumbnailUrl
		    FROM PLANe p
		    INNER JOIN Accompany a ON p.tripId = a.tripId
		    WHERE a.userId = #{userId}
		    AND p.deletedDate IS NULL
		    AND p.departureDate > CURDATE()
		    AND p.state = '저장'
		    ORDER BY p.departureDate ASC
		    LIMIT 1
			""")
	UpcomingTripResponse selectUpcomingTrip(String userId);

	
	@Update("""
			UPDATE PLANe
			SET isReviewed = true
			WHERE userId = #{userId}
			AND tripId = #{tripId}
			""")
	int updatePlaneIsReviewed(@Param("userId") String userId, @Param("tripId") Long tripId);

	
	@Update("""
			UPDATE PLANe
			SET accompanyNum = #{accompanyNum}
			WHERE tripId = #{tripId}
			""")
	int updateAccompanyNum(@Param("tripId") Long tripId, @Param("accompanyNum") Integer accompanyNum);


	@Select("""
			<script>
			SELECT COUNT(*)
			FROM PLANe p
	        INNER JOIN Accompany a ON p.tripId = a.tripId
	        <where>
	           p.deletedDate IS NULL
	           AND a.deletedDate IS NULL
	           AND a.userId = #{userId}
	           <if test="tripSearchRequest.regionId != null">
	               AND p.regionId = #{tripSearchRequest.regionId}
	           </if>
	           <if test="tripSearchRequest.state != null">
	               AND p.state = #{tripSearchRequest.state}
	           </if>
	           <if test="tripSearchRequest.accompanyNum != null">
	               AND p.accompanyNum = #{tripSearchRequest.accompanyNum}
	           </if>
	           <if test="tripSearchRequest.tripDays != null">
	               AND p.tripDays = #{tripSearchRequest.tripDays}
	           </if>
	           <if test="tripSearchRequest.isLiked != null">
	               AND p.isLiked = #{tripSearchRequest.isLiked}
	           </if>
	           <if test="tripSearchRequest.isReviewed != null">
	               AND p.isReviewed = #{tripSearchRequest.isReviewed}
	               AND p.arrivedDate &lt; CURDATE()
	           </if>
	        </where>
			</script>
			""")
	long countAllAccompanyTrips(String userId, TripSearchRequest tripSearchRequest);
	
	
	@Select("""
			<script>
	        SELECT p.tripId, p.userId, p.regionId, p.tripName, p.departureDate, p.arrivedDate,
	              p.state, p.accompanyNum, p.tripDays, p.isLiked, p.isReviewed,
	              (
	              SELECT url
	              FROM (
			            SELECT tp.tripId, tp.url,
			            ROW_NUMBER() OVER (PARTITION BY tp.tripId ORDER BY RAND()) as rn
			            FROM TripPlan tp
			            WHERE tp.url IS NOT NULL
			            ) ranked
			      WHERE ranked.tripId = p.tripId 
			      AND ranked.rn = 1
			      ) as thumbnailUrl
	        FROM PLANe p
	        INNER JOIN Accompany a ON p.tripId = a.tripId
	        <where>
	           p.deletedDate IS NULL
	           AND a.deletedDate IS NULL
	           AND a.userId = #{userId}
	           <if test="tripSearchRequest.regionId != null">
	               AND p.regionId = #{tripSearchRequest.regionId}
	           </if>
	           <if test="tripSearchRequest.state != null">
	               AND p.state = #{tripSearchRequest.state}
	           </if>
	           <if test="tripSearchRequest.accompanyNum != null">
	               AND p.accompanyNum = #{tripSearchRequest.accompanyNum}
	           </if>
	           <if test="tripSearchRequest.tripDays != null">
	               AND p.tripDays = #{tripSearchRequest.tripDays}
	           </if>
	           <if test="tripSearchRequest.isLiked != null">
	               AND p.isLiked = #{tripSearchRequest.isLiked}
	           </if>
	           <if test="tripSearchRequest.isReviewed != null">
	               AND p.isReviewed = #{tripSearchRequest.isReviewed}
	               AND p.arrivedDate &lt; CURDATE()
	           </if>
	        </where>
	        ORDER BY ${tripSearchRequest.pageRequest.sortBy} ${tripSearchRequest.pageRequest.sortDirection}
	        LIMIT #{tripSearchRequest.pageRequest.offset}, #{tripSearchRequest.pageRequest.size}
	        </script>
	        """)
	@Results({
	    @Result(property = "tripId", column = "tripId"),
	    @Result(property = "tripThema", column = "tripId", many = @Many(select = "com.plane.trip.mapper.TripMapper.selectTripThemasByTripId"))
	})
	List<TripSearchResponse> selectAccompanyTripsByPageRequest(@Param("userId") String userId, @Param("tripSearchRequest") TripSearchRequest tripSearchRequest);
	
}
