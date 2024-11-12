package com.plane.user.service;

import com.plane.user.dto.AuthResponse;
import com.plane.user.dto.TokenDto;
import com.plane.user.dto.UserLoginRequest;

public interface AuthService {

	AuthResponse login(UserLoginRequest userLoginRequest);

	void logout(String accessToken);

	TokenDto reGenerateToken(String refreshToken);

	boolean isValidToken(String token);
	
}
