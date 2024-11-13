package com.plane.article.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plane.article.dto.ArticleUpdateRequest;
import com.plane.article.repository.ArticleRepository;
import com.plane.article.service.ArticleService;

@AutoConfigureMockMvc
@SpringBootTest(
		properties = { 
			"spring.config.location=classpath:application.properties" 
		}
	)
//@Transactional
public class ArticleControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
    private ArticleRepository articleRepository;
	
	
	@Test
	@DisplayName("게시글 상세보기")
	@Disabled
	void testArticleDetail() throws Exception {
		System.out.println("==== ArticleDetail Test Start ====");
		
		mockMvc.perform(get("/api/article/{articleId}", 1)
				.param("currentUserId", "user001")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andDo(print())                             
	            .andExpect(status().isOk());
		
        System.out.println("==== ArticleDetail Test End ====");

	}
	
	
	@Test
	@DisplayName("게시글 수정하기")
//	@Disabled
	void testUpdateArticle() throws Exception {
		System.out.println("==== UpdateArticle Test Start ====");
		
		ArticleUpdateRequest articleUpdateRequest = new ArticleUpdateRequest();
		
		articleUpdateRequest.setArticleId(3);
		articleUpdateRequest.setTitle("수정합니둥123123");
		articleUpdateRequest.setContent("수정한 내용입니당다리123123123");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(articleUpdateRequest);
		
		System.out.println(content);
		
		String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMTQ5NTIyNSwiZXhwIjoxNzMxNTA0MjI1fQ.6Hugxe1JfNeDUjdMWnuk_WYn8TTRVyjZfJa_Kv2kawkuj6Gjue1aABQOGcqm8hlh";
		
		mockMvc.perform(patch("/api/article/update")
				.header("Authorization", "Bearer " + accessToken)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(content))
	            .andDo(print())
	            .andExpect(status().isOk());
		
        System.out.println("==== UpdateArticle Test End ====");

	}
}
