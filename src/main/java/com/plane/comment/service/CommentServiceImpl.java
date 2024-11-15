package com.plane.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.comment.dto.CommentResponse;
import com.plane.comment.repository.CommentRepository;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;

	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	
	@Override
	public List<CommentResponse> getCommentList(String userId, int articleId) {
		
		List<CommentResponse> commentList = commentRepository.selectCommentByArticleId(userId, articleId);
		
		for (CommentResponse comment: commentList) {
			if (comment.getStatus().equals("삭제")) {
				comment.setCommentContents("삭제된 댓글입니다.");
			}
			
			if (comment.isHidden()) {
				comment.setCommentContents("비밀 댓글입니다.");
			}
		}
		
		return commentList;
	}
	
	
	
}
