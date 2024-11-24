package com.plane.accompany.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.plane.accompany.dto.AccompanyAcceptRequest;
import com.plane.accompany.dto.AccompanyApplyDto;
import com.plane.accompany.dto.AccompanyDetailRequest;
import com.plane.accompany.dto.AccompanyDetailResponse;
import com.plane.accompany.dto.AccompanyResponse;
import com.plane.accompany.dto.AccompanyTripInfo;
import com.plane.accompany.dto.ApplyType;
import com.plane.accompany.mapper.AccompanyMapper;
import com.plane.notification.dto.AccompanyNotificationDto;

@Repository
public class AccompanyRepositoryImpl implements AccompanyRepository {

	private final AccompanyMapper accompanyMapper;

	@Autowired
	public AccompanyRepositoryImpl(AccompanyMapper accompanyMapper) {
		this.accompanyMapper = accompanyMapper;
	}
	
	
	@Override
	public boolean existsAccompanyArticleByArticleId(Long articleId) {
		
		return accompanyMapper.existsAccompanyArticleByArticleId(articleId);
	}	
	
	@Override
	public boolean existsRegistByUserIdAndArticleId(String userId, Long articleId) {
		
		return accompanyMapper.existsRegistByUserIdAndArticleId(userId, articleId);
	}
	
	@Override
	public int insertAccompanyApply(AccompanyApplyDto accompanyApplyDto) {
		
		return accompanyMapper.insertAccompanyApply(accompanyApplyDto);
	}

	@Override
	public int insertApplyDetails(Long applyId, List<AccompanyDetailRequest> accompanyDetailRequest, boolean isUpdate) {
		
		return accompanyMapper.insertApplyDetails(applyId, accompanyDetailRequest, isUpdate);
	}


	@Override
	public List<AccompanyResponse> findAccompanyList(String userId, ApplyType type) {
		
		return accompanyMapper.findAccompanyList(userId, type);
	}


	@Override
	public boolean existsRegistByUserIdAndApplyId(String userId, Long applyId) {
		
		return accompanyMapper.existsRegistByUserIdAndApplyId(userId, applyId);
	}


	@Override
	public int deleteAllApplyDetails(Long applyId) {
		
		return accompanyMapper.deleteAllApplyDetails(applyId);
	}


	@Override
	public int updateApplyStatus(String userId, Long applyId, String status) {
		
		return accompanyMapper.updateApplyStatus(userId, applyId, status);
	}


	@Override
	public int deleteAccompany(String userId, Long applyId) {

		return accompanyMapper.updateApplyStatusDelete(userId, applyId);
	}


	@Override
	public boolean existsRegistByApplyId(Long applyId) {
		
		return accompanyMapper.existsRegistByApplyId(applyId);
	}


	@Override
	public AccompanyDetailResponse findAccompanyDetail(String userId, Long applyId, ApplyType applyType) {
		
		return accompanyMapper.findAccompanyDetail(userId, applyId, applyType);
	}


	@Override
	public AccompanyTripInfo findTripInfo(String userId, Long applyId) {
		
		return accompanyMapper.findTripInfo(userId, applyId);
	}


	@Override
	public int countAccompanyByTripId(Long tripId) {
		
		return accompanyMapper.countAccompanyByTripId(tripId);
	}


	@Override
	public int insertAccompany(Long tripId, String applicantId, String role) {
		
		return accompanyMapper.insertAccompany(tripId, applicantId, role);
	}


	@Override
	public int updateAccompanyApplyStatus(Long applyId) {

		return accompanyMapper.updateAccompanyApplyStatus(applyId);
	}


	@Override
	public boolean existsAccompanyByUserIdAndTripId(String userId, Long tripId) {
		
		return accompanyMapper.existsAccompanyByUserIdAndTripId(userId, tripId);
	}


	@Override
	public List<AccompanyNotificationDto> findAllArrivedAccompany() {
		
		return accompanyMapper.findAllArrivedAccompany();
	}

	
}
