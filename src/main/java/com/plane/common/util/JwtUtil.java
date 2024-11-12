package com.plane.common.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.plane.user.domain.User;
import com.plane.user.dto.TokenDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

/**
 * 토큰 발급 및 검증을 위한 클래스.
 */
@Component
public class JwtUtil {
	
	private final HashUtil hashUtil;
	
	public JwtUtil(HashUtil hashUtil) {
		this.hashUtil = hashUtil;
	}
	
	private final long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 15; // 15분
	private final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 4; // 4시간
	
	@Value("${jwt.secretkey.accesstoken}")
	private String accessTokenSecretKey;
	
	@Value("${jwt.secretkey.refreshtoken}")
	private String refreshTokenSecretKey;

	
	private SecretKey getSecretKey(String type) {
		// AccessToken과 RefreshToken을 서로 다른 키를 이용하여 발급.
		// AccessToken과 RefreshToken을 서로 변경하여 인증 시도를 방지.
		
		if(type.equals("AccessToken")) {
			return Keys.hmacShaKeyFor(accessTokenSecretKey.getBytes());
		}
		else if(type.equals("RefreshToken")) {
			return Keys.hmacShaKeyFor(refreshTokenSecretKey.getBytes());
		}
		
		return null;
	}
	
	
	private long getExpiration(long issuedAt, String type) {
		if(type.equals("AccessToken")) {
			return issuedAt + ACCESS_TOKEN_EXPIRE_TIME;
		}
		else if(type.equals("RefreshToken")) {
			return issuedAt + REFRESH_TOKEN_EXPIRE_TIME;
		}
		
		return 0L;
	}
	
	
	public TokenDto generateToken(User user, String type) {
		
		// 발급 시간과 만료 시간을 계산.
		long issuedAt = System.currentTimeMillis();
		long expiresAt = getExpiration(issuedAt, type);
		
		String token = Jwts.builder()
				.claim("userId", user.getUserId())
				.claim("role", user.getRole())
				.issuedAt(new Date(issuedAt))
				.expiration(new Date(expiresAt))
				.signWith(getSecretKey(type))
				.compact();
		
		TokenDto tokenDto = new TokenDto();
		tokenDto.setUserId(user.getUserId());
		tokenDto.setToken(token);
		tokenDto.setHashedToken(hashUtil.generateSHA512(token));
		tokenDto.setIssuedAt(issuedAt);
		tokenDto.setExpiresAt(expiresAt);
		
		return tokenDto;
	}

	
	public String getUserId(String token, String type) {
		if(isValidToken(token, type)) {
			
			Claims payload = Jwts.parser()
					.verifyWith(getSecretKey(type))
					.build()
					.parseSignedClaims(token)
					.getPayload();
			
			return payload.get("userId", String.class);
		}
		
		return null;
	}

	
	public boolean isValidToken(String token, String type) {
		Jws<Claims> payload = null;
		
		try {
			payload = Jwts.parser()
					.verifyWith(getSecretKey(type))
					.build()
					.parseSignedClaims(token);
		} catch(ExpiredJwtException | SignatureException | MalformedJwtException e) {
			return false;
		}
		
		return true;
	}
	
}