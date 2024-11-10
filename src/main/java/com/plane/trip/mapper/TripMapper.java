package com.plane.trip.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.plane.trip.domain.TripStyle;
import com.plane.trip.domain.TripThema;

@Mapper
public interface TripMapper {
	
	@Select("""
		    SELECT ts.styleId as id, ts.styleName
		    FROM TripStyle ts
		    JOIN UsersTripStyle uts ON ts.styleId = uts.styleId
		    WHERE uts.userId = #{userId}
		    """)
	List<TripStyle> selectTripStylesByUserId(String userId);
	
	
	@Select("""
		    SELECT tt.themaId as id, tt.themaName
		    FROM TripThema tt
		    JOIN UsersTripThema utt ON tt.themaId = utt.themaId
		    WHERE utt.userId = #{userId}
		    """)
	List<TripThema> selectTripThemasByUserId(String userId);
	
	
	@Select("""
		    SELECT tt.themaId as id, tt.themaName
		    FROM TripThema tt
		    JOIN PLANeTripThema ptt ON tt.themaId = ptt.themaId
		    WHERE ptt.tripId = #{tripId}
		    """)
	List<TripThema> selectTripThemasByTripId(int tripId);

}
