package com.plane.article.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.plane.article.domain.Article;
import com.plane.article.dto.ArticleDetailResponse;
import com.plane.article.dto.ArticleResponse;
import com.plane.article.dto.ArticleSearchRequest;
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
	
	
	@Select("""
			<script>
			SELECT COUNT(*)
			FROM Board b
		    LEFT JOIN Users u ON b.authorId = u.userId
		    LEFT JOIN PLANe p ON b.tripId = p.tripId
		    LEFT JOIN Region r ON p.regionId = r.regionId
		    <where>
				<if test="articleSearchRequest.articleType != null">
					b.articleType = #{articleSearchRequest.articleType}
		        </if>
		        <if test="articleSearchRequest.regionId != null">
		            AND p.regionId = #{articleSearchRequest.regionId}
		        </if>
		        <if test="articleSearchRequest.tripPeriod != null">
		            AND p.departureDate = #{articleSearchRequest.tripPeriod}
		        </if>
		        <if test="articleSearchRequest.tripDays != null">
		            AND p.tripDays = #{articleSearchRequest.tripDays}
		        </if>
		        <if test="articleSearchRequest.isRecommand">
			        AND EXISTS (
			        	SELECT 1
			        	FROM Saved s
			        	WHERE s.articleId = b.articleId 
			        	AND s.userId = #{userId}
			        	AND s.type = 'RECOMMAND'
			        	)
			    </if>
	        </where>
	        </script>
			""")
	long countAll(@Param("userId") String userId, @Param("articleSearchRequest") ArticleSearchRequest articleSearchRequest);
	
	
	@Select("""
			<script>
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
		    <where>
				<if test="articleSearchRequest.articleType != null">
					b.articleType = #{articleSearchRequest.articleType}
		        </if>
		        <if test="articleSearchRequest.regionId != null">
		            AND p.regionId = #{articleSearchRequest.regionId}
		        </if>
		        <if test="articleSearchRequest.tripPeriod != null">
		            AND p.departureDate = #{articleSearchRequest.tripPeriod}
		        </if>
		        <if test="articleSearchRequest.tripDays != null">
		            AND p.tripDays = #{articleSearchRequest.tripDays}
		        </if>
		        <if test="articleSearchRequest.isRecommand">
			        AND EXISTS (
			        	SELECT 1
			        	FROM Saved s
			        	WHERE s.articleId = b.articleId 
			        	AND s.userId = #{userId}
			        	AND s.type = 'RECOMMAND'
			        	)
			    </if>
	        </where>
		    ORDER BY ${articleSearchRequest.pageRequest.sortBy} ${articleSearchRequest.pageRequest.sortDirection}
		    LIMIT #{articleSearchRequest.pageRequest.offset}, #{articleSearchRequest.pageRequest.size}
		    </script>
			""")
	@Results({
		@Result(property = "tripThema", column = "tripId", many = @Many(select = "com.plane.trip.mapper.TripMapper.selectTripThemasByTripId"))
	})
	List<ArticleResponse> selectArticlesByPageRequest(@Param("userId") String userId, @Param("articleSearchRequest") ArticleSearchRequest articleSearchRequest);


	@Select("""
			SELECT *
			FROM Board
			WHERE authorId = #{userId}
			AND articleId = #{articleId}
			""")
	Article selectArticleByUserIdAndArticleId(@Param("userId") String userId, @Param("articleId") Integer articleId);


	@Delete("""
			DELETE FROM Board
			WHERE authorId = #{userId}
			AND articleId = #{articleId}
			""")
	int deleteArticle(@Param("userId") String userId, @Param("articleId") Integer articleId);
	
}
