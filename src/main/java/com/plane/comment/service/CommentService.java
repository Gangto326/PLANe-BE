package com.plane.comment.service;

import java.util.List;

import com.plane.comment.dto.CommentResponse;

public interface CommentService {

	List<CommentResponse> getCommentList(String userId, int articleId);

}
