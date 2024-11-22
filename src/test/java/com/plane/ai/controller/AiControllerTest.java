package com.plane.ai.controller;

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
import com.plane.ai.dto.AiGenerateRequest;
import com.plane.ai.service.AiService;

@AutoConfigureMockMvc
@SpringBootTest(
		properties = { 
			"spring.config.location=classpath:application.properties" 
		}
	)
public class AiControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private AiService aiService;
	
	
	@Test
	@DisplayName("Ai 테스트")
	@Disabled
	void testAi() throws Exception {
		System.out.println("==== Ai Test Start ====");
		
		String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMjIzMzYxMSwiZXhwIjoxNzMyMjY5NjExfQ.Di38kTx-K_sqYVnodt9l6yo0aJbN-tPbQl6JuNvv8DGvHjVuIYHZ81s5msIBLwew";
		
		AiGenerateRequest aiGenerateRequest = new AiGenerateRequest();
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(aiGenerateRequest);
		
		mockMvc.perform(post("/api/ai/generate")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + accessToken)
				.content(content))
		        .andDo(print())
		        .andExpect(status().isOk());
		
        System.out.println("==== Ai Test End ====");

	}
	
}
