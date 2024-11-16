package com.plane.accompany.repository;

import java.util.List;

import com.plane.accompany.dto.AccompanyApplyDto;
import com.plane.accompany.dto.AccompanyDetailRequest;
import com.plane.accompany.dto.AccompanyResponse;
import com.plane.accompany.dto.ApplyType;

public interface AccompanyRepository {
	
	boolean existsRegistByUserIdAndArticleId(String userId, Long articleId);
	
	boolean existsAccompanyArticleByArticleId(Long articleId);

	int insertAccompanyApply(AccompanyApplyDto accompanyApplyDto);
	
	int insertApplyDetails(Long applyId, List<AccompanyDetailRequest> accompanyDetailRequest);

	List<AccompanyResponse> findAccompanyList(String userId, ApplyType type);

	

}
