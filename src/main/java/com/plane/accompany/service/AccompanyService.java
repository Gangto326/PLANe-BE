package com.plane.accompany.service;

import java.util.List;

import com.plane.accompany.dto.AccompanyArticleDetailRequest;
import com.plane.accompany.dto.AccompanyDetailResponse;
import com.plane.accompany.dto.AccompanyRegistRequest;
import com.plane.accompany.dto.AccompanyResponse;
import com.plane.accompany.dto.AccompanyUpdateRequest;
import com.plane.accompany.dto.ApplyType;

import jakarta.validation.Valid;


public interface AccompanyService {

	boolean sendAccompanyRegist(String userId, AccompanyRegistRequest accompanyRegistRequest);

	List<AccompanyResponse> getAccompanyList(String userId, String type);

	boolean updateAccompany(String userId, AccompanyUpdateRequest accompanyUpdateRequest);

	boolean deleteAccompany(String userId, Long applyId);

	AccompanyDetailResponse getAccompanyDetail(String userId, AccompanyArticleDetailRequest accompanyArticleDetailRequest);

}
