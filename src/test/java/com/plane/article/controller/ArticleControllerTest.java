package com.plane.article.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import com.plane.article.dto.ArticleInteractionRequset;
import com.plane.article.dto.ArticleReportRequest;
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
		
		String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMTU1MTI1OSwiZXhwIjoxNzMxNTg3MjU5fQ.BK-budRN9x_m-G-VJX3BY1aHfSSFWl_Kwg7ovR_T67UO620PPEweo8_qlmUgcexv";
		
		mockMvc.perform(get("/api/article/{articleId}", 1)
				.header("Authorization", "Bearer " + accessToken)
	            .contentType(MediaType.APPLICATION_JSON))
	            .andDo(print())                             
	            .andExpect(status().isOk());
		
        System.out.println("==== ArticleDetail Test End ====");

	}
	
	
	@Test
	@DisplayName("게시글 수정하기")
	@Disabled
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
	
	
	@Test
	@DisplayName("게시글 목록 불러오기")
	@Disabled
	void testArticleSearch() throws Exception {
		System.out.println("==== ArticleSearch Test Start ====");
		
		String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMTU1MTI1OSwiZXhwIjoxNzMxNTg3MjU5fQ.BK-budRN9x_m-G-VJX3BY1aHfSSFWl_Kwg7ovR_T67UO620PPEweo8_qlmUgcexv";
		
		mockMvc.perform(get("/api/article/search")
				.header("Authorization", "Bearer " + accessToken)
				.param("page", "1")
                .param("size", "12")
                .param("sortBy", "createdDate")
                .param("sortDirection", "DESC")
				.param("articleType", "동행")
				.param("recommend", String.valueOf(true)))
				.andDo(print())                             
		        .andExpect(status().isOk());
		
        System.out.println("==== ArticleSearch Test End ====");

	}
	
	
	@Test
	@DisplayName("게시글 삭제")
	@Disabled
	void testArticleDelete() throws Exception {
		System.out.println("==== ArticleDelete Test Start ====");
		
		String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMTU1MTI1OSwiZXhwIjoxNzMxNTg3MjU5fQ.BK-budRN9x_m-G-VJX3BY1aHfSSFWl_Kwg7ovR_T67UO620PPEweo8_qlmUgcexv";
		
		mockMvc.perform(delete("/api/article/delete")
				.contentType(MediaType.APPLICATION_JSON)
				.content(String.valueOf(6))
				.header("Authorization", "Bearer " + accessToken))
				.andDo(print())
		        .andExpect(status().isOk());
		
        System.out.println("==== ArticleDelete Test End ====");

	}
	
	
	@Test
	@DisplayName("좋아요/보관하기 상호작용")
	@Disabled
	void testArticleInteraction() throws Exception {
		System.out.println("==== ArticleInteraction Test Start ====");
		
		String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMTU1MTI1OSwiZXhwIjoxNzMxNTg3MjU5fQ.BK-budRN9x_m-G-VJX3BY1aHfSSFWl_Kwg7ovR_T67UO620PPEweo8_qlmUgcexv";
		
		
		ArticleInteractionRequset articleInteractionRequset = new ArticleInteractionRequset();
		articleInteractionRequset.setArticleId(8);
		articleInteractionRequset.setInteraction("RECOMMAND");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(articleInteractionRequset);
		
		mockMvc.perform(post("/api/article/interaction")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + accessToken)
				.content(content))
		        .andDo(print())
		        .andExpect(status().isOk());
		
        System.out.println("==== ArticleInteraction Test End ====");

	}
	
	@Test
	@DisplayName("게시글 신고하기")
	@Disabled
	void testArticleReport() throws Exception {
		System.out.println("==== ArticleReport Test Start ====");
		
		String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMTY1NDUyOSwiZXhwIjoxNzMxNjkwNTI5fQ.v8AfQ_sjUddlRCNbzRcdt9F0mfM07FAU-uLClPvFiF67DQR8SgINeaFGC23l9rCH";
		
		
		ArticleReportRequest articleReportRequest = new ArticleReportRequest();
		articleReportRequest.setArticleId(10);
		articleReportRequest.setReportId(1);
		articleReportRequest.setDetails("내 글을 내가 신고한다.");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(articleReportRequest);
		
		mockMvc.perform(post("/api/article/report")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + accessToken)
				.content(content))
		        .andDo(print())
		        .andExpect(status().isOk());
		
        System.out.println("==== ArticleReport Test End ====");

	}
	
}
