package com.plane.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plane.common.response.ApiResponse;
import com.plane.common.util.HeaderUtil;
import com.plane.user.dto.AuthResponse;
import com.plane.user.dto.TokenDto;
import com.plane.user.dto.UserLoginRequest;
import com.plane.user.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthService authService;
	
	@Autowired
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<Void>> login(
			@Valid @RequestBody UserLoginRequest userLoginRequest,
			HttpServletRequest request) {
		
		AuthResponse authResponse = authService.login(userLoginRequest);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HeaderUtil.getAuthorizationHeaderName(), HeaderUtil.getTokenPrefix() + authResponse.getAccessToken());
		
		// RefreshToken을 HttpOnly Cookie로 전달.
		ResponseCookie responseCookie = ResponseCookie
				.from(HeaderUtil.getRefreshCookieName(), authResponse.getRefreshToken())
				.domain("localhost") // 어떤 사이트에서 쿠키를 사용할 수 있도록 허용할 지 설정.
				.path("/") // 위 사이트에서 쿠키를 허용할 경로를 설정.
				.httpOnly(true) // HTTP 통신을 위해서만 사용하도록 설정.
				.secure(true) // Set-Cookie 설정.
				.maxAge(authResponse.getMaxAge() / 1000) // RefreshToken과 동일한 만료 시간으로 설정.
				.sameSite("None") // 동일한 사이트에서 사용할 수 있도록 설정 None: 동일한 사이트가 아니어도 된다.
				.build();
		
		return ResponseEntity.ok()
	            .headers(httpHeaders)
	            .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
	            .header("Access-Control-Expose-Headers", "Set-Cookie")
	            .body(ApiResponse.success(null, "로그인 성공"));
	}
	
	
	@DeleteMapping("/logout")
	public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request) {
		
		// HTTP Header의 Authorization (AccessToken) 추출.
		String accessToken = HeaderUtil.getAccessToken(request);
		
		authService.logout(accessToken);
		
		// maxAge(0)으로 RefreshToken 삭제.
		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseCookie responseCookie = ResponseCookie
				.from(HeaderUtil.getRefreshCookieName(), "")
				.domain("localhost")
				.path("/")
				.httpOnly(true)
				.secure(true)
				.maxAge(0)
				.sameSite("None")
				.build();

		return ResponseEntity.ok()
				.headers(httpHeaders).header(HttpHeaders.SET_COOKIE, responseCookie.toString())
				.body(ApiResponse.success(null, "로그아웃 성공"));
	}

	
	@GetMapping("/refresh")
	public ResponseEntity<ApiResponse<Boolean>> refresh(HttpServletRequest httpServletRequest) {
		
		// Client에서 withCredentials 옵션으로 설정하여 전송된 경우, RefreshToken을 받을 수 있다.
		String refreshToken = HeaderUtil.getRefreshToken(httpServletRequest);
		
		// RefreshToken을 바탕으로 새로운 AccessToken을 발급.
		TokenDto newAccessToken = authService.reGenerateToken(refreshToken);
		
		// 새로운 Accesstoken을 Header에 추가.
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HeaderUtil.getAuthorizationHeaderName(), HeaderUtil.getTokenPrefix() + newAccessToken.getTokenValue());
		
		// 새로운 AccessToken을 전송.
		return ResponseEntity.ok()
				.headers(httpHeaders)
				.body(ApiResponse.success(true, "Token 발급 성공"));
	}
}