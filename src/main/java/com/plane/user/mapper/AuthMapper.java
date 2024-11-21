package com.plane.user.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.plane.user.domain.User;
import com.plane.user.dto.TokenDto;
import com.plane.user.dto.UserLoginRequest;

@Mapper
public interface AuthMapper {
	
	@Select("""
			SELECT EXISTS (
	            SELECT 1
	            FROM Users
	            WHERE userId = #{userId}
	            AND state != '탈퇴'
	        )
			""")
	boolean existsUserById(String userId);
	
	
	@Select("SELECT * FROM users WHERE userId = #{userId}")
	User selectUserById(String userId);
	

	@Select("SELECT * FROM users WHERE userId = #{userId} AND password = #{hashedPassword}")
	User selectUser(UserLoginRequest userLoginRequest);

	
	@Insert("""
			INSERT INTO Tokens (userId, tokenType, tokenValue, issuedAt, expiresAt)
			VALUES (#{userId}, #{tokenType}, #{tokenValue}, #{issuedAt}, #{expiresAt})
			""")
	int insertToken(TokenDto tokenDto);


	@Select("""
			SELECT
				CASE 
					WHEN COUNT(*) > 0 THEN true
					ELSE false 
				END 
			FROM Tokens 
			WHERE tokenValue = #{token} 
			AND tokenType = 'RefreshToken' 
			AND isActive = true
			""")
	boolean isTokenActive(String token);

	
	@Delete("""
	        DELETE FROM Tokens
	        WHERE expiresAt < #{currentTime}
	        OR isActive = false
			""")
	int deleteExpiredTokens(long currentTime);


	@Update("UPDATE Tokens SET isActive=0 WHERE userId=#{userId} AND isActive=1")
	void updateTokenActiveById(String userId);

	
	@Update("UPDATE Tokens SET isActive=0 WHERE userId=#{userId} AND isActive=1 AND tokenValue != #{refreshToken}")
	void updateTokenActiveByIdAndToken(String userId, String refreshToken);
	
}
