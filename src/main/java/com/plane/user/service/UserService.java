package com.plane.user.service;

import com.plane.user.dto.ChangePasswordRequest;
import com.plane.user.dto.FindIdRequest;
import com.plane.user.dto.UserIdResponse;
import com.plane.user.dto.UserMyPageRequest;
import com.plane.user.dto.UserMyPageResponse;
import com.plane.user.dto.UserProfileResponse;
import com.plane.user.dto.UserSignupRequest;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;


public interface UserService {
	
	boolean signup(UserSignupRequest userSignupRequest);

	UserProfileResponse getProfile(String userId);

	UserMyPageResponse getMyPage(String userId);
	
	boolean updateMyPage(String userId, UserMyPageRequest userMyPageRequest);

	boolean checkDuplicatedId(String userId);

	boolean changePassword(String userId, ChangePasswordRequest changePasswordRequest);

	UserIdResponse findId(FindIdRequest findIdRequest);

	boolean uploadAuthenticationfile(String userId, MultipartFile file);
	
}
