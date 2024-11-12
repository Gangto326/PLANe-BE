package com.plane.user.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plane.common.util.FormatUtil;
import com.plane.user.controller.UserController;
import com.plane.user.dto.ChangePasswordRequest;
import com.plane.user.dto.EmailVerificationRequest;
import com.plane.user.dto.FindIdRequest;
import com.plane.user.dto.FindPasswordRequest;
import com.plane.user.dto.UserMyPageRequest;
import com.plane.user.dto.UserSignupRequest;
import com.plane.user.repository.UserRepository;
import com.plane.user.service.UserService;

@AutoConfigureMockMvc
@SpringBootTest(
		properties = { 
			"spring.config.location=classpath:application.properties" 
		}
	)
//@Transactional
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private FormatUtil formatUtil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private UserRepository userRepository;
	
	@Test
	@DisplayName("회원 가입하기")
//	@Disabled
	void testUserRegister() throws Exception {
		System.out.println("==== Test Start ====");
		
		UserSignupRequest userSignupRequest = new UserSignupRequest();
		userSignupRequest.setUserId("kangsansam123");
		userSignupRequest.setPassword("qweqwe1!");
		userSignupRequest.setConfirmPassword("qweqwe1!");
		userSignupRequest.setEmail("kangsansam123@naver.com");
		userSignupRequest.setNickName("김강토");
		userSignupRequest.setPhone("01012349999");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(userSignupRequest);
		
		System.out.println(content);
		
		mockMvc.perform(post("/api/users/signup")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(content))
	            .andDo(print())
	            .andExpect(status().isOk());
		
        System.out.println("==== Test End ====");

	}
	
	@Test
	@DisplayName("유저 프로필 확인하기")
	@Disabled
	void testUserProfile() throws Exception {
		System.out.println("==== Profile Test Start ====");
		
		mockMvc.perform(post("/api/users/profile")    
	            .contentType(MediaType.APPLICATION_JSON)
				.content("user001"))
	            .andDo(print())                             
	            .andExpect(status().isOk());
		
        System.out.println("==== Test End ====");

	}
	
	@Test
	@DisplayName("마이페이지 확인하기")
	@Disabled
	void testUserMyPage() throws Exception {
		System.out.println("==== MyPage Test Start ====");
		
		mockMvc.perform(post("/api/users/myPage")    
	            .contentType(MediaType.APPLICATION_JSON)
	            .content("jjuj99"))
	            .andDo(print())                             
	            .andExpect(status().isOk());
		
        System.out.println("==== Test End ====");

	}
	
	
	@Test
	@DisplayName("마이페이지 수정하기")
	@Disabled
	void testUpdateMyPage() throws Exception {
		System.out.println("==== UpdateMyPage Test Start ====");
		List<Integer> tripStyle = new ArrayList<>();
		List<Integer> tripThema = new ArrayList<>();
		
		tripStyle.add(2);
		tripStyle.add(3);
		
		tripThema.add(1);
		tripThema.add(3);
		
		UserMyPageRequest userMyPageRequest = new UserMyPageRequest();
		userMyPageRequest.setUserId("jjuj99");
		userMyPageRequest.setNickName("유진유진유진");
		userMyPageRequest.setProfileUrl("https://example.com/profile001.jpg");
		userMyPageRequest.setIntroduce("수정수정수정수정");
		userMyPageRequest.setPublic(true);
		userMyPageRequest.setTripStyle(tripStyle);
		userMyPageRequest.setTripThema(tripThema);
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(userMyPageRequest);
		
		System.out.println(content);
		
		mockMvc.perform(patch("/api/users/myPage")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(content))
	            .andDo(print())
	            .andExpect(status().isOk());
		
        System.out.println("==== Test End ====");

	}
	
	
	@Test
	@DisplayName("아이디 중복 확인하기 & 유효성 검사")
	@Disabled
	void testCheckId() throws Exception {
		System.out.println("==== CheckId Test Start ====");
		
		mockMvc.perform(get("/api/users/checkId/user105")    
	            .contentType(MediaType.APPLICATION_JSON))
	            .andDo(print())                             
	            .andExpect(status().isOk());
		
        System.out.println("==== Test End ====");

	}
	
	@Test
	@DisplayName("비밀번호 찾기")
	@Disabled
	void testFindPassword() throws Exception {
		System.out.println("==== Test Start ====");
		
		FindPasswordRequest findPasswordRequest = new FindPasswordRequest();
		
		findPasswordRequest.setUserId("kangsansam");
		findPasswordRequest.setEmail("kangsansam@naver.com");
		findPasswordRequest.setPhone("01012341234");
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(findPasswordRequest);
		
		System.out.println(content);
		
		mockMvc.perform(post("/api/users/find/password")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(content))
	            .andDo(print())
	            .andExpect(status().isOk());
		
        System.out.println("==== Test End ====");

	}
	
	
	@Test
	@DisplayName("비밀번호 변경하기")
	@Disabled
	void testChangePassword() throws Exception {
		System.out.println("==== ChangePassword Test Start ====");
		
		ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
		
		changePasswordRequest.setUserId("kangsansam");
		changePasswordRequest.setPassword("ssafy1234!");
		changePasswordRequest.setNewPassword("ssafy1234!!");
		changePasswordRequest.setConfirmPassword("ssafy1234!!");
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(changePasswordRequest);
		
		System.out.println(content);
		
		mockMvc.perform(patch("/api/users/changePassword")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(content))
	            .andDo(print())
	            .andExpect(status().isOk());
		
        System.out.println("==== ChangePassword Test End ====");

	}
	
	
	@Test
	@DisplayName("인증번호 발송")
	@Disabled
	void testVerificationCode() throws Exception {
		System.out.println("==== VerificationCode Test Start ====");
		
		EmailVerificationRequest emailVerificationRequest = new EmailVerificationRequest();
		
		emailVerificationRequest.setEmail("kangsansam@naver.com");
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(emailVerificationRequest);
		
		System.out.println(content);
		
		mockMvc.perform(post("/api/users/verification")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(content))
	            .andDo(print())
	            .andExpect(status().isOk());
		
        System.out.println("==== VerificationCode Test End ====");

	}
	
	@Test
	@DisplayName("아이디 찾기")
	@Disabled
	void testFindId() throws Exception {
		System.out.println("==== FindId Test Start ====");
		
		FindIdRequest findIdRequest = new FindIdRequest();
		
		findIdRequest.setEmail("kangsansam@naver.com");
		findIdRequest.setVerificationCode("$H939t%Fxa");
		
		
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(findIdRequest);
		
		System.out.println(content);
		
		mockMvc.perform(post("/api/users//find/id")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(content))
	            .andDo(print())
	            .andExpect(status().isOk());
		
        System.out.println("==== FindId Test End ====");

	}
	
}
