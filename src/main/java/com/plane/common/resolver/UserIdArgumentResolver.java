package com.plane.common.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.plane.common.annotation.UserId;
import com.plane.common.util.HeaderUtil;
import com.plane.common.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {
	
	private final JwtUtil jwtUtil;
	private final HeaderUtil headerUtil;
	
	public UserIdArgumentResolver(JwtUtil jwtUtil, HeaderUtil headerUtil) {
        this.jwtUtil = jwtUtil;
        this.headerUtil = headerUtil;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = headerUtil.getAccessToken(request);
        
        return jwtUtil.getUserId(token, "AccessToken");
    }
    
}