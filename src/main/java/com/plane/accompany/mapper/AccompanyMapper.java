package com.plane.accompany.mapper;

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

import com.plane.accompany.dto.AccompanyAcceptRequest;
import com.plane.accompany.dto.AccompanyApplyDto;
import com.plane.accompany.dto.AccompanyDetailDto;
import com.plane.accompany.dto.AccompanyDetailRequest;
import com.plane.accompany.dto.AccompanyDetailResponse;
import com.plane.accompany.dto.AccompanyResponse;
import com.plane.accompany.dto.AccompanyTripInfo;
import com.plane.accompany.dto.ApplyType;
import com.plane.manner.dto.MannerUserResponse;
import com.plane.notification.dto.AccompanyNotificationDto;

@Mapper
public interface AccompanyMapper {
	
	@Select("""
			SELECT EXISTS (
				SELECT 1
				FROM Board b
				JOIN PLANe p ON b.tripId = p.tripId
				WHERE b.articleId = #{articleId}
				AND b.articleType = '동행'
				AND b.deletedDate IS NULL
				AND p.departureDate > CURRENT_DATE()
			)
			""")
	boolean existsAccompanyArticleByArticleId(@Param("articleId") Long articleId);
	
	
	@Select("""
			SELECT EXISTS (
	            SELECT 1
	            FROM AccompanyApply
	            WHERE articleId = #{articleId}
	            AND userId = #{userId}
	            AND deletedDate IS NULL
	        )
			""")
	boolean existsRegistByUserIdAndArticleId(@Param("userId") String userId, @Param("articleId") Long articleId);
	

	@Insert("""
			INSERT INTO AccompanyApply (`articleId`, `userId`)
            VALUES (#{apply.articleId}, #{apply.userId})
			""")
	@Options(useGeneratedKeys = true, keyProperty = "apply.applyId")
	int insertAccompanyApply(@Param("apply") AccompanyApplyDto accompanyApplyDto);

	
	@Insert("""
			<script>
            INSERT INTO ApplyDetails (`askId`, `applyId`, `answer`
            <if test="isUpdate">
			    , `updatedDate`
	        </if>
	        )
            VALUES
            <foreach collection="details" item="detail" separator=",">
            	(#{detail.askId}, #{applyId}, #{detail.answer}
            	<if test="isUpdate">
	                , NOW()
	            </if>
	            )
            </foreach>
            </script>
            """)
    int insertApplyDetails(@Param("applyId") Long applyId, @Param("details") List<AccompanyDetailRequest> accompanyDetailRequest, @Param("isUpdate") boolean isUpdate);


	@Select("""
	        SELECT a.applyId, a.articleId, b.title, u.nickName, a.isOk, a.status, a.createdDate
	        FROM AccompanyApply a
	        JOIN Board b ON a.articleId = b.articleId
	        JOIN Users u ON ${type.userColumn} = u.userId
	        WHERE ${type.whereCondition} = #{userId}
	        AND a.deletedDate IS NULL
	        """)
	List<AccompanyResponse> findAccompanyList(@Param("userId") String userId, @Param("type") ApplyType type);


	@Select("""
			SELECT EXISTS (
	            SELECT 1
	            FROM AccompanyApply
	            WHERE userId = #{userId}
	            AND applyId = #{applyId}
	            AND deletedDate IS NULL
	        )
			""")
	boolean existsRegistByUserIdAndApplyId(@Param("userId") String userId, @Param("applyId") Long applyId);


	@Delete("""
			DELETE FROM ApplyDetails
			WHERE applyId = #{applyId}
			""")
	int deleteAllApplyDetails(@Param("applyId") Long applyId);

	
	@Update("""
			UPDATE AccompanyApply 
	        SET status = #{status}
			WHERE applyId = #{applyId}
			""")
	int updateApplyStatus(@Param("applyId") Long applyId, @Param("status") String status);


	@Update("""
			UPDATE AccompanyApply
			SET deletedDate = NOW()
			WHERE userId = #{userId}
			AND applyId = #{applyId}
			""")
	int updateApplyStatusDelete(@Param("userId") String userId, @Param("applyId") Long applyId);
	

	@Select("""
			SELECT EXISTS (
	            SELECT 1
	            FROM AccompanyApply
	            WHERE applyId = #{applyId}
	            AND deletedDate IS NULL
	        )
			""")
	boolean existsRegistByApplyId(@Param("applyId") Long applyId);


	@Select("""
	        SELECT 
	            CASE 
	                WHEN #{type} = 'RECEIVED' THEN a.userId
	                ELSE b.authorId
	            END as userId,
	            b.articleId, u.nickName, a.applyId, a.status
	        FROM AccompanyApply a
	        JOIN Board b ON a.articleId = b.articleId
	        JOIN Users u ON u.userId = CASE 
	            WHEN #{type} = 'RECEIVED' THEN a.userId
	            ELSE b.authorId
	        END
	        WHERE a.applyId = #{applyId}
	        AND ${type.whereCondition} = #{userId}
	        AND a.deletedDate IS NULL
	        AND b.deletedDate IS NULL
	        """)
	@Results({
	    @Result(property = "userId", column = "userId"),
	    @Result(property = "nickName", column = "nickName"),
	    @Result(property = "detailList", column = "applyId", javaType = List.class,
	        many = @Many(select = "findApplyDetails"))
	})
	AccompanyDetailResponse findAccompanyDetail(@Param("userId") String userId, @Param("applyId") Long applyId, @Param("type") ApplyType type);


	@Select("""
	        SELECT 
	            ad.askId,
	            ad.answer
	        FROM ApplyDetails ad
	        WHERE ad.applyId = #{applyId}
	        ORDER BY ad.askId
	        """)
	List<AccompanyDetailDto> findApplyDetails(@Param("applyId") Long applyId);


	@Select("""
	        SELECT p.tripId, p.accompanyNum, aa.userId as applicantId, b.articleId
	        FROM AccompanyApply aa
	        JOIN Board b ON aa.articleId = b.articleId
	        JOIN PLANe p ON b.tripId = p.tripId
	        WHERE aa.applyId = #{applyId}
	        AND b.authorId = #{userId}
	        AND aa.deletedDate IS NULL
	        AND b.deletedDate IS NULL
	        """)
	AccompanyTripInfo findTripInfo(@Param("userId") String userId, @Param("applyId") Long applyId);

	
	@Select("""
			SELECT COUNT(*)
			FROM Accompany
			WHERE tripId = #{tripId}
			AND deletedDate IS NULL
			""")
	int countAccompanyByTripId(@Param("tripId") Long tripId);

	
	@Insert("""
			INSERT INTO Accompany (tripId, userId, role)
			VALUES (#{tripId}, #{applicantId}, #{role})
			""")
	int insertAccompany(@Param("tripId") Long tripId, @Param("applicantId") String applicantId, @Param("role") String role);
	
	
	@Select("""
			SELECT EXISTS (
	            SELECT 1
		        FROM Accompany a
		        JOIN PLANe p ON a.tripId = p.tripId
		        WHERE a.tripId = #{tripId}
		        AND a.userId = #{userId}
		        AND p.deletedDate IS NULL
	        )
			""")
	boolean existsAccompanyByUserIdAndTripId(@Param("userId") String userId, @Param("tripId") Long tripId);


	@Select("""
		    SELECT a.userId, p.tripId, p.tripName, p.arrivedDate
		    FROM PLANe p
		    JOIN Accompany a ON p.tripId = a.tripId
		    WHERE p.deletedDate IS NULL
		    AND p.accompanyNum > 1
		    AND p.arrivedDate = CURDATE()
		    """)
	List<AccompanyNotificationDto> findAllArrivedAccompany();

	
	@Select("""
			SELECT u.userId, u.nickName
			FROM Accompany a
			JOIN Users u ON a.userId = u.userId
			WHERE a.tripId = #{tripId}
			ORDER BY FIELD(a.role, '팀장', '동행원', '일반'), u.nickName
			""")
	List<MannerUserResponse> findAllAccompany(Long tripId);

	
	@Select("""
			SELECT userId
			FROM Accompany
			WHERE tripId = #{tripId}
			AND userId != #{userId}
			""")
	List<String> findAllAccompanyUserId(String userId, Long tripId);
	
}
