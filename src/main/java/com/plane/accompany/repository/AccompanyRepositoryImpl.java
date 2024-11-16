package com.plane.accompany.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.plane.accompany.dto.AccompanyApplyDto;
import com.plane.accompany.dto.AccompanyDetailRequest;
import com.plane.accompany.mapper.AccompanyMapper;

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
	public int insertApplyDetails(Long applyId, List<AccompanyDetailRequest> accompanyDetailRequest) {
		
		return accompanyMapper.insertApplyDetails(applyId, accompanyDetailRequest);
	}


	
	
}
