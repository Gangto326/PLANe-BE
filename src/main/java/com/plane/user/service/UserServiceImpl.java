package com.plane.user.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.common.exception.custom.DuplicateUserException;
import com.plane.common.exception.custom.InvalidParameterException;
import com.plane.common.exception.custom.InvalidPasswordException;
import com.plane.common.exception.custom.UserNotFoundException;
import com.plane.common.exception.custom.UserUpdateException;
import com.plane.common.exception.custom.VerificationCodeException;
import com.plane.common.util.HashUtil;
import com.plane.user.dto.ChangePasswordRequest;
import com.plane.user.dto.FindIdRequest;
import com.plane.user.dto.UserIdResponse;
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
        
        // Hash
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

        if (updateTripStyles(userId, userMyPageRequest.getTripStyle())) {
        	result |= STYLE_UPDATE;
        }

        if (updateTripThemas(userId, userMyPageRequest.getTripThema())) {
        	result |= THEMA_UPDATE;
        }
        
        if (userRepository.updateUser(userMyPageRequest) > 0 ) {
			result |= USER_UPDATE;
		}
        
        if (result > 0) {
//        	System.out.println(Integer.toBinaryString(result));
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
		
		validateUserId(userId);
		
		if (userRepository.findUserById(userId) == 0) {
			return true;
		}
		
		throw new DuplicateUserException("이미 가입된 아이디입니다.");
	}
	
	
	private void validateUserId(String userId) {
		
		final int MIN_LENGTH = 4;
	    final int MAX_LENGTH = 20;
	    final Pattern ID_PATTERN = Pattern.compile("^[a-zA-Z0-9]*$");
		
		if (userId == null || userId.isBlank()) {
	        throw new InvalidParameterException("아이디는 필수입니다.");
	    }
	
	    if (userId.length() < MIN_LENGTH || userId.length() > MAX_LENGTH) {
	        throw new InvalidParameterException(
	            String.format("아이디는 %d~%d자 사이여야 합니다.", MIN_LENGTH, MAX_LENGTH)
	        );
	    }
	
	    if (!ID_PATTERN.matcher(userId).matches()) {
	        throw new InvalidParameterException("아이디는 영문과 숫자만 가능합니다.");
	    }
	    
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
