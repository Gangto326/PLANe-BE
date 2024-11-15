package com.plane.comment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plane.comment.dto.CommentResponse;
import com.plane.comment.service.CommentService;
import com.plane.common.annotation.UserId;
import com.plane.common.response.ApiResponse;

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
	
	
}
