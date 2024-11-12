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
	public User selectUser(UserLoginRequest userLoginRequest) {
		
		return authMapper.selectUser(userLoginRequest);
	}

}
