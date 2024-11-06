package com.plane.user.repository;

import com.plane.user.domain.User;
import com.plane.user.dto.UserMyPageResponse;
import com.plane.user.dto.UserProfileResponse;
import com.plane.user.dto.UserSignupRequest;

public interface UserRepository {

	User selectByPhone(String hashedPhone);

	int save(UserSignupRequest userSignupRequest);
	
	UserProfileResponse selectUserProfile(String userId);

	UserMyPageResponse selectUserMyPage(String userId);
}
