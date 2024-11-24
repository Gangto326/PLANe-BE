package com.plane.manner.service;

import com.plane.manner.dto.MannerEvaluateRequest;

import jakarta.validation.Valid;

public interface MannerService {

	boolean evaluateManner(String userId, @Valid MannerEvaluateRequest mannerEvaluateRequest);

}
