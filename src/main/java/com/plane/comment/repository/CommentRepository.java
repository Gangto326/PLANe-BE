package com.plane.comment.repository;

import java.util.List;

import com.plane.comment.dto.CommentResponse;

public interface CommentRepository {

	List<CommentResponse> selectCommentByArticleId(String userId, int articleId);

}
