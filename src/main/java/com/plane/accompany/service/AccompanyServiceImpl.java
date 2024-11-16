package com.plane.accompany.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.accompany.dto.AccompanyApplyDto;
import com.plane.accompany.dto.AccompanyRegistRequest;
import com.plane.accompany.dto.AccompanyResponse;
import com.plane.accompany.dto.ApplyType;
import com.plane.accompany.repository.AccompanyRepository;
import com.plane.common.exception.custom.ArticleNotFoundException;
import com.plane.common.exception.custom.CreationFailedException;
import com.plane.common.exception.custom.DuplicateException;

@Service
@Transactional
public class AccompanyServiceImpl implements AccompanyService {

	private final AccompanyRepository accompanyRepository;

	@Autowired
	public AccompanyServiceImpl(AccompanyRepository accompanyRepository) {
		this.accompanyRepository = accompanyRepository;
	}

	@Override
	public boolean sendAccompanyRegist(String userId, AccompanyRegistRequest accompanyRegistRequest) {
		
		if (!accompanyRepository.existsAccompanyArticleByArticleId(accompanyRegistRequest.getArticleId())) {
			throw new ArticleNotFoundException("게시글이 존재하지 않거나, 동행 게시글이 아닙니다.");
		}
		
		if (accompanyRepository.existsRegistByUserIdAndArticleId(userId, accompanyRegistRequest.getArticleId())) {
			throw new DuplicateException("이미 신청한 동행입니다.");
		}
		
		AccompanyApplyDto accompanyApplyDto = new AccompanyApplyDto();
		accompanyApplyDto.setUserId(userId);
		accompanyApplyDto.setArticleId(accompanyRegistRequest.getArticleId());
		
		if (accompanyRepository.insertAccompanyApply(accompanyApplyDto) != 1) {
			throw new CreationFailedException("동행 신청 추가 실패.");
		}
		
		int result = accompanyRepository.insertApplyDetails(accompanyApplyDto.getApplyId(), accompanyRegistRequest.getAccompanyDetailList());
		
		if (result == accompanyRegistRequest.getAccompanyDetailList().size()) {
			return true;
		}
		
		throw new CreationFailedException("동행 신청 응답 추가 실패.");
	}

	@Override
	public List<AccompanyResponse> getAccompanyList(String userId, ApplyType type) {
		
		return accompanyRepository.findAccompanyList(userId, type);
	}
	
	
	
}
