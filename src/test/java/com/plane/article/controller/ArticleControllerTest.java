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
	
}
