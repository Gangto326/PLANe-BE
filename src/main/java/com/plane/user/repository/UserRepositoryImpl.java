package com.plane.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.plane.user.domain.User;
import com.plane.user.dto.FindIdRequest;
import com.plane.user.dto.UserIdResponse;
import com.plane.user.dto.UserMyPageRequest;
import com.plane.user.dto.UserMyPageResponse;
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
	public int saveUser(UserSignupRequest userSignupRequest) {
		
		return userMapper.insertUser(userSignupRequest);
	}

	@Override
	public UserProfileResponse selectUserProfile(String userId) {
		
		UserProfileResponse asd = userMapper.selectUserProfile(userId);
		
		return userMapper.selectUserProfile(userId);
	}

	@Override
	public UserMyPageResponse selectUserMyPage(String userId) {
		
		return userMapper.selectUserMyPage(userId);
	}

	@Override
	public int updateUser(String userId, UserMyPageRequest userMyPageRequest) {
		
		return userMapper.updateUser(userId, userMyPageRequest);
	}

	@Override
	public int deleteTripStyle(String userId) {
		
		return userMapper.deleteTripStyle(userId);
	}

	@Override
	public int deleteTripThema(String userId) {
		
		return userMapper.deleteTripThema(userId);
	}

	@Override
	public int insertTripStyle(String userId, List<Integer> tripStyle) {
		
		return userMapper.insertTripStyle(userId, tripStyle);
	}

	@Override
	public int insertTripThema(String userId, List<Integer> tripThema) {
		
		return userMapper.insertTripThema(userId, tripThema);
	}

	@Override
	public int findUserById(String userId) {
		
		return userMapper.findUserById(userId);
	}

	@Override
	public int updateUserPassword(String userId, String newPassword) {
		
		return userMapper.updateUserPassword(userId, newPassword);
	}

	@Override
	public boolean existsById(String userId) {
		
		return userMapper.selectUserIdById(userId).isPresent();
	}

	@Override
	public boolean existsByEmail(String email) {
		
		return !userMapper.selectUserIdByEmail(email).isEmpty();
	}

	@Override
	public int insertVerificationCode(String email, String verificationCode) {
		
		return userMapper.insertVerificationCode(email, verificationCode);
	}

	@Override
	public boolean existsCodeByEmail(FindIdRequest findIdRequest) {
		
		return userMapper.selectCodeByEmail(findIdRequest).isPresent();
	}

	@Override
	public List<String> selectIdByEmail(FindIdRequest findIdRequest) {
		
		return userMapper.selectUserIdByEmail(findIdRequest.getEmail());
	}

	@Override
	public String selectUserNicknameByUserId(String userId) {

		return userMapper.selectUserNicknameByUserId(userId);
	}

	@Override
	public User selectUserByUserId(String userId) {

		return userMapper.selectUserByUserId(userId);
	}
	
}
