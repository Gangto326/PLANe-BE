package com.plane.user.repository;

import com.plane.user.domain.User;
import com.plane.user.dto.TokenDto;
import com.plane.user.dto.UserLoginRequest;

public interface AuthRepository {

	int saveToken(TokenDto tokenDto);

	User selectUser(UserLoginRequest userLoginRequest);

	boolean isTokenActive(String token);

	int deleteExpiredTokens(long currentTime);

	void setTokenInvalid(String userId);

	void setTokenInvalid(String userId, String refreshToken);

	User selectUser(String userId);
	
}
