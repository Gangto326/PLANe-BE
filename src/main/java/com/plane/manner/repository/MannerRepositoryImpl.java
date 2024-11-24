package com.plane.manner.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.plane.manner.dto.MannerEvaluateRequest;
import com.plane.manner.mapper.MannerMapper;

@Repository
public class MannerRepositoryImpl implements MannerRepository {
	
	private final MannerMapper mannerMapper;

	@Autowired
	public MannerRepositoryImpl(MannerMapper mannerMapper) {
		this.mannerMapper = mannerMapper;
	}

	
	@Override
	public Double getCurrentScore(String evaluatorId) {
		
		return mannerMapper.selectCurrentScore(evaluatorId);
	}


	@Override
	public int insertManners(String userId, MannerEvaluateRequest mannerEvaluateRequest) {
		
		return mannerMapper.insertManners(userId, mannerEvaluateRequest);
	}


	@Override
	public int updateUserManner(String evaluatorId, double newScore) {
		
		return mannerMapper.updateUserManner(evaluatorId, newScore);
	}
	
	
	
}
