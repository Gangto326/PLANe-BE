package com.plane.user.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.plane.user.domain.User;
import com.plane.user.dto.UserSignupRequest;

@Mapper
public interface UserMapper {
	
	@Select("SELECT * FROM users WHERE phone = #{hashedPhone}")
	User selectByPhone(String hashedPhone);
	
	
	@Results({
        @Result(property = "hashedPassword", column = "password"),
        @Result(property = "hashedPhone", column = "phone")
    })
	@Insert("INSERT INTO Users (userId, password, nickName, phone, email) "
			+ "VALUES (#{userId}, #{hashedPassword}, #{nickName}, #{hashedPhone}, #{email})")
	int insertUser(UserSignupRequest userSignupRequest);
	
}
