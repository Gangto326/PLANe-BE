package com.plane.accompany.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.plane.accompany.dto.AccompanyApplyDto;
import com.plane.accompany.dto.AccompanyDetailRequest;

@Mapper
public interface AccompanyMapper {
	
	@Select("""
			SELECT EXISTS (
	            SELECT 1
	            FROM Board
	            WHERE articleId = #{articleId}
	            AND articleType = '동행'
	        )
			""")
	boolean existsAccompanyArticleByArticleId(@Param("articleId") Long articleId);
	
	
	@Select("""
			SELECT EXISTS (
	            SELECT 1
	            FROM AccompanyApply
	            WHERE articleId = #{articleId}
	            AND userId = #{userId}
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
            INSERT INTO ApplyDetails (`askId`, `applyId`, `answer`)
            VALUES
            <foreach collection="details" item="detail" separator=",">
            	(#{detail.askId}, #{applyId}, #{detail.answer})
            </foreach>
            </script>
            """)
    int insertApplyDetails(@Param("applyId") Long applyId, @Param("details") List<AccompanyDetailRequest> accompanyDetailRequest);


}
