package com.plane.user.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.plane.user.domain.User;
import com.plane.user.dto.UserLoginResponse;
import com.plane.user.dto.UserProfileResponse;
import com.plane.user.dto.UserSignupRequest;
import com.plane.user.mapper.UserMapper;

@Repository
public class UserRepositoryImpl implements UserRepository{
	
	private final UserMapper userMapper;
	
	@Autowired
	public UserRepositoryImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public User selectByPhone(String hashedPhone) {
		
		return userMapper.selectByPhone(hashedPhone);
	}

	@Override
	public int save(UserSignupRequest userSignupRequest) {
		
		return userMapper.insertUser(userSignupRequest);
	}

	@Override
	public UserProfileResponse selectUserProfile(String userId) {
		
		return userMapper.selectUserProfile(userId);
	}
	
}
