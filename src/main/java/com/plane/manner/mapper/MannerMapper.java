package com.plane.manner.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.plane.manner.dto.MannerEvaluateRequest;

@Mapper
public interface MannerMapper {
	
	@Select("""
			SELECT manner
			FROM Users
			WHERE userId = #{evaluatorId}
			""")
	Double selectCurrentScore(String evaluatorId);
	
	
	@Insert("""
			<script>
			INSERT INTO Manners (userId, evaluatorId, mannerTagId, score)
			VALUES
			<foreach collection="request.tagList" item="tag" separator=",">
				(#{userId}, #{request.evaluatorId}, #{tag.mannerTagId}, #{tag.score} - 3)
			</foreach>
			</script>
			""")
	int insertManners(@Param("userId") String userId, @Param("request") MannerEvaluateRequest mannerEvaluateRequest);
	
	
	@Update("""
			UPDATE Users
			SET manner = #{newScore}
			WHERE userId = #{evaluatorId}
			""")
	int updateUserManner(@Param("evaluatorId") String evaluatorId, @Param("currentScore") double newScore);

	
}
