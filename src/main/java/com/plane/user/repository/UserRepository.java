package com.plane.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

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

	int updateUser(String userId, UserMyPageRequest userMyPageRequest);

	int findUserById(String userId);

	int updateUserPassword(String userId, String newPassword);

	boolean existsById(String userId);

	boolean existsByEmail(String email);

	int insertVerificationCode(String email, String verificationCode);

	boolean existsCodeByEmail(String email, String verificationCode);

	List<String> selectIdByEmail(FindIdRequest findIdRequest);

	String selectUserNicknameByUserId(String userId);

	User selectUserByUserId(String userId);

	int updateVerificationCodeDelete(String email);
	
	int deleteVerificationCodes();

	int insertAuthenticationfile(String userId, String authenticationfileUrl, String originalFilename);

	boolean existsAuthenticationfileByUserId(String userId);
	
}
