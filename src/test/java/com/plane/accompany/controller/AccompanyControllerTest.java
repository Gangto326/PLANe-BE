package com.plane.accompany.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plane.accompany.dto.AccompanyDetailRequest;
import com.plane.accompany.dto.AccompanyRegistRequest;
import com.plane.accompany.repository.AccompanyRepository;
import com.plane.accompany.service.AccompanyService;

@AutoConfigureMockMvc
@SpringBootTest(
		properties = { 
			"spring.config.location=classpath:application.properties" 
		}
	)
public class AccompanyControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private AccompanyService accompanyService;
	
	@Autowired
    private AccompanyRepository accompanyRepository;
	
	
	@Test
	@DisplayName("동행 신청하기")
	@Disabled
	void testAccompanyRegist() throws Exception {
		System.out.println("==== AccompanyRegist Test Start ====");
		
		String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMTc3MjgyNywiZXhwIjoxNzMxODA4ODI3fQ.aQoG8BskY-yPM7Ksk1qeT5A69YEXJcS15L_-Plv7EpLePeOjodRMsI9R8bMOfifQ";
		
		AccompanyRegistRequest accompanyRegistRequest = new AccompanyRegistRequest();
		accompanyRegistRequest.setArticleId(1L);
		
		List<AccompanyDetailRequest> details = new ArrayList<>();
		
		AccompanyDetailRequest detail1 = new AccompanyDetailRequest();
		detail1.setAskId(1);
		detail1.setAnswer("1번 답변");
		details.add(detail1);
		
		AccompanyDetailRequest detail2 = new AccompanyDetailRequest();
		detail2.setAskId(2);
		detail2.setAnswer("2번 답변");
		details.add(detail2);
		
		AccompanyDetailRequest detail3 = new AccompanyDetailRequest();
		detail3.setAskId(3);
		detail3.setAnswer("3번 답변");
		details.add(detail3);
		
		accompanyRegistRequest.setAccompanyDetailList(details);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(accompanyRegistRequest);
		
		mockMvc.perform(post("/api/accompany/regist")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + accessToken)
				.content(content))
		        .andDo(print())
		        .andExpect(status().isOk());
		
        System.out.println("==== AccompanyRegist Test End ====");

	}
	
	@Test
	@DisplayName("동행 가져오기")
//	@Disabled
	void testAccompanyList() throws Exception {
		System.out.println("==== AccompanyList Test Start ====");
		
		String accessToken = "eyJhbGciOiJIUzM4NCJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMTc3MjgyNywiZXhwIjoxNzMxODA4ODI3fQ.aQoG8BskY-yPM7Ksk1qeT5A69YEXJcS15L_-Plv7EpLePeOjodRMsI9R8bMOfifQ";
		
		mockMvc.perform(get("/api/accompany/list?type=RECEIVED")
				.header("Authorization", "Bearer " + accessToken)
	            .contentType(MediaType.APPLICATION_JSON))
	            .andDo(print())
	            .andExpect(status().isOk());
		
        System.out.println("==== AccompanyList Test End ====");

	}
	
}
