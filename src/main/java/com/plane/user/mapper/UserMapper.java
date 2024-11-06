package com.plane.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Many;

import com.plane.user.domain.User;
import com.plane.user.dto.UserProfileResponse;
import com.plane.user.dto.UserSignupRequest;
import com.plane.user.dto.MannerTagDto;
import com.plane.user.dto.TripStyleDto;

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
	
	
	@Select("""
		    SELECT userId, nickName, manner, profileUrl, introduce, isPublic
		    FROM Users
		    WHERE userId = #{userId}
		    """)
	@Results({
		@Result(property = "userId", column = "userId"),
	    @Result(property = "tripStyle", column = "userId", many = @Many(select = "selectTripStyles")),
	    @Result(property = "mannerTags", column = "userId", many = @Many(select = "selectMannerTags"))
	})
	UserProfileResponse selectUserProfile(String userId);

	@Select("""
		    SELECT ts.styleId as id, ts.styleName
		    FROM TripStyle ts
		    JOIN UsersTripStyle uts ON ts.styleId = uts.styleId
		    WHERE uts.userId = #{userId}
		    """)
	List<TripStyleDto> selectTripStyles(String userId);

	@Select("""
		    SELECT mannerTagName
		    FROM MannerTags
		    WHERE userId = #{userId}
		    GROUP BY mannerTagName
		    ORDER BY COUNT(*) DESC
		    LIMIT 3
		    """)
	List<MannerTagDto> selectMannerTags(String userId);
}
