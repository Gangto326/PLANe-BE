package com.plane.article.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
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
		
		String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMTkxNTgxOCwiZXhwIjoxNzMxOTUxODE4fQ._ZGcmnQWIss-0ZvmOAH85tjX0yEC66f8Ljkgtt-sjbNNf6xJIIFGGGotzYvhxiMM";
		
		mockMvc.perform(get("/api/article/{articleId}", 10)
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
		
		articleUpdateRequest.setArticleId(3L);
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
		
		String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMTgyMzY3MywiZXhwIjoxNzMxODU5NjczfQ.nHrdDakYia73Xv8MVHsNrT0jzjDNtu4uSKNXbdKbMAXDFmzK78kZwfqxvgmLM5IS";
		
		mockMvc.perform(get("/api/article/search")
				.header("Authorization", "Bearer " + accessToken)
				.param("page", "2")
                .param("size", "12")
                .param("sortBy", "createdDate")
                .param("sortDirection", "DESC")
				.param("articleType", "동행")
				.param("recommend", String.valueOf(false)))
				.andDo(print())                             
		        .andExpect(status().isOk());
		
        System.out.println("==== ArticleSearch Test End ====");

	}
	
	
	@Test
	@DisplayName("게시글 삭제")
	@Disabled
	void testArticleDelete() throws Exception {
		System.out.println("==== ArticleDelete Test Start ====");
		
		String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMTgyMzY3MywiZXhwIjoxNzMxODU5NjczfQ.nHrdDakYia73Xv8MVHsNrT0jzjDNtu4uSKNXbdKbMAXDFmzK78kZwfqxvgmLM5IS";
		
		mockMvc.perform(delete("/api/article/delete")
				.contentType(MediaType.APPLICATION_JSON)
				.content(String.valueOf(19))
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
		articleInteractionRequset.setArticleId(8L);
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
		
		String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMTk5MzYzMSwiZXhwIjoxNzMyMDI5NjMxfQ.wVC_3bxlVyjgSYYGh1RKBBGnL2KbVsCJPvo3kir14LGh27_Vo1YerjXyXQgHkHTo";
		
		
		ArticleReportRequest articleReportRequest = new ArticleReportRequest();
		articleReportRequest.setArticleId(4L);
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
	
	@Test
	@DisplayName("게시글 생성하기")
	@Disabled
	void articleCreateTest() throws Exception {
		
		String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMjI1ODQ1MiwiZXhwIjoxNzMyMjk0NDUyfQ.b78-e2OCJv6XC585Q5BR2_XgYfwBJH_hmF34e9mtzgXywdQXSrpOxraYlSnYtR9-";
		
		
	    mockMvc.perform(multipart("/api/article/create")
	            .param("tripId", "2")
	            .param("articleType", "동행")  // "동행" 또는 "후기"
	            .param("title", "테스트 제목")
	            .param("content", "테스트 내용")
	            // articlePictureUrl은 선택적이므로 생략
	            // file은 null이므로 생략
	            .header("Authorization", "Bearer " + accessToken)
	            .contentType(MediaType.MULTIPART_FORM_DATA))
	            .andDo(print())
	            .andExpect(status().isOk());
	}
	
}
