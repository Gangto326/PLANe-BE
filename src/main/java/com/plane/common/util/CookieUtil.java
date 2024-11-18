package com.plane.common.util;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CookieUtil {
	
	private static final String COOKIE_PREFIX = "article_view_";
    
	
    public String generateViewCookieName(Long articleId) {
        return COOKIE_PREFIX + articleId;
    }
    
    
    public boolean checkViewCookie(Long articleId, HttpServletRequest request) {
    	String cookieName = COOKIE_PREFIX + articleId;
        Cookie[] cookies = request.getCookies();
        
        if (cookies == null) {
            return false;
        }
        
        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName()) 
                && "true".equals(cookie.getValue())) {
                return true;
            }
        }
        
        return false;
    }
    
	
}
