package com.plane.comment.repository;

import java.util.List;

import com.plane.comment.dto.CommentRequest;
import com.plane.comment.dto.CommentResponse;

public interface CommentRepository {

	List<CommentResponse> selectCommentByArticleId(String userId, int articleId);

	int insertComment(String userId, CommentRequest commentRequest);

}
