package com.plane.comment.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
//	@Disabled
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
}