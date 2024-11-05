package com.plane.common.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class HashUtil {
	
	public String generateSHA512(String plainText) {
		return DigestUtils.sha3_512Hex(plainText);
	}
	
	 // 비밀번호 해시
    public String hashPassword(String password) {
        return DigestUtils.sha3_512Hex(password);
    }
    
    // 전화번호 정규화 및 해시
    public String hashPhone(String phone) {
        String normalizedPhone = phone.replaceAll("[^0-9]", "");
        return DigestUtils.sha3_512Hex(normalizedPhone);
    }

}
