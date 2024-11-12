package com.plane.common.util;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.plane.common.exception.custom.InvalidParameterException;

@Component
public class FormatUtil {
	
	private static Pattern pattern;
	
	
	public static boolean isValidUserId(String userId) {
		
		final int MIN_LENGTH = 4;
	    final int MAX_LENGTH = 20;
	    final Pattern ID_PATTERN = Pattern.compile("^[a-zA-Z0-9]*$");
		
		if (userId == null || userId.isBlank()) {
	        throw new InvalidParameterException("아이디는 필수입니다.");
	    }
	
	    if (userId.length() < MIN_LENGTH || userId.length() > MAX_LENGTH) {
	        throw new InvalidParameterException(
	            String.format("아이디는 %d~%d자 사이여야 합니다.", MIN_LENGTH, MAX_LENGTH)
	        );
	    }
	
	    if (!ID_PATTERN.matcher(userId).matches()) {
	        throw new InvalidParameterException("아이디는 영문과 숫자만 가능합니다.");
	    }
		
		return pattern.matcher(userId).find();
	}
	
	
	public static boolean isValidEmail(String userEmail) {
		
		pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
		
		return pattern.matcher(userEmail).find();
	}

}