package com.plane.filter;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plane.common.exception.ErrorCode;
import com.plane.common.response.ApiResponse;
import com.plane.common.response.ErrorResponse;
import com.plane.common.util.HeaderUtil;
import com.plane.common.util.JwtUtil;
import com.plane.user.service.AuthService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
	
	private final JwtUtil jwtUtil;
	private final AuthService authService;
	private final ObjectMapper objectMapper;
	
	public JwtFilter(JwtUtil jwtUtil, AuthService authService, ObjectMapper objectMapper) {
		this.jwtUtil = jwtUtil;
		this.authService = authService;
		this.objectMapper = objectMapper;
	}
	
	
	// 토큰 에러 응답
	private void sendErrorResponse(HttpServletResponse httpServletResponse, ErrorCode errorCode) throws IOException {
		httpServletResponse.setStatus(errorCode.getStatus().value());
		httpServletResponse.setContentType("application/json;charset=UTF-8");
		httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");

	    ApiResponse<ErrorResponse> apiResponse = ApiResponse.error(errorCode, errorCode.getMessage());
	    
	    String jsonResponse = objectMapper.writeValueAsString(apiResponse);

	    PrintWriter writer = httpServletResponse.getWriter();
	    writer.write(jsonResponse);
	    writer.flush();
	}
	

	@Override
	protected boolean shouldNotFilter(HttpServletRequest httpServletRequest) throws ServletException {
		
		// 필터를 거치치 않을 URI 설정.
		String[] excludeURIList = {
				"/api/auth/login", "/api/auth/logout",
				"/api/users/signup", "/api/users/verification", "/api/users/find/id", "/api/users/find/password", "/api/users/checkId"
		};
		
		// 필터를 거치치 않을 URI와 일치하는 경우 True(필터를 거치지 않음) / False(필터를 거침)
		for(String excludeURI : excludeURIList) {
			if(httpServletRequest.getRequestURI().contains(excludeURI)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
			throws ServletException, IOException {

		// Preflight 설정.
		// OPTIONS 요청이 발생하는 경우.
		// 아래 Header를 설정하여 전송해주어야 한다.
		// 반드시 응답으로 200 OK가 전송되어야 한다.
		if(httpServletRequest.getMethod().equals("OPTIONS")) {
			httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
			httpServletResponse.setHeader("Access-Control-Allow-Headers", "content-type, authorization");
			httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");
			httpServletResponse.setStatus(HttpStatus.OK.value());
			
			return;
		}
		
		// Refresh 요청이 발생한 경우.
		if(httpServletRequest.getRequestURI().contains("/auth/refresh")) {
			
			String refreshToken = HeaderUtil.getRefreshToken(httpServletRequest);
			
			if(refreshToken != null) {
				
				// 토큰이 비정상이거나, 비활성 토큰인 경우는 새로운 토큰을 발급해줄 수 없다.
				if(!jwtUtil.isValidToken(refreshToken, "RefreshToken") || !authService.isTokenActive(refreshToken)) {
					
					sendErrorResponse(httpServletResponse, ErrorCode.REFRESH_TOKEN_EXPIRED);
					return;
				}
			}
		}
		
		// 다른 요청이 발생한 경우.
		else {
			String accessToken = HeaderUtil.getAccessToken(httpServletRequest);
			
			// AccessToken이 없거나, 비정상 토큰인 경우 접근 불가 처리.
			if(accessToken == null || !jwtUtil.isValidToken(accessToken, "AccessToken")) {
				
				sendErrorResponse(httpServletResponse, ErrorCode.ACCESS_TOKEN_EXPIRED);
				return;
			}
		}
		
		// 위 조건을 제외한 모든 요청은 그대로 진행.
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
}