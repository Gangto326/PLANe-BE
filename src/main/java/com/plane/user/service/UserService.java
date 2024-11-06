package com.plane.user.service;

import com.plane.user.dto.UserLoginResponse;
import com.plane.user.dto.UserMyPageResponse;
import com.plane.user.dto.UserProfileResponse;
import com.plane.user.dto.UserSignupRequest;

public interface UserService {
	
	UserLoginResponse signup(UserSignupRequest userSignupRequest);

	UserProfileResponse getProfile(String userId);

	UserMyPageResponse getMyPage(String userId);
	
}
