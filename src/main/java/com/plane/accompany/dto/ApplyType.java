package com.plane.accompany.dto;

import com.plane.common.exception.custom.InvalidParameterException;

public enum ApplyType {

	RECEIVED("a.userId", "b.authorId"),     // 게시글 작성자가 나인 경우 신청자의 닉네임 필요
    SENT("b.authorId", "a.userId");      // 신청자가 나인 경우 작성자의 닉네임 필요
    
    private final String userColumn;
    private final String whereCondition;
    
    ApplyType(String userColumn, String whereCondition) {
        this.userColumn = userColumn;
        this.whereCondition = whereCondition;
    }
    
    public String getUserColumn() {
        return userColumn;
    }

    public String getWhereCondition() {
        return whereCondition;
    }
    
    
    // String 값을 ApplyType으로 변환
    public static ApplyType from(String type) {
    	
        try {
            return ApplyType.valueOf(type.toUpperCase());
        } catch (Exception e) {
        	throw new InvalidParameterException("요청은 RECEIVED 또는 SENT 여야 합니다.");
        }
    }
    
}
