package com.plane.user.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.plane.user.domain.User;
import com.plane.user.dto.TokenDto;
import com.plane.user.dto.UserLoginRequest;

@Mapper
public interface AuthMapper {

	@Select("SELECT * FROM users WHERE userId = #{userId} AND password = #{hashedPassword}")
	User selectUser(UserLoginRequest userLoginRequest);

	
	@Insert("""
			
			""")
	int insertToken(TokenDto tokenDto);
	
}
