package com.plane.manner.repository;

import com.plane.manner.dto.MannerEvaluateRequest;

public interface MannerRepository {

	Double getCurrentScore(String evaluatorId);

	int insertManners(String userId, MannerEvaluateRequest mannerEvaluateRequest);

	int updateUserManner(String evaluatorId, double newScore);
	
}
