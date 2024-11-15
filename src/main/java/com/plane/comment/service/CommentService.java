package com.plane.comment.service;

import java.util.List;

import com.plane.comment.dto.CommentDeleteRequest;
import com.plane.comment.dto.CommentRequest;
import com.plane.comment.dto.CommentResponse;
import com.plane.comment.dto.CommentUpdateRequest;

import jakarta.validation.Valid;

public interface CommentService {

	List<CommentResponse> getCommentList(String userId, int articleId);

	boolean createComment(String userId, CommentRequest commentRequest);

	boolean updateComment(String userId, CommentUpdateRequest commentUpdateRequest);

	boolean deleteComment(String userId, CommentDeleteRequest commentDeleteRequest);

}
