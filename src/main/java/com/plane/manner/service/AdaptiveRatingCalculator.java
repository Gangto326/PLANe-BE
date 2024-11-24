package com.plane.manner.service;

import java.util.Arrays;
import java.util.List;

import com.plane.common.exception.custom.InvalidParameterException;

public final class AdaptiveRatingCalculator {
	
	static final double BASELINE_SCORE = 3.0;
	static final double WEIGHT_COEFFICIENT = 1.5;
	static final double MIN_SCORE = 0.0;
	static final double MAX_SCORE = 100.0;
	static final double MID_SCORE = 30.0;
	
	
	public static double calculateAdaptiveScore(double currentScore, List<Integer> rating) {
		
		if (rating.stream().anyMatch(score -> score < 1 || score > 5)) {
		    throw new InvalidParameterException("각 항목의 점수는 1 ~ 5 사이여야 합니다.");
		}
		
		//총 점수 평균 계산
		double averageScore = rating.stream()
									.mapToDouble(Integer::doubleValue)
					                .average()
					                .orElse(BASELINE_SCORE);
	
		// 점수 변화량 계산
		double scoreDifference = averageScore - BASELINE_SCORE;
		
		// 점수 변동 보정 값 계산
		// 30점을 기준으로 상하로 갈수록 변동폭이 작아지도록 수정
		double adjustmentFactor;
		
		if (scoreDifference > 0) {
			adjustmentFactor = (MAX_SCORE - currentScore) / (MAX_SCORE - MID_SCORE);
		} else {
			adjustmentFactor = (currentScore - MIN_SCORE) / (MID_SCORE - MIN_SCORE);
		}
		
		// 최종 점수 계산
		double finalScore = currentScore + scoreDifference * WEIGHT_COEFFICIENT * adjustmentFactor;
		
		return Math.min(Math.max(finalScore, MIN_SCORE), MAX_SCORE);
		
	}
	
}
