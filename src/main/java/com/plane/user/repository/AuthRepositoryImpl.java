package com.plane.user.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.plane.user.domain.User;
import com.plane.user.dto.TokenDto;
import com.plane.user.dto.UserLoginRequest;
import com.plane.user.mapper.AuthMapper;

@Repository
public class AuthRepositoryImpl implements AuthRepository {

	private final AuthMapper authMapper;
	
	@Autowired
	public AuthRepositoryImpl(AuthMapper authMapper) {
		this.authMapper = authMapper;
	}

	
	@Override
	public int saveToken(TokenDto tokenDto) {
		
		return authMapper.insertToken(tokenDto);
	}
	
	
	@Override
	public User selectUser(String userId) {
		
		return authMapper.selectUserById(userId);
	}

	
	@Override
	public User selectUser(UserLoginRequest userLoginRequest) {
		
		return authMapper.selectUser(userLoginRequest);
	}
	

	@Override
	public boolean isTokenActive(String token) {
		
		return authMapper.isTokenActive(token);
	}


	@Override
	public int deleteExpiredTokens(long currentTime) {
		
		return authMapper.deleteExpiredTokens(currentTime);
	}


	@Override
	public void setTokenInvalid(String userId) {

		authMapper.updateTokenActiveById(userId);
	}


	@Override
	public void setTokenInvalid(String userId, String refreshToken) {
		
		authMapper.updateTokenActiveByIdAndToken(userId, refreshToken);
	}

	
}
