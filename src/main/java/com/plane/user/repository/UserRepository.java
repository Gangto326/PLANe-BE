package com.plane.user.repository;

import java.util.List;

import com.plane.trip.domain.TripStyle;
import com.plane.user.domain.User;
import com.plane.user.dto.FindIdRequest;
import com.plane.user.dto.UserIdResponse;
import com.plane.user.dto.UserMyPageRequest;
import com.plane.user.dto.UserMyPageResponse;
import com.plane.user.dto.UserProfileResponse;
import com.plane.user.dto.UserSignupRequest;

public interface UserRepository {

	User selectByPhone(String hashedPhone);

	int saveUser(UserSignupRequest userSignupRequest);
	
	UserProfileResponse selectUserProfile(String userId);

	UserMyPageResponse selectUserMyPage(String userId);

	int updateUser(UserMyPageRequest userMyPageRequest);

	int deleteTripStyle(String userId);

	int deleteTripThema(String userId);

	int insertTripStyle(String userId, List<Integer> tripStyle);

	int insertTripThema(String userId, List<Integer> tripThema);

	int findUserById(String userId);

	int updateUserPassword(String userId, String newPassword);

	boolean existsById(String userId);

	boolean existsByEmail(String email);

	int insertVerificationCode(String email, String verificationCode);

	boolean existsCodeByEmail(FindIdRequest findIdRequest);

	List<String> selectIdByEmail(FindIdRequest findIdRequest);

}
