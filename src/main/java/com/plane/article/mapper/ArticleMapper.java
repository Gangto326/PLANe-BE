package com.plane.article.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.plane.article.dto.ArticleDetailResponse;
import com.plane.article.dto.ArticleResponse;
import com.plane.article.dto.ArticleUpdateRequest;
import com.plane.common.dto.PageRequest;

@Mapper
public interface ArticleMapper {
	
	@Select("""
	        SELECT b.articleId, u.nickName, b.tripId, b.articleType, b.title, b.content, b.articlePictureUrl, b.likeCount, b.viewCount, b.createdDate,
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
	        LEFT JOIN Users u ON b.authorId = u.userId
	        LEFT JOIN PLANe p ON b.tripId = p.tripId
	        LEFT JOIN Region r ON p.regionId = r.regionId
	        WHERE b.articleId = #{articleId}
		    """)
	@Results({
		@Result(property = "tripId", column = "tripId"),
		@Result(property = "tripThema", column = "tripId", many = @Many(select = "com.plane.trip.mapper.TripMapper.selectTripThemasByTripId"))
	})
	ArticleDetailResponse selectArticleDetail(@Param("currentUserId") String currentUserId, @Param("articleId") int articleId);
	
	
	@Update("""
			UPDATE Board
		    SET title = #{articleUpdateRequest.title},
		        content = #{articleUpdateRequest.content},
		        updatedDate = CURRENT_TIMESTAMP
		    WHERE articleId = #{articleUpdateRequest.articleId}
		    AND authorId = #{userId}
			""")
	int updateArticle(@Param("userId") String userId, @Param("articleUpdateRequest") ArticleUpdateRequest articleUpdateRequest);
	
	
	@Select("SELECT COUNT(*) FROM Board WHERE articleType = #{articleType}")
	long countAll(String articleType);
	
	
	@Select("""
			SELECT b.articleId, u.nickName, b.articleType, b.title, b.articlePictureUrl, b.createdDate,
	        CASE 
	            WHEN EXISTS (
	                SELECT 1 
	                FROM Saved s 
	                WHERE s.articleId = b.articleId 
	                AND s.userId = #{userId}
	                AND s.type = 'SAVE'
	            ) THEN true 
	            ELSE false 
	        END as isSaved,
	        CASE 
	            WHEN EXISTS (
	                SELECT 1 
	                FROM Saved s 
	                WHERE s.articleId = b.articleId 
	                AND s.userId = #{userId}
	                AND s.type = 'RECOMMAND'
	            ) THEN true 
	            ELSE false 
	        END as isRecommand,
	        p.accompanyNum,
	        r.sigungu,
	        p.departureDate,
	        p.arrivedDate,
	        b.tripId
		    FROM Board b
		    LEFT JOIN Users u ON b.authorId = u.userId
		    LEFT JOIN PLANe p ON b.tripId = p.tripId
		    LEFT JOIN Region r ON p.regionId = r.regionId
		    WHERE articleType = #{pageRequest.articleType}
		    ORDER BY ${pageRequest.sortBy} ${pageRequest.sortDirection}
		    LIMIT #{pageRequest.offset}, #{pageRequest.size};
			""")
	@Results({
		@Result(property = "tripThema", column = "tripId", many = @Many(select = "com.plane.trip.mapper.TripMapper.selectTripThemasByTripId"))
	})
	List<ArticleResponse> selectArticlesByPageRequest(@Param("userId") String userId, @Param("pageRequest") PageRequest pageRequest);
	
}
