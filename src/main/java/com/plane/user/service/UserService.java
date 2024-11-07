package com.plane.user.service;

import com.plane.user.dto.UserLoginResponse;
import com.plane.user.dto.UserMyPageRequest;
import com.plane.user.dto.UserMyPageResponse;
import com.plane.user.dto.UserProfileResponse;
import com.plane.user.dto.UserSignupRequest;

import java.util.List;


public interface UserService {
	
	UserLoginResponse signup(UserSignupRequest userSignupRequest);

	UserProfileResponse getProfile(String userId);

	UserMyPageResponse getMyPage(String userId);
	
	UserMyPageResponse updateMyPage(UserMyPageRequest userMyPageRequest);
	
}
