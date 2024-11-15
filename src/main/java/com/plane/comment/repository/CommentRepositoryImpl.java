package com.plane.comment.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.plane.comment.dto.CommentResponse;
import com.plane.comment.mapper.CommentMapper;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

	private final CommentMapper commentMapper;

	@Autowired
	public CommentRepositoryImpl(CommentMapper commentMapper) {
		this.commentMapper = commentMapper;
	}

	@Override
	public List<CommentResponse> selectCommentByArticleId(String userId, int articleId) {
		
		return commentMapper.selectCommentByArticleId(userId, articleId);
	}
	
	
}
