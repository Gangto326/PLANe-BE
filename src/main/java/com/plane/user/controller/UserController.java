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
import com.plane.user.dto.UserSignupRequest;
import com.plane.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
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
	
	@GetMapping("/profile/{userId}")
	public ResponseEntity<ApiResponse<?>> profile(@PathVariable String userId) {
		
		return null;
	}
	
	@GetMapping("/myPage")
	public ResponseEntity<ApiResponse<?>> myPage() {
		
		return null;
	}
	
	@PatchMapping("/myPage")
	public ResponseEntity<ApiResponse<?>> updateMyPage() {
		
		return null;
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
	@DeleteMapping("/signOut")
	public ResponseEntity<ApiResponse<?>> signOut() {
		
		return null;
	}
	
	@PostMapping("/find/id")
	public ResponseEntity<ApiResponse<?>> findId() {
		
		return null;
	}
	
	@PostMapping("/find/password")
	public ResponseEntity<ApiResponse<?>> findPassword() {
		
		return null;
	}
	
	@GetMapping("/checkId/{userId}")
	public ResponseEntity<ApiResponse<?>> checkId(@PathVariable String userId) {
		
		return null;
	}
	
	@PatchMapping("/changePassword")
	public ResponseEntity<ApiResponse<?>> changePassword() {
		
		return null;
	}
}
