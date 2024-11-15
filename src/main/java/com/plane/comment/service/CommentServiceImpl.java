package com.plane.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.article.repository.ArticleRepository;
import com.plane.comment.dto.CommentRequest;
import com.plane.comment.dto.CommentResponse;
import com.plane.comment.repository.CommentRepository;
import com.plane.common.exception.custom.ArticleNotFoundException;
import com.plane.common.exception.custom.CommentCreationFailedException;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;
	private final ArticleRepository articleRepository;

	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository, ArticleRepository articleRepository) {
		this.commentRepository = commentRepository;
		this.articleRepository = articleRepository;
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


	@Override
	public boolean createComment(String userId, CommentRequest commentRequest) {
		
		if (!articleRepository.existsArticleByArticleId(commentRequest.getArticleId())) {
			throw new ArticleNotFoundException("해당 게시글이 존재하지 않습니다.");
		}
		
		if (commentRepository.insertComment(userId, commentRequest) == 1) {
			return true;
		}
		
		throw new CommentCreationFailedException("댓글 생성에 실패했습니다.");
	}
	
	
	
	
}
