package com.plane.manner.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.accompany.repository.AccompanyRepository;
import com.plane.article.repository.ArticleRepository;
import com.plane.common.exception.custom.CreationFailedException;
import com.plane.common.exception.custom.TripNotFoundException;
import com.plane.common.exception.custom.UpdateFailedException;
import com.plane.common.exception.custom.UserNotFoundException;
import com.plane.manner.dto.MannerEvaluateRequest;
import com.plane.manner.dto.MannerTagRequest;
import com.plane.manner.dto.MannerUserResponse;
import com.plane.manner.repository.MannerRepository;

import jakarta.validation.Valid;

@Service
@Transactional
public class MannerServiceImpl implements MannerService {

	private final MannerRepository mannerRepository;
	private final AccompanyRepository accompanyRepository;
	private final ArticleRepository articleRepository;
	
	@Autowired
	public MannerServiceImpl(MannerRepository mannerRepository, AccompanyRepository accompanyRepository, ArticleRepository articleRepository) {
		this.mannerRepository = mannerRepository;
		this.accompanyRepository = accompanyRepository;
		this.articleRepository = articleRepository;
	}

	
	@Override
	public boolean evaluateManner(String userId, MannerEvaluateRequest mannerEvaluateRequest) {
		
		if (!accompanyRepository.existsAccompanyByUserIdAndTripId(userId, mannerEvaluateRequest.getTripId())) {
			throw new TripNotFoundException("함께 간 동행 정보를 찾을 수 없습니다.");
		}
		
		Double currentScore = mannerRepository.getCurrentScore(mannerEvaluateRequest.getEvaluatorId());
		
		if (currentScore == null) {
			throw new UserNotFoundException("해당 유저가 존재하지 않습니다.");
		}
		
		if (mannerRepository.insertManners(userId, mannerEvaluateRequest) != mannerEvaluateRequest.getTagList().size()) {
			throw new CreationFailedException("매너 정보 등록에서 문제가 발생하였습니다.");
		}
		
		// 매너점수 변경 로직
		List<MannerTagRequest> tagList = mannerEvaluateRequest.getTagList();
		List<Integer> ratings = new ArrayList<>();
		
		for (MannerTagRequest tagRequest: tagList) {
			ratings.add(tagRequest.getScore());
		}
		
		double newScore = AdaptiveRatingCalculator.calculateAdaptiveScore(currentScore, ratings);
		
		if (mannerRepository.updateUserManner(mannerEvaluateRequest.getEvaluatorId(), newScore) == 1) {
			return true;
		}
		
		throw new UpdateFailedException("매너 점수 변경에서 문제가 발생하였습니다.");
	}


	@Override
	public List<MannerUserResponse> getMannerDetail(String userId, Long articleId) {
		
		Long tripId = articleRepository.selectTripIdByArticleId(articleId);
		
		if (tripId == null || !accompanyRepository.existsAccompanyByUserIdAndTripId(userId, tripId)) {
			throw new TripNotFoundException("함께 간 동행 정보를 찾을 수 없습니다.");
		}
		
		return accompanyRepository.findAllAccompany(tripId);
	}
	
}
