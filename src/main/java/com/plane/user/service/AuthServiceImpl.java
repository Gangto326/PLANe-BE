package com.plane.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.common.exception.custom.UserNotFoundException;
import com.plane.common.util.HashUtil;
import com.plane.common.util.JwtUtil;
import com.plane.user.domain.User;
import com.plane.user.dto.AuthResponse;
import com.plane.user.dto.TokenDto;
import com.plane.user.dto.UserLoginRequest;
import com.plane.user.repository.AuthRepository;


@Service
@Transactional
public class AuthServiceImpl implements AuthService {
	
	private final AuthRepository authRepository;
	private final JwtUtil jwtUtil;
	private final HashUtil hashUtil;
	
	@Autowired
	public AuthServiceImpl(AuthRepository authRepository, JwtUtil jwtUtil, HashUtil hashUtil) {
		this.authRepository = authRepository;
		this.jwtUtil = jwtUtil;
		this.hashUtil = hashUtil;
	}

	
	@Override
	public AuthResponse login(UserLoginRequest userLoginRequest) {
		
		// Hash
		userLoginRequest.setHashedPassword(hashUtil.hashPassword(userLoginRequest.getPassword()));
		
		User user = authRepository.selectUser(userLoginRequest);
		
		if(user == null) {
			throw new UserNotFoundException("일치하는 사용자가 없습니다.");
		}
		
		TokenDto accessToken = jwtUtil.generateToken(user, "AccessToken");
		TokenDto refreshToken = jwtUtil.generateToken(user, "RefreshToken");
		
		authRepository.saveToken(accessToken);
		authRepository.saveToken(refreshToken);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setAccessToken(accessToken.getTokenValue());
		authResponse.setRefreshToken(refreshToken.getTokenValue());
		authResponse.setMaxAge(refreshToken.getExpiresAt());
		
		return authResponse;
	}

	
	@Override
	public void logout(String accessToken) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public TokenDto reGenerateToken(String refreshToken) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public boolean isValidToken(String token) {
		// TODO Auto-generated method stub
		return false;
	}

}
