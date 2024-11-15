package com.plane.comment.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plane.comment.dto.CommentDeleteRequest;
import com.plane.comment.dto.CommentRequest;
import com.plane.comment.dto.CommentUpdateRequest;
import com.plane.comment.repository.CommentRepository;
import com.plane.comment.service.CommentService;

@AutoConfigureMockMvc
@SpringBootTest(
		properties = { 
			"spring.config.location=classpath:application.properties" 
		}
	)
//@Transactional
public class CommentControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
    private CommentRepository commentRepository;
	
	
	@Test
	@DisplayName("댓글 가져오기")
	@Disabled
	void testCommentList() throws Exception {
		System.out.println("==== CommentList Test Start ====");
		
		String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMTY1NDUyOSwiZXhwIjoxNzMxNjkwNTI5fQ.v8AfQ_sjUddlRCNbzRcdt9F0mfM07FAU-uLClPvFiF67DQR8SgINeaFGC23l9rCH";
		
		mockMvc.perform(get("/api/comment/{articleId}", 4)
				.header("Authorization", "Bearer " + accessToken)
	            .contentType(MediaType.APPLICATION_JSON))
	            .andDo(print())
	            .andExpect(status().isOk());
		
        System.out.println("==== CommentList Test End ====");

	}
	
	@Test
	@DisplayName("댓글 생성하기")
	@Disabled
	void testComment() throws Exception {
		System.out.println("==== Comment Test Start ====");
		
		String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMTY1NDUyOSwiZXhwIjoxNzMxNjkwNTI5fQ.v8AfQ_sjUddlRCNbzRcdt9F0mfM07FAU-uLClPvFiF67DQR8SgINeaFGC23l9rCH";
		
		CommentRequest commentRequest = new CommentRequest();
		commentRequest.setArticleId(8);
		commentRequest.setCommentContents("댓글댓글");
		commentRequest.setParents(null);
		commentRequest.setStatus("공개");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(commentRequest);
		
		mockMvc.perform(post("/api/comment")
				.header("Authorization", "Bearer " + accessToken)
	            .contentType(MediaType.APPLICATION_JSON)
				.content(content))
		        .andDo(print())
		        .andExpect(status().isOk());
		
        System.out.println("==== Comment Test End ====");

	}
	
	@Test
	@DisplayName("댓글 변경하기")
	@Disabled
	void testUpdateComment() throws Exception {
		System.out.println("==== UpdateComment Test Start ====");
		
		String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMTY1NDUyOSwiZXhwIjoxNzMxNjkwNTI5fQ.v8AfQ_sjUddlRCNbzRcdt9F0mfM07FAU-uLClPvFiF67DQR8SgINeaFGC23l9rCH";
		
		CommentUpdateRequest commentUpdateRequest = new CommentUpdateRequest();
		
		commentUpdateRequest.setArticleId(8);
		commentUpdateRequest.setCommentId(1);
		commentUpdateRequest.setCommentContents("수정수정");
		commentUpdateRequest.setStatus("비공개");
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(commentUpdateRequest);
		
		System.out.println(content);
		
		mockMvc.perform(patch("/api/comment")
				.header("Authorization", "Bearer " + accessToken)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(content))
	            .andDo(print())
	            .andExpect(status().isOk());
		
        System.out.println("==== UpdateComment Test End ====");

	}
	
	@Test
	@DisplayName("댓글 삭제하기")
	@Disabled
	void testDeleteComment() throws Exception {
		System.out.println("==== DeleteComment Test Start ====");
		
		String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMTY1NDUyOSwiZXhwIjoxNzMxNjkwNTI5fQ.v8AfQ_sjUddlRCNbzRcdt9F0mfM07FAU-uLClPvFiF67DQR8SgINeaFGC23l9rCH";
		
		CommentDeleteRequest commentDeleteRequest = new CommentDeleteRequest();
		
		commentDeleteRequest.setArticleId(8);
		commentDeleteRequest.setCommentId(1);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(commentDeleteRequest);
		
		System.out.println(content);
		
		mockMvc.perform(delete("/api/comment")
				.header("Authorization", "Bearer " + accessToken)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(content))
	            .andDo(print())
	            .andExpect(status().isOk());
		
        System.out.println("==== DeleteComment Test End ====");

	}
}