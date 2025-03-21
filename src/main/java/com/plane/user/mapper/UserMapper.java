package com.plane.user.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Many;

import com.plane.trip.domain.TripStyle;
import com.plane.trip.domain.TripThema;
import com.plane.user.domain.User;
import com.plane.user.dto.UserProfileResponse;
import com.plane.user.dto.UserSignupRequest;
import com.plane.user.dto.FindIdRequest;
import com.plane.user.dto.MannerTagDto;
import com.plane.user.dto.UserIdResponse;
import com.plane.user.dto.UserLoginRequest;
import com.plane.user.dto.UserMyPageRequest;
import com.plane.user.dto.UserMyPageResponse;

@Mapper
public interface UserMapper {
	
	@Select("SELECT * FROM users WHERE phone = #{hashedPhone}")
	User selectByPhone(String hashedPhone);
	
	
	@Insert("""
			INSERT INTO Users (userId, password, nickName, phone, email)
			VALUES (#{userId}, #{hashedPassword}, #{nickName}, #{hashedPhone}, #{email})
			""")
	@Results({
        @Result(property = "hashedPassword", column = "password"),
        @Result(property = "hashedPhone", column = "phone")
    })
	int insertUser(UserSignupRequest userSignupRequest);
	
	
	@Select("""
		    SELECT userId, nickName, role, manner, profileUrl, introduce, isPublic
		    FROM Users
		    WHERE userId = #{userId}
		    """)
	@Results({
		@Result(property = "userId", column = "userId"),
	    @Result(property = "tripStyle", column = "userId", many = @Many(select = "com.plane.trip.mapper.TripMapper.selectTripStylesByUserId")),
	    @Result(property = "mannerTags", column = "userId", many = @Many(select = "selectMannerTags"))
	})
	UserProfileResponse selectUserProfile(String userId);


	@Select("""
			SELECT mannerTagId
			FROM Manners
			WHERE userId = #{userId}
			GROUP BY mannerTagId
			ORDER BY SUM(score) DESC
			LIMIT 3
		    """)
	List<Integer> selectMannerTags(String userId);

	
	@Select("""
		    SELECT userId, nickName, role, manner, profileUrl, introduce, isPublic
		    FROM Users
		    WHERE userId = #{userId}
		    """)
	@Results({
		@Result(property = "userId", column = "userId"),
	    @Result(property = "tripStyle", column = "userId", many = @Many(select = "com.plane.trip.mapper.TripMapper.selectTripStylesByUserId")),
	    @Result(property = "tripThema", column = "userId", many = @Many(select = "com.plane.trip.mapper.TripMapper.selectTripThemasByUserId")),
	    @Result(property = "mannerTags", column = "userId", many = @Many(select = "selectMannerTags"))
	})
	UserMyPageResponse selectUserMyPage(String userId);

	
	@Update("""
			UPDATE Users 
	        SET nickName = #{request.nickName},
	            profileUrl = #{request.profileUrl},
	            introduce = #{request.introduce},
	            isPublic = #{request.isPublic}
			WHERE userId = #{userId}
			""")
	int updateUser(@Param("userId") String userId, @Param("request") UserMyPageRequest userMyPageRequest);
	
	
	@Select("""
			SELECT COUNT(*)
			FROM Users
			WHERE userId = #{userId}
			""")
	int findUserById(String userId);

	
	@Update("""
			UPDATE Users 
	        SET password = #{newPassword}
			WHERE userId = #{userId}
			""")
	int updateUserPassword(@Param("userId") String userId, @Param("newPassword") String newPassword);


	@Select("""
	        SELECT userId
	        FROM users
	        WHERE userId = #{userId}
		    """)
	Optional<String> selectUserIdById(String userId);

	
	@Select("""
			SELECT userId
	        FROM users
	        WHERE email = #{email}
		    """)
	List<String> selectUserIdByEmail(String email);

	
	@Insert("""
			INSERT INTO VerificationCodes (email, verificationCode)
			VALUES (#{email}, #{verificationCode})
			""")
	int insertVerificationCode(@Param("email") String email, @Param("verificationCode") String verificationCode);
	
	
	@Select("""
	        SELECT *
	        FROM VerificationCodes
	        WHERE email = #{email}
	        AND verificationCode = #{verificationCode}
            AND createdDate > DATE_SUB(NOW(), INTERVAL 10 MINUTE)
            AND deletedDate IS NULL
            LIMIT 1;
		    """)
	Optional<String> selectCodeByEmail(@Param("email") String email, @Param("verificationCode") String verificationCode);


	@Select("""
			SELECT nickName
			FROM users
			WHERE userId = #{userId}
		    """)
	String selectUserNicknameByUserId(String userId);


	@Select("""
			SELECT *
			FROM Users
			WHERE userId = #{userId}
			""")
	User selectUserByUserId(@Param("userId") String userId);

	
	@Update("""
			UPDATE VerificationCodes
	        SET deletedDate = NOW()
			WHERE email = #{email}
			AND deletedDate IS NULL
			""")
	int updateVerificationCodeDelete(String email);

	
	@Delete("""
	        DELETE FROM VerificationCodes
	        WHERE createdDate < DATE_SUB(NOW(), INTERVAL 10 MINUTE)
	        OR deletedDate IS NOT NULL
	        """)
	int deleteVerificationCodes();

	
	@Insert("""
			INSERT INTO AuthenticationData (userId, authenticationfileUrl, originalFilename)
			VALUES (#{userId}, #{url}, #{originalFilename})
			""")
	int insertAuthenticationfile(@Param("userId") String userId, @Param("url") String authenticationfileUrl, @Param("originalFilename") String originalFilename);

	
	@Select("""
		    SELECT EXISTS (
			   SELECT 1
			   FROM AuthenticationData
			   WHERE userId = #{userId}
			   AND deletedDate IS NULL
			)
			""")
	boolean existsAuthenticationfileByUserId(String userId);
}
