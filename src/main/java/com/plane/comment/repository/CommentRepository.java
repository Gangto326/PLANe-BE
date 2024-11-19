package com.plane.comment.repository;

import java.util.List;

import com.plane.comment.domain.Comment;
import com.plane.comment.dto.CommentDeleteRequest;
import com.plane.comment.dto.CommentNotificationInfo;
import com.plane.comment.dto.CommentReportRequest;
import com.plane.comment.dto.CommentRequest;
import com.plane.comment.dto.CommentResponse;
import com.plane.comment.dto.CommentUpdateRequest;

import jakarta.validation.Valid;

public interface CommentRepository {

	List<CommentResponse> selectCommentByArticleId(String userId, Long articleId);

	int insertComment(String userId, CommentRequest commentRequest);

	int updateComment(String userId, CommentUpdateRequest commentUpdateRequest);

	Comment selectCommentByCommentId(Long commentId);

	int deleteComment(String userId, CommentDeleteRequest commentDeleteRequest);

	boolean existsCommentByCommentId(Long commentId);

	boolean existsReportByUserIdAndCommentId(String userId, Long commentId);

	int insertReport(String userId, CommentReportRequest commentReportRequest);

	CommentNotificationInfo selectCommentNotificationInfo(Long commentId);
}
