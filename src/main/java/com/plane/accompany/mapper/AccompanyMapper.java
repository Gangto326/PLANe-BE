package com.plane.accompany.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.plane.accompany.dto.AccompanyApplyDto;
import com.plane.accompany.dto.AccompanyDetailRequest;
import com.plane.accompany.dto.AccompanyResponse;
import com.plane.accompany.dto.ApplyType;

@Mapper
public interface AccompanyMapper {
	
	@Select("""
			SELECT EXISTS (
	            SELECT 1
	            FROM Board
	            WHERE articleId = #{articleId}
	            AND articleType = '동행'
	            AND deletedDate IS NULL
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
	        SET status = '수정'
			WHERE userId = #{userId}
			AND applyId = #{applyId}
			""")
	int updateApplyStatus(@Param("userId") String userId, @Param("applyId") Long applyId);


	@Update("""
			UPDATE AccompanyApply
			SET deletedDate = NOW()
			WHERE userId = #{userId}
			AND applyId = #{applyId}
			""")
	int updateApplyStatusDelete(String userId, Long applyId);


}
