package com.plane.accompany.service;

import com.plane.accompany.dto.AccompanyRegistRequest;


public interface AccompanyService {

	boolean sendAccompanyRegist(String userId, AccompanyRegistRequest accompanyRegistRequest);

}
