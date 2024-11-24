package com.plane.accompany.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.plane.accompany.dto.AccompanyAcceptRequest;
import com.plane.accompany.dto.AccompanyApplyDto;
import com.plane.accompany.dto.AccompanyDetailRequest;
import com.plane.accompany.dto.AccompanyDetailResponse;
import com.plane.accompany.dto.AccompanyResponse;
import com.plane.accompany.dto.AccompanyTripInfo;
import com.plane.accompany.dto.ApplyType;
import com.plane.notification.dto.AccompanyNotificationDto;

public interface AccompanyRepository {
	
	boolean existsRegistByUserIdAndArticleId(String userId, Long articleId);
	
	boolean existsAccompanyArticleByArticleId(Long articleId);

	int insertAccompanyApply(AccompanyApplyDto accompanyApplyDto);
	
	int insertApplyDetails(Long applyId, List<AccompanyDetailRequest> accompanyDetailRequest, boolean isUpdate);

	List<AccompanyResponse> findAccompanyList(String userId, ApplyType type);

	boolean existsRegistByUserIdAndApplyId(String userId, Long applyId);

	int deleteAllApplyDetails(Long applyId);

	int updateApplyStatus(String userId, Long applyId, String status);

	int deleteAccompany(String userId, Long applyId);

	boolean existsRegistByApplyId(Long applyId);

	AccompanyDetailResponse findAccompanyDetail(String userId, Long applyId, ApplyType applyType);

	AccompanyTripInfo findTripInfo(String userId, Long applyId);

	int countAccompanyByTripId(Long tripId);

	int insertAccompany(Long tripId, String applicantId, String role);

	int updateAccompanyApplyStatus(Long applyId);
	
	boolean existsAccompanyByUserIdAndTripId(String userId, Long tripId);

	List<AccompanyNotificationDto> findAllArrivedAccompany();

}
