package com.plane.article.mapper;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.plane.article.dto.ArticleDetailResponse;

@Mapper
public interface ArticleMapper {
	
	@Select("""
	        SELECT b.articleId, b.authorId, b.tripId, b.articleType, b.title, b.content, b.articlePictureUrl, b.likeCount, b.viewCount, b.createdDate,
            CASE 
                WHEN EXISTS (
                    SELECT 1 
                    FROM Saved s 
                    WHERE s.articleId = b.articleId 
                    AND s.userId = #{currentUserId}
                    AND s.type = 'SAVE'
                ) THEN true 
                ELSE false 
            END as isSaved,
	        CASE 
                WHEN EXISTS (
                    SELECT 1 
                    FROM Saved s 
                    WHERE s.articleId = b.articleId 
                    AND s.userId = #{currentUserId}
                    AND s.type = 'RECOMMAND'
                ) THEN true 
                ELSE false 
            END as isRecommand,
	        p.accompanyNum, r.sigungu, p.departureDate, p.arrivedDate
	        FROM Board b
	        LEFT JOIN PLANe p ON b.tripId = p.tripId
	        LEFT JOIN Region r ON p.regionId = r.regionId
	        WHERE b.articleId = #{articleId}
		    """)
	@Results({
		@Result(property = "tripId", column = "tripId"),
		@Result(property = "tripThema", column = "tripId", many = @Many(select = "com.plane.trip.mapper.TripMapper.selectTripThemasByTripId"))
	})
	ArticleDetailResponse selectArticleDetail(@Param("articleId") int articleId, @Param("currentUserId") String currentUserId);
	
}
