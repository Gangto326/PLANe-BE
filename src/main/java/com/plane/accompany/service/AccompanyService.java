package com.plane.accompany.service;

import java.util.List;

import com.plane.accompany.dto.AccompanyRegistRequest;
import com.plane.accompany.dto.AccompanyResponse;
import com.plane.accompany.dto.ApplyType;


public interface AccompanyService {

	boolean sendAccompanyRegist(String userId, AccompanyRegistRequest accompanyRegistRequest);

	List<AccompanyResponse> getAccompanyList(String userId, ApplyType type);

}
