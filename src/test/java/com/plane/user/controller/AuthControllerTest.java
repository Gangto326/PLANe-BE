package com.plane.user.controller;


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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;

import com.plane.article.repository.ArticleRepository;
import com.plane.article.service.ArticleService;
import com.plane.user.dto.UserLoginRequest;
import com.plane.user.service.AuthService;

import jakarta.servlet.http.Cookie;

@AutoConfigureMockMvc
@SpringBootTest(
		properties = { 
			"spring.config.location=classpath:application.properties" 
		}
	)
//@Transactional
public class AuthControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean  // 가짜 객체 주입
	private AuthService authService;
	
	
	@Test
	public void testRefreshToken() throws Exception {
	   // Given
	   String refreshToken = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOiJrYW5nc2Fuc2FtMTIzIiwicm9sZSI6Iu2ajOybkCIsImlhdCI6MTczMjE5OTk2OSwiZXhwIjoxNzMyMjIxNTY5fQ.Wl3hT0cbU8vFBLSXJSUukvJDoj7mPelrMOANPFUfTLlFdcx73dQAJBhTP8zMD1CRBnF64jsbGc5xpfnwAyqjLQ";

	   // When & Then
	   mockMvc.perform(get("/api/auth/refresh")
	           .cookie(new Cookie("refresh_token", refreshToken)))
	           .andExpect(status().isOk())
	           .andExpect(jsonPath("$.success").value(true))
	           .andExpect(jsonPath("$.message").value("Token 발급 성공"))
	           .andDo(print());  // 응답 결과를 콘솔에 출력
	}
}
