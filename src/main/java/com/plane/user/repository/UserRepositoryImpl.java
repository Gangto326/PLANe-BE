package com.plane.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
	public boolean existsCodeByEmail(String email, String verificationCode) {
		
		return userMapper.selectCodeByEmail(email, verificationCode).isPresent();
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

	@Override
	public int updateVerificationCodeDelete(String email) {

		return userMapper.updateVerificationCodeDelete(email);
	}

	@Override
	public int deleteVerificationCodes() {

		return userMapper.deleteVerificationCodes();
	}

	@Override
	public int insertAuthenticationfile(String userId, String authenticationfileUrl, String originalFilename) {
		
		return userMapper.insertAuthenticationfile(userId, authenticationfileUrl, originalFilename);
	}

	@Override
	public boolean existsAuthenticationfileByUserId(String userId) {

		return userMapper.existsAuthenticationfileByUserId(userId);
	}

	
}
