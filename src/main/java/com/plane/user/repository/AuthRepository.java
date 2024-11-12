package com.plane.user.repository;

import com.plane.user.domain.User;
import com.plane.user.dto.TokenDto;
import com.plane.user.dto.UserLoginRequest;

public interface AuthRepository {

	int saveToken(TokenDto tokenDto);

	User selectUser(UserLoginRequest userLoginRequest);

}
