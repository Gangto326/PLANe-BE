package com.plane.comment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plane.comment.dto.CommentDeleteRequest;
import com.plane.comment.dto.CommentReportRequest;
import com.plane.comment.dto.CommentRequest;
import com.plane.comment.dto.CommentResponse;
import com.plane.comment.dto.CommentUpdateRequest;
import com.plane.comment.service.CommentService;
import com.plane.common.annotation.UserId;
import com.plane.common.response.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

	private final CommentService commentService;
	
	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	
	@GetMapping("/{articleId}")
	public ResponseEntity<ApiResponse<List<CommentResponse>>> commentList(
			@UserId String userId,
			@PathVariable int articleId
			) {
		
		List<CommentResponse> commentList = commentService.getCommentList(userId, articleId);
		return ResponseEntity.ok(ApiResponse.success(commentList, "댓글을 불러왔습니다."));
	}
	
	
	@PostMapping("")
	public ResponseEntity<ApiResponse<Boolean>> comment(
			@UserId String userId,
			@Valid @RequestBody CommentRequest commentRequest
			) {
		
		commentService.createComment(userId, commentRequest);
		return ResponseEntity.ok(ApiResponse.success(true, "댓글 작성 성공."));
	}
	
	
	@PatchMapping("")
	public ResponseEntity<ApiResponse<Boolean>> commentUpdate(
			@UserId String userId,
			@Valid @RequestBody CommentUpdateRequest commentUpdateRequest
			) {
		
		commentService.updateComment(userId, commentUpdateRequest);
		return ResponseEntity.ok(ApiResponse.success(true, "댓글 수정 성공."));
	}
	
	
	@DeleteMapping("")
	public ResponseEntity<ApiResponse<Boolean>> commentDelete(
			@UserId String userId,
			@Valid @RequestBody CommentDeleteRequest commentDeleteRequest
			) {
		
		commentService.deleteComment(userId, commentDeleteRequest);
		return ResponseEntity.ok(ApiResponse.success(true, "댓글 삭제 성공."));
	}
	
	@PostMapping("/report")
	public ResponseEntity<ApiResponse<Boolean>> commentReport(
			@UserId String userId,
			@Valid @RequestBody CommentReportRequest commentReportRequest
			) {
		
		commentService.reportComment(userId, commentReportRequest);
		return ResponseEntity.ok(ApiResponse.success(true, "댓글 신고가 완료되었습니다."));
	}
}
