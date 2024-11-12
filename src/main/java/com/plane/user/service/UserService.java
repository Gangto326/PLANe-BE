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


public interface UserService {
	
	boolean signup(UserSignupRequest userSignupRequest);

	UserProfileResponse getProfile(String userId);

	UserMyPageResponse getMyPage(String userId);
	
	UserMyPageResponse updateMyPage(UserMyPageRequest userMyPageRequest);

	boolean checkDuplicatedId(String userId);

	boolean changePassword(ChangePasswordRequest changePasswordRequest);

	UserIdResponse findId(FindIdRequest findIdRequest);
	
}
