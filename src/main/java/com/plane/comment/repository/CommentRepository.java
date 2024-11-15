package com.plane.comment.repository;

import java.util.List;

import com.plane.comment.domain.Comment;
import com.plane.comment.dto.CommentDeleteRequest;
import com.plane.comment.dto.CommentRequest;
import com.plane.comment.dto.CommentResponse;
import com.plane.comment.dto.CommentUpdateRequest;

import jakarta.validation.Valid;

public interface CommentRepository {

	List<CommentResponse> selectCommentByArticleId(String userId, int articleId);

	int insertComment(String userId, CommentRequest commentRequest);

	int updateComment(String userId, CommentUpdateRequest commentUpdateRequest);

	Comment selectCommentByCommentId(Integer commentId);

	int deleteComment(String userId, CommentDeleteRequest commentDeleteRequest);

}
