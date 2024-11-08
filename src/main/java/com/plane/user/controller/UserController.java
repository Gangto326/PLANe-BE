package com.plane.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plane.common.response.ApiResponse;
import com.plane.user.dto.UserLoginRequest;
import com.plane.user.dto.UserLoginResponse;
import com.plane.user.dto.UserMyPageRequest;
import com.plane.user.dto.UserProfileResponse;
import com.plane.user.dto.UserSignupRequest;
import com.plane.user.dto.UserMyPageResponse;
import com.plane.user.dto.FindPasswordRequest;
import com.plane.user.dto.MannerTagDto;
import com.plane.user.dto.TripStyleDto;
import com.plane.user.service.UserEmailService;
import com.plane.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private final UserService userService;
	private final UserEmailService userEmailService;
	
	@Autowired
	public UserController(UserService userService, UserEmailService userEmailService) {
		this.userService = userService;
		this.userEmailService = userEmailService;
	}
	
	// 로그인 & 인증 관련
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<UserLoginResponse>> login(UserLoginRequest userLoginRequest) {
		
		
		return null;
	}
	
	@PostMapping("/login/social")
	public ResponseEntity<ApiResponse<?>> socialLogin() {
		
		return null;
	}
	
	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<?>> logout() {
		
		return null;
	}
	
	
	// 회원 관련
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<UserLoginResponse>> signup(@Valid @RequestBody UserSignupRequest userSignupRequest) {
		
		UserLoginResponse userLoginResponse = userService.signup(userSignupRequest);
		return ResponseEntity.ok(ApiResponse.success(userLoginResponse, "회원이 정상적으로 등록되었습니다."));
	}
	
	@PostMapping("/profile")
	public ResponseEntity<ApiResponse<UserProfileResponse>> profile(@RequestBody String userId) {
		
		UserProfileResponse userProfileResponse = userService.getProfile(userId);
		return ResponseEntity.ok(ApiResponse.success(userProfileResponse, "프로필 정보를 정상적으로 불러왔습니다."));
	}
	
	@PostMapping("/myPage")
	public ResponseEntity<ApiResponse<UserMyPageResponse>> myPage(@RequestBody String userId) {
		
		UserMyPageResponse userMyPageResponse = userService.getMyPage(userId);
		return ResponseEntity.ok(ApiResponse.success(userMyPageResponse, "마이페이지 정보를 정상적으로 불러왔습니다."));
	}
	
	@PatchMapping("/myPage")
	public ResponseEntity<ApiResponse<UserMyPageResponse>> updateMyPage(@Valid @RequestBody UserMyPageRequest userMyPageRequest) {
		
		UserMyPageResponse userMyPageResponse = userService.updateMyPage(userMyPageRequest);
		return ResponseEntity.ok(ApiResponse.success(userMyPageResponse, "마이페이지 수정이 정상적으로 처리되었습니다."));
	}
	
	
	// 인증 관련
	@PostMapping("/authentication")
	public ResponseEntity<ApiResponse<?>> authentication() {
		
		return null;
	}
	
	@PostMapping("/authenticationData")
	public ResponseEntity<ApiResponse<?>> authenticationData() {
		
		return null;
	}
	
	
	// 계정 찾기 & 변경, 탈퇴
	@DeleteMapping("/signout")
	public ResponseEntity<ApiResponse<?>> signOut() {
		
		return null;
	}
	
	@PostMapping("/find/id")
	public ResponseEntity<ApiResponse<?>> findId() {
		
		return null;
	}
	
	@PostMapping("/find/password")
	public ResponseEntity<ApiResponse<Boolean>> findPassword(@Valid @RequestBody FindPasswordRequest findPasswordRequest) {
		
		userEmailService.sendNewPassword(findPasswordRequest.getUserId(), findPasswordRequest.getEmail());
        return ResponseEntity.ok(ApiResponse.success(true, "비밀번호가 발송되었습니다"));
	}
	
	@GetMapping("/checkId/{userId}")
	public ResponseEntity<ApiResponse<Boolean>> checkId(@PathVariable String userId) {
		
		userService.checkDuplicatedId(userId);
		return ResponseEntity.ok(ApiResponse.success(true, "사용할 수 있는 아이디입니다."));
	}
	
	@PatchMapping("/changePassword")
	public ResponseEntity<ApiResponse<?>> changePassword() {
		
		return null;
	}
}
