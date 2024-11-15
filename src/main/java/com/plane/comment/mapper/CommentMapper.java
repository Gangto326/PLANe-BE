package com.plane.comment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.plane.comment.dto.CommentResponse;

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
	List<CommentResponse> selectCommentByArticleId(String userId, int articleId);

}
