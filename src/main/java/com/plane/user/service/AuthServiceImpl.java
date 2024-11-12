package com.plane.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.common.exception.custom.UserNotFoundException;
import com.plane.common.util.HashUtil;
import com.plane.common.util.JwtUtil;
import com.plane.user.domain.User;
import com.plane.user.dto.AuthDto;
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
	public AuthDto login(UserLoginRequest userLoginRequest) {
		
		// Hash
		userLoginRequest.setHashedPassword(hashUtil.hashPassword(userLoginRequest.getPassword()));

		User user = authRepository.selectUser(userLoginRequest);
		
		if(user == null) {
			throw new UserNotFoundException("일치하는 사용자가 없습니다.");
		}
		
		// 2. AccessToken과 RefreshToken을 발급.
		TokenDto accessToken = jwtUtil.generateToken(user, "AccessToken");
		TokenDto refreshToken = jwtUtil.generateToken(user, "RefreshToken");
		
		// 3. 발급한 토큰을 token_status 테이블에 저장.
		authRepository.saveToken(accessToken);
		authRepository.saveToken(refreshToken);
		
		// 4. AuthDto 설정.
		AuthDto authDto = new AuthDto();
		authDto.setAccessToken(accessToken.getToken());
		authDto.setRefreshToken(refreshToken.getToken());
		authDto.setMaxAge(refreshToken.getExpiresAt());
		
		return authDto;
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
