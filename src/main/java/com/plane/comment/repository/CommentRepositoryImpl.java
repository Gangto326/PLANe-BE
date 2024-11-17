package com.plane.comment.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.plane.comment.domain.Comment;
import com.plane.comment.dto.CommentDeleteRequest;
import com.plane.comment.dto.CommentReportRequest;
import com.plane.comment.dto.CommentRequest;
import com.plane.comment.dto.CommentResponse;
import com.plane.comment.dto.CommentUpdateRequest;
import com.plane.comment.mapper.CommentMapper;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

	private final CommentMapper commentMapper;

	@Autowired
	public CommentRepositoryImpl(CommentMapper commentMapper) {
		this.commentMapper = commentMapper;
	}

	@Override
	public List<CommentResponse> selectCommentByArticleId(String userId, Long articleId) {
		
		return commentMapper.selectCommentByArticleId(userId, articleId);
	}

	@Override
	public int insertComment(String userId, CommentRequest commentRequest) {

		return commentMapper.insertComment(userId, commentRequest);
	}

	@Override
	public int updateComment(String userId, CommentUpdateRequest commentUpdateRequest) {
		
		return commentMapper.updateComment(userId, commentUpdateRequest);
	}

	@Override
	public Comment selectCommentByCommentId(Integer commentId) {
		
		return commentMapper.selectCommentByCommentId(commentId);
	}

	@Override
	public int deleteComment(String userId, CommentDeleteRequest commentDeleteRequest) {
		
		return commentMapper.updateCommentStatusDelete(userId, commentDeleteRequest);
	}

	@Override
	public boolean existsCommentByCommentId(Integer commentId) {
		
		return commentMapper.existsCommentByCommentId(commentId);
	}

	@Override
	public boolean existsReportByUserIdAndCommentId(String userId, Integer commentId) {
		
		return commentMapper.existsReportByUserIdAndCommentId(userId, commentId);
	}

	@Override
	public int insertReport(String userId, CommentReportRequest commentReportRequest) {
		
		return commentMapper.insertReport(userId, commentReportRequest);
	}
	
	
}
