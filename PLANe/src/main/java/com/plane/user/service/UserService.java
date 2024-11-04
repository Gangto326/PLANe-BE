package com.plane.user.service;

import com.plane.user.dto.UserLoginResponse;
import com.plane.user.dto.UserSignupRequest;

public interface UserService {
	
	UserLoginResponse signup(UserSignupRequest userSignupRequest);
	
}
