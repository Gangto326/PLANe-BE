package com.plane.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.common.exception.custom.DuplicateUserException;
import com.plane.common.exception.custom.InvalidPasswordException;
import com.plane.common.exception.custom.UserNotFoundException;
import com.plane.common.exception.custom.UserUpdateException;
import com.plane.common.exception.custom.VerificationCodeException;
import com.plane.common.util.FormatUtil;
import com.plane.common.util.HashUtil;
import com.plane.user.dto.ChangePasswordRequest;
import com.plane.user.dto.FindIdRequest;
import com.plane.user.dto.UserIdResponse;
import com.plane.user.dto.UserMyPageRequest;
import com.plane.user.dto.UserMyPageResponse;
import com.plane.user.dto.UserProfileResponse;
import com.plane.user.dto.UserSignupRequest;
import com.plane.user.repository.UserRepository;


@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
	private final UserPreferenceService userPreferenceService;
	private final FormatUtil formatUtil;
	private final HashUtil hashUtil;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, UserPreferenceService userPreferenceService, FormatUtil formatUtil, HashUtil hashUtil) {
		this.userRepository = userRepository;
		this.userPreferenceService = userPreferenceService;
		this.formatUtil = formatUtil;
		this.hashUtil = hashUtil;
	}

	
	@Override
	public boolean signup(UserSignupRequest userSignupRequest) {
		
		// 비밀번호 일치 여부 확인
		if (!userSignupRequest.getPassword().equals(userSignupRequest.getConfirmPassword())) {
			throw new InvalidPasswordException("Password != ConfirmPassword");
		}
		
		// 전화번호 중복 검사
        String hashedPhone = hashUtil.hashPhone(userSignupRequest.getPhone());
        
        if (userRepository.selectByPhone(hashedPhone) != null) {
            throw new DuplicateUserException("이미 가입된 번호입니다.");
        }
        
        // Hash
        userSignupRequest.setHashedPassword(hashUtil.hashPassword(userSignupRequest.getPassword()));
        userSignupRequest.setHashedPhone(hashUtil.hashPhone(userSignupRequest.getPhone()));
        
        if (userRepository.saveUser(userSignupRequest) == 1) {
        	return true;
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
	public boolean updateMyPage(UserMyPageRequest userMyPageRequest) {
		
		String userId = userMyPageRequest.getUserId();
		
		// 비트로 변경 확인
		final int USER_UPDATE = 1;
	    final int STYLE_UPDATE = 2;
	    final int THEMA_UPDATE = 4;
		int result = 0;

        if (userPreferenceService.updateTripStyles(userId, userMyPageRequest.getTripStyle())) {
        	result |= STYLE_UPDATE;
        }

        if (userPreferenceService.updateTripThemas(userId, userMyPageRequest.getTripThema())) {
        	result |= THEMA_UPDATE;
        }
        
        if (userRepository.updateUser(userMyPageRequest) > 0 ) {
			result |= USER_UPDATE;
		}
        
        if (result > 0) {
//        	System.out.println(Integer.toBinaryString(result));
        	return true;
        }
        
        throw new UserUpdateException("마이페이지 업데이트 중 오류가 발생했습니다.");
	}


	@Override
	public boolean checkDuplicatedId(String userId) {
		
		formatUtil.isValidUserId(userId);
		
		if (userRepository.findUserById(userId) == 0) {
			return true;
		}
		
		throw new DuplicateUserException("이미 가입된 아이디입니다.");
	}


	@Override
	public boolean changePassword(ChangePasswordRequest changePasswordRequest) {
		
		// 아이디 존재 여부 확인 --> login 로직 사용 (id, password && password != newPassword 모두 확인 필요)
		if (!userRepository.existsById(changePasswordRequest.getUserId())) {
			throw new UserNotFoundException("해당 ID를 사용하는 회원이 없습니다.");
		}
		
		// 비밀번호 일치 여부 확인
		if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
			throw new InvalidPasswordException("Password != ConfirmPassword");
		}
		
		// Hash
		changePasswordRequest.setHashedPassword(hashUtil.hashPassword(changePasswordRequest.getNewPassword()));
		
		if (userRepository.updateUserPassword(changePasswordRequest.getUserId(), changePasswordRequest.getHashedPassword()) >= 0) {
			return true;
		}
		
		throw new UserUpdateException("비밀번호 변경 실패");
	}


	@Override
	public UserIdResponse findId(FindIdRequest findIdRequest) {
		
		// 인증번호 확인
		if (!userRepository.existsCodeByEmail(findIdRequest)) {
			throw new VerificationCodeException("인증코드가 일치하지 않거나 유효기간이 만료된 코드입니다.");
		}
		
		UserIdResponse userIdResponse = new UserIdResponse();
		userIdResponse.setIdList(userRepository.selectIdByEmail(findIdRequest));
		
		return userIdResponse;
	}
	
}
