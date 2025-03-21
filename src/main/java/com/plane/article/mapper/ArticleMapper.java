package com.plane.article.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.plane.article.domain.Article;
import com.plane.article.dto.ArticleCreateRequest;
import com.plane.article.dto.ArticleDetailResponse;
import com.plane.article.dto.ArticleInteractionRequset;
import com.plane.article.dto.ArticleNotificationInfo;
import com.plane.article.dto.ArticleReportRequest;
import com.plane.article.dto.ArticleResponse;
import com.plane.article.dto.ArticleSearchRequest;
import com.plane.article.dto.ArticleUpdateRequest;
import com.plane.article.dto.ChangePublicRequest;
import com.plane.common.dto.PageRequest;

@Mapper
public interface ArticleMapper {
	
	@Select("""
	        SELECT b.articleId, b.authorId, b.tripId, b.articleType, b.title, b.content, b.articlePictureUrl, b.likeCount, b.viewCount, b.createdDate, b.deletedDate,
            CASE 
                WHEN EXISTS (
                    SELECT 1 
                    FROM Interactions i
                    WHERE i.articleId = b.articleId 
                    AND i.userId = #{currentUserId}
                    AND i.type = 'SAVE'
                ) THEN true 
                ELSE false 
            END as isSaved,
	        CASE 
                WHEN EXISTS (
                    SELECT 1 
                    FROM Interactions i
                    WHERE i.articleId = b.articleId 
                    AND i.userId = #{currentUserId}
                    AND i.type = 'RECOMMAND'
                ) THEN true 
                ELSE false 
            END as isRecommand,
            CASE 
               WHEN b.authorId = #{currentUserId} THEN true 
               ELSE false 
            END as isAuthor,
	        p.accompanyNum, p.regionId, p.departureDate, p.arrivedDate
	        FROM Board b
	        LEFT JOIN PLANe p ON b.tripId = p.tripId
	        WHERE b.articleId = #{articleId}
	        AND b.deletedDate IS NULL
		    """)
	@Results({
		@Result(property = "tripId", column = "tripId"),
		@Result(property = "tripThema", column = "tripId", many = @Many(select = "com.plane.trip.mapper.TripMapper.selectTripThemasByTripId"))
	})
	ArticleDetailResponse selectArticleDetail(@Param("currentUserId") String currentUserId, @Param("articleId") Long articleId);
	

	@Update("""
			UPDATE Board
			SET viewCount = viewCount + 1
			WHERE articleId = #{articleId}
			AND deletedDate IS NULL
			""")
	int incrementViewCount(@Param("articleId") Long articleId);
	
	
	@Update("""
			UPDATE Board
		    SET title = #{articleUpdateRequest.title},
		        content = #{articleUpdateRequest.content},
		        articlePictureUrl = #{articleUpdateRequest.articlePictureUrl},
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
			    b.deletedDate IS NULL
			    AND b.isPublic = true
			    AND u.deletedDate IS NULL
				<if test="articleSearchRequest.articleType != null">
					AND b.articleType = #{articleSearchRequest.articleType}
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
			        	FROM Interactions i
			        	WHERE i.articleId = b.articleId 
			        	AND i.userId = #{userId}
			        	AND i.type = 'RECOMMAND'
			        	)
			    </if>
			    <if test="articleSearchRequest.isSaved">
			        AND EXISTS (
			        	SELECT 1
			        	FROM Interactions i
			        	WHERE i.articleId = b.articleId 
			        	AND i.userId = #{userId}
			        	AND i.type = 'SAVE'
			        	)
			    </if>
			    <if test="articleSearchRequest.searchTitle != null">
		            AND b.title LIKE CONCAT('%', #{articleSearchRequest.searchTitle}, '%')
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
	                FROM Interactions i
	                WHERE i.articleId = b.articleId 
	                AND i.userId = #{userId}
	                AND i.type = 'SAVE'
	            ) THEN true 
	            ELSE false 
	        END as isSaved,
	        CASE 
	            WHEN EXISTS (
	                SELECT 1 
	                FROM Interactions i
	                WHERE i.articleId = b.articleId 
	                AND i.userId = #{userId}
	                AND i.type = 'RECOMMAND'
	            ) THEN true 
	            ELSE false 
	        END as isRecommand,
	        CASE 
               WHEN b.authorId = #{userId} THEN true 
               ELSE false 
            END as isAuthor,
            p.accompanyNum,
	        (
		        SELECT p.accompanyNum - COUNT(*)
		        FROM Accompany a
		        WHERE a.tripId = b.tripId
	        ) as accompanyCount,
	        p.regionId, p.departureDate, p.arrivedDate, p.tripDays, b.tripId
		    FROM Board b
		    LEFT JOIN Users u ON b.authorId = u.userId
		    LEFT JOIN PLANe p ON b.tripId = p.tripId
		    LEFT JOIN Region r ON p.regionId = r.regionId
		    <where>
			    b.deletedDate IS NULL
			    AND b.isPublic = true
			    AND u.deletedDate IS NULL
				<if test="articleSearchRequest.articleType != null">
					AND b.articleType = #{articleSearchRequest.articleType}
		        </if>
		        <if test="articleSearchRequest.regionId != null">
		            AND p.regionId = #{articleSearchRequest.regionId}
		        </if>
		        <if test="articleSearchRequest.tripPeriod != null">
		            AND p.departureDate >= #{articleSearchRequest.tripPeriod}
		        </if>
		        <if test="articleSearchRequest.tripDays != null">
		            AND p.tripDays = #{articleSearchRequest.tripDays}
		        </if>
		        <if test="articleSearchRequest.isRecommand">
			        AND EXISTS (
			        	SELECT 1
			        	FROM Interactions i
			        	WHERE i.articleId = b.articleId 
			        	AND i.userId = #{userId}
			        	AND i.type = 'RECOMMAND'
			        	)
			    </if>
			    <if test="articleSearchRequest.isSaved">
			        AND EXISTS (
			        	SELECT 1
			        	FROM Interactions i
			        	WHERE i.articleId = b.articleId 
			        	AND i.userId = #{userId}
			        	AND i.type = 'SAVE'
			        	)
			    </if>
			    <if test="articleSearchRequest.searchTitle != null">
		            AND b.title LIKE CONCAT('%', #{articleSearchRequest.searchTitle}, '%')
		        </if>
	        </where>
		    ORDER BY ${articleSearchRequest.pageRequest.sortBy} ${articleSearchRequest.pageRequest.sortDirection}
		    LIMIT #{articleSearchRequest.pageRequest.offset}, #{articleSearchRequest.pageRequest.size}
		    </script>
			""")
	@Results({
		@Result(property = "tripId", column = "tripId"),
		@Result(property = "tripThema", column = "tripId", many = @Many(select = "com.plane.trip.mapper.TripMapper.selectTripThemasByTripId"))
	})
	List<ArticleResponse> selectArticlesByPageRequest(@Param("userId") String userId, @Param("articleSearchRequest") ArticleSearchRequest articleSearchRequest);

	
	@Select("""
			SELECT EXISTS (
	            SELECT 1
	            FROM Board
	            WHERE articleId = #{articleId}
	            AND deletedDate IS NULL
	        )
			""")
	boolean existsArticleByArticleId(@Param("articleId") Long articleId);
	

	@Select("""
			SELECT *
			FROM Board
			WHERE authorId = #{userId}
			AND articleId = #{articleId}
			""")
	Article selectArticleByUserIdAndArticleId(@Param("userId") String userId, @Param("articleId") Long articleId);


	@Update("""
			UPDATE Board
			SET deletedDate = NOW()
			WHERE authorId = #{userId}
			AND articleId = #{articleId}
			""")
	int updateArticleDelete(@Param("userId") String userId, @Param("articleId") Long articleId);

	
	@Delete("""
			DELETE FROM Interactions
			WHERE userId = #{userId}
			AND articleId = #{articleInteractionRequset.articleId}
			AND type = #{articleInteractionRequset.interaction}
			""")
	int deleteInteractionByUserId(@Param("userId") String userId, @Param("articleInteractionRequset") ArticleInteractionRequset articleInteractionRequset);


	@Insert("""
			INSERT INTO Interactions (`userId`, `articleId`, `type`)
			VALUES (#{userId}, #{articleInteractionRequset.articleId}, #{articleInteractionRequset.interaction})
			""")
	int insertInteractionByUserId(@Param("userId") String userId, @Param("articleInteractionRequset") ArticleInteractionRequset articleInteractionRequset);

	
	@Select("""
			SELECT EXISTS (
	            SELECT 1
	            FROM Report
	            WHERE articleId = #{articleId}
	            AND userId = #{userId}
	        )
			""")
	boolean existsReportByUserIdAndArticleId(@Param("userId") String userId, @Param("articleId") Long articleId);
	
	
	@Insert("""
			INSERT INTO Report (`articleId`, `userId`, `reportId`, `details`)
			VALUES (#{articleReportRequest.articleId}, #{userId}, #{articleReportRequest.reportId}, #{articleReportRequest.details})
			""")
	int insertReport(@Param("userId") String userId, @Param("articleReportRequest") ArticleReportRequest articleReportRequest);


	@Select("""
			SELECT title, authorId
			FROM Board
			WHERE articleId = #{articleId}
			""")
	ArticleNotificationInfo selectArticleNotificationInfo(@Param("articleId") Long articleId);

	
	@Insert("""
			INSERT INTO Board (`authorId`, `tripId`, `articleType`, `title`, `content`, `articlePictureUrl`, `isPublic`)
			VALUES (#{userId}, #{request.tripId}, #{request.articleType}, #{request.title}, #{request.content}, #{request.articlePictureUrl}, #{request.isPublic})
			""")
	int insertArticle(@Param("userId") String userId, @Param("request") ArticleCreateRequest articleCreateRequest);

	
	@Update("""
			UPDATE Board
			SET isPublic = #{request.isPublic}
			WHERE authorId = #{userId}
			AND articleId = #{request.articleId}
			""")
	int updateArticlePublic(@Param("userId") String userId, @Param("request") ChangePublicRequest changePublicRequest);

	
	@Update("""
			UPDATE PLANe p
			SET p.accompanyNum = #{accompanyNum}
			WHERE p.tripId = (
			    SELECT b.tripId 
			    FROM Board b 
			    WHERE b.articleId = #{articleId}
			    AND b.articleType = '동행'
			)
			""")
	int updateAccompanyNum(@Param("articleId") Long articleId, @Param("accompanyNum") Integer accompanyNum);

	
	@Select("""
			SELECT articleId
			FROM Board
			WHERE tripId = #{tripId}
			""")
	Long selectArticleIdByTripId(@Param("tripId") Long tripId);
	
	
	@Select("""
			SELECT tripId
			FROM Board
			WHERE articleId = #{articleId}
			""")
	Long selectTripIdByArticleId(@Param("articleId") Long articleId);


}
