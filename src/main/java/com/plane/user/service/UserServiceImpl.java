package com.plane.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.common.exception.custom.DuplicateUserException;
import com.plane.common.exception.custom.InvalidPasswordException;
import com.plane.common.exception.custom.UserNotFoundException;
import com.plane.common.exception.custom.UserUpdateException;
import com.plane.common.util.HashUtil;
import com.plane.user.dto.UserLoginResponse;
import com.plane.user.dto.UserMyPageRequest;
import com.plane.user.dto.UserMyPageResponse;
import com.plane.user.dto.UserProfileResponse;
import com.plane.user.dto.UserSignupRequest;
import com.plane.user.repository.UserRepository;


@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	private final HashUtil hashUtil;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, HashUtil hashUtil) {
		this.userRepository = userRepository;
		this.hashUtil = hashUtil;
	}
	
	
	@Override
	@Transactional
	public UserLoginResponse signup(UserSignupRequest userSignupRequest) {
		
		// 비밀번호 일치 여부 확인
		if (!userSignupRequest.getPassword().equals(userSignupRequest.getConfirmPassword())) {
			throw new InvalidPasswordException("Password != ConfirmPassword");
		}
		
		// 전화번호 중복 검사
        String hashedPhone = hashUtil.hashPhone(userSignupRequest.getPhone());
        
        if (userRepository.selectByPhone(hashedPhone) != null) {
            throw new DuplicateUserException("이미 가입된 번호입니다.");
        }
        
        // 엔티티 생성 및 저장
        userSignupRequest.setHashedPassword(hashUtil.hashPassword(userSignupRequest.getPassword()));
        userSignupRequest.setHashedPhone(hashUtil.hashPhone(userSignupRequest.getPhone()));
        
        UserLoginResponse userLoginResponse = null;
        
        if (userRepository.save(userSignupRequest) == 1) {
        	userLoginResponse = new UserLoginResponse(userSignupRequest.getUserId());
        	return userLoginResponse;
        }
        
		throw new UserNotFoundException("회원가입 실패.");
	}


	@Override
	public UserProfileResponse getProfile(String userId) {
		
		UserProfileResponse userProfileResponse = userRepository.selectUserProfile(userId);
		
		if (userProfileResponse == null) {
			throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
		}
		
		return userProfileResponse;
	}


	@Override
	public UserMyPageResponse getMyPage(String userId) {
		
		UserMyPageResponse userMyPageResponse = userRepository.selectUserMyPage(userId);
		
		if (userMyPageResponse == null) {
			throw new UserNotFoundException("사용자를 찾을 수 없습니다.");
		}
		
		return userMyPageResponse;
	}


	@Override
	public UserMyPageResponse updateMyPage(UserMyPageRequest userMyPageRequest) {
		
		String userId = userMyPageRequest.getUserId();
		
		// 비트로 변경 확인
		final int USER_UPDATE = 1;
	    final int STYLE_UPDATE = 2;
	    final int THEMA_UPDATE = 4;
		int result = 0;

        // TripStyle 업데이트
        if (updateTripStyles(userId, userMyPageRequest.getTripStyle())) {
        	result |= STYLE_UPDATE;
        }

        // TripThema 업데이트
        if (updateTripThemas(userId, userMyPageRequest.getTripThema())) {
        	result |= THEMA_UPDATE;
        }
        
        if (userRepository.updateUser(userMyPageRequest) > 0 ) {
			result |= USER_UPDATE;
		}
        
        if (result > 0) {
        	System.out.println(Integer.toBinaryString(result));
        	
        	return userRepository.selectUserMyPage(userId);
        }
        
        throw new UserUpdateException("마이페이지 업데이트 중 오류가 발생했습니다.");
	}
	
	
	private boolean updateTripStyles(String userId, List<Integer> tripStyle) {
		
        if (tripStyle == null) {
            return false;
        }
        
        userRepository.deleteTripStyle(userId);
        
        if (!tripStyle.isEmpty()) {
            userRepository.insertTripStyle(userId, tripStyle);
        }
        
        return true;
    }

	
	private boolean updateTripThemas(String userId, List<Integer> tripThema) {
		
        if (tripThema == null) {
            return false;
        }
        
        userRepository.deleteTripThema(userId);
        
        if (!tripThema.isEmpty()) {
            userRepository.insertTripThema(userId, tripThema);
        }
        
        return true;
    }


	@Override
	public boolean checkDuplicatedId(String userId) {
		
		if (userRepository.findUserById(userId) == 0) {
			return true;
		}
		
		throw new DuplicateUserException("이미 가입된 아이디입니다.");
	}
	
}
