package com.plane.user.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plane.user.controller.UserController;
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
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private UserRepository userRepository;
	
	@Test
	@Disabled
	void testUserRegister() throws Exception {
		System.out.println("==== Test Start ====");
		
		UserSignupRequest userSignupRequest = new UserSignupRequest();
		userSignupRequest.setUserId("ssafy");
		userSignupRequest.setPassword("ssafy1234!");
		userSignupRequest.setConfirmPassword("ssafy1234!");
		userSignupRequest.setEmail("ssafy@ssafy.com");
		userSignupRequest.setNickName("김싸피");
		userSignupRequest.setPhone("01012345678");
		
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
//	@Disabled
	void testUserProfile() throws Exception {
		System.out.println("==== Profile Test Start ====");
		
		mockMvc.perform(get("/api/users/profile/user001")    
	            .contentType(MediaType.APPLICATION_JSON))
	            .andDo(print())                             
	            .andExpect(status().isOk());
		
        System.out.println("==== Test End ====");

	}
	
}
