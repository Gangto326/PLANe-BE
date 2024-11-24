package com.plane.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.article.repository.ArticleRepository;
import com.plane.comment.domain.Comment;
import com.plane.comment.dto.CommentDeleteRequest;
import com.plane.comment.dto.CommentNotificationInfo;
import com.plane.comment.dto.CommentReportRequest;
import com.plane.comment.dto.CommentRequest;
import com.plane.comment.dto.CommentResponse;
import com.plane.comment.dto.CommentUpdateRequest;
import com.plane.comment.repository.CommentRepository;
import com.plane.common.exception.custom.ArticleNotFoundException;
import com.plane.common.exception.custom.CreationFailedException;
import com.plane.common.exception.custom.DuplicateException;
import com.plane.common.exception.custom.CommentNotFoundException;
import com.plane.common.exception.custom.CommentUpdateException;
import com.plane.common.exception.custom.UnauthorizedException;
import com.plane.notification.dto.NotificationAction;
import com.plane.notification.dto.NotificationCreateRequest;
import com.plane.notification.dto.NotificationTargetType;
import com.plane.notification.service.NotificationService;

import jakarta.validation.Valid;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	private final CommentRepository commentRepository;
	private final ArticleRepository articleRepository;
	private final NotificationService notificationService;

	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository, ArticleRepository articleRepository, NotificationService notificationService) {
		this.commentRepository = commentRepository;
		this.articleRepository = articleRepository;
		this.notificationService = notificationService;
	}

	
	@Override
	public List<CommentResponse> getCommentList(String userId, Long articleId) {
		
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
		
		throw new CreationFailedException("댓글 생성에 실패했습니다.");
	}


	@Override
	public boolean updateComment(String userId, CommentUpdateRequest commentUpdateRequest) {
		
		Comment comment = commentRepository.selectCommentByCommentId(commentUpdateRequest.getCommentId());
		
		if (comment == null) {
			throw new CommentNotFoundException("해당 댓글이 존재하지 않습니다.");
		}
		
		if (comment.getArticleId() != commentUpdateRequest.getArticleId() ||
				!articleRepository.existsArticleByArticleId(commentUpdateRequest.getArticleId())) {
			throw new ArticleNotFoundException("해당 게시글이 존재하지 않습니다.");
		}
		
		if (!comment.getAuthorId().equals(userId)) {
			throw new UnauthorizedException("댓글 수정 권한이 없습니다.");
		}
		
		if (commentRepository.updateComment(userId, commentUpdateRequest) == 1) {
			return true;
		}
		
		throw new CommentUpdateException("댓글 수정 중 오류가 발생했습니다.");
	}


	@Override
	public boolean deleteComment(String userId, CommentDeleteRequest commentDeleteRequest) {
		
		Comment comment = commentRepository.selectCommentByCommentId(commentDeleteRequest.getCommentId());
		
		if (comment == null) {
			throw new CommentNotFoundException("해당 댓글이 존재하지 않습니다.");
		}
		
		if (comment.getArticleId() != commentDeleteRequest.getArticleId() ||
				!articleRepository.existsArticleByArticleId(commentDeleteRequest.getArticleId())) {
			throw new ArticleNotFoundException("해당 게시글이 존재하지 않습니다.");
		}
		
		if (!comment.getAuthorId().equals(userId)) {
			throw new UnauthorizedException("댓글 삭제 권한이 없습니다.");
		}
		
		if (commentRepository.deleteComment(userId, commentDeleteRequest) == 1) {
			return true;
		}
		
		throw new CommentUpdateException("댓글 삭제 중 오류가 발생했습니다.");
	}


	@Override
	public boolean reportComment(String userId, CommentReportRequest commentReportRequest) {
		
		if (!commentRepository.existsCommentByCommentId(commentReportRequest.getCommentId())) {
			throw new ArticleNotFoundException("댓글을 찾을 수 없습니다.");
		}
		
		if (commentRepository.existsReportByUserIdAndCommentId(userId, commentReportRequest.getCommentId())) {
			throw new DuplicateException("이미 신고한 글입니다.");
		}
		
		if (commentRepository.insertReport(userId, commentReportRequest) == 1) {
			
			try {
				CommentNotificationInfo notificationInfo = commentRepository.selectCommentNotificationInfo(commentReportRequest.getCommentId());
				
				NotificationCreateRequest notificationCreateRequest = new NotificationCreateRequest();
				notificationCreateRequest.setNotificationType("댓글");
				notificationCreateRequest.setContentId(notificationInfo.getArticleId());
				notificationCreateRequest.setDetails(notificationInfo.getCommentContents());
				notificationCreateRequest.setType(NotificationTargetType.COMMENT);
				notificationCreateRequest.setAction(NotificationAction.REPORT);
				
				notificationService.createNotification(notificationInfo.getAuthorId(), notificationCreateRequest);
			} catch (Exception e) {
				return true;
			}
			
			return true;
		}
		
		throw new CreationFailedException("신고글 생성에 실패하였습니다");
	}
	
}
