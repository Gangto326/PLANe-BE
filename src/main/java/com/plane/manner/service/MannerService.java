package com.plane.manner.service;

import java.util.List;

import com.plane.manner.dto.MannerEvaluateRequest;
import com.plane.manner.dto.MannerUserResponse;

import jakarta.validation.Valid;

public interface MannerService {

	boolean evaluateManner(String userId, @Valid MannerEvaluateRequest mannerEvaluateRequest);

	List<MannerUserResponse> getMannerDetail(String userId, Long tripId);

}
