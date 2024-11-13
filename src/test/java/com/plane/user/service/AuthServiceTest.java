package com.plane.user.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.plane.user.dto.AuthResponse;
import com.plane.user.dto.UserLoginRequest;

@SpringBootTest
class AuthServiceTest {
    
    @Autowired
    private AuthService authService;
    
    @Test
    @DisplayName("실제 JWT 토큰 생성 테스트")
    void generateJwtTokenTest() {
    	
        UserLoginRequest loginRequest = new UserLoginRequest("kangsansam123", "qweqwe1!");

        AuthResponse response = authService.login(loginRequest);

        assertNotNull(response.getAccessToken());
        assertTrue(response.getAccessToken().contains(".")); // JWT는 점(.)으로 구분된 형식
        assertNotNull(response.getRefreshToken());
        assertTrue(response.getMaxAge() > 0);
    }
    
}