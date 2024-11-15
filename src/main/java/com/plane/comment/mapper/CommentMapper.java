package com.plane.comment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.plane.comment.domain.Comment;
import com.plane.comment.dto.CommentDeleteRequest;
import com.plane.comment.dto.CommentRequest;
import com.plane.comment.dto.CommentResponse;
import com.plane.comment.dto.CommentUpdateRequest;

@Mapper
public interface CommentMapper {
	
	@Select("""
			SELECT c.commentId, u.nickName as authorNickName, c.parents, c.commentContents, c.status,
			CASE
				WHEN c.authorId = #{userId} THEN true
			ELSE false 
			END as isMine,
			CASE
				WHEN c.status = '공개' THEN false
				WHEN b.authorId = #{userId} THEN false
				WHEN c.authorId = #{userId} THEN false
				WHEN pc.authorId = #{userId} THEN false
			ELSE true
			END as isHidden,
			c.createdDate, c.updatedDate
			FROM Comment c
			JOIN Users u ON c.authorId = u.userId
			JOIN Board b ON c.articleId = b.articleId
			LEFT JOIN Comment pc ON c.parents = pc.commentId
			WHERE c.articleId = #{articleId}
			ORDER BY 
			COALESCE(c.parents, c.commentId),
			c.createdDate ASC
			""")
	List<CommentResponse> selectCommentByArticleId(@Param("userId") String userId, @Param("articleId") int articleId);
	
	
	@Insert("""
			INSERT INTO Comment (`articleId`, `authorId`, `parents`, `commentContents`, `status`)
			VALUES (#{commentRequest.articleId}, #{userId}, #{commentRequest.parents}, #{commentRequest.commentContents}, #{commentRequest.status})
			""")
	int insertComment(@Param("userId") String userId, @Param("commentRequest") CommentRequest commentRequest);


	@Update("""
			UPDATE Comment
			SET commentContents = #{commentUpdateRequest.commentContents},
				status = #{commentUpdateRequest.status},
				updatedDate = NOW()
			WHERE commentId = #{commentUpdateRequest.commentId}
			AND articleId = #{commentUpdateRequest.articleId}
			AND authorId = #{userId}
			""")
	int updateComment(@Param("userId") String userId, @Param("commentUpdateRequest") CommentUpdateRequest commentUpdateRequest);

	
	@Select("""
			SELECT *
			FROM Comment
			WHERE commentId = #{commentId}
			""")
	Comment selectCommentByCommentId(@Param("commentId") Integer commentId);


	
	@Update("""
			UPDATE Comment
			SET status = '삭제',
				updatedDate = NOW()
			WHERE commentId = #{commentDeleteRequest.commentId}
			AND articleId = #{commentDeleteRequest.articleId}
			AND authorId = #{userId}
			""")
	int updateCommentStatusDelete(@Param("userId") String userId, @Param("commentDeleteRequest") CommentDeleteRequest commentDeleteRequest);

}
