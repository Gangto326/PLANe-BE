package com.plane.accompany.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plane.accompany.dto.AccompanyApplyDto;
import com.plane.accompany.dto.AccompanyRegistRequest;
import com.plane.accompany.dto.AccompanyResponse;
import com.plane.accompany.dto.AccompanyUpdateRequest;
import com.plane.accompany.dto.ApplyType;
import com.plane.accompany.repository.AccompanyRepository;
import com.plane.common.exception.custom.ArticleNotFoundException;
import com.plane.common.exception.custom.CreationFailedException;
import com.plane.common.exception.custom.DuplicateException;
import com.plane.common.exception.custom.InvalidParameterException;
import com.plane.common.exception.custom.RegistNotFoundException;
import com.plane.common.exception.custom.UpdateFailedException;

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
		
		int result = accompanyRepository.insertApplyDetails(accompanyApplyDto.getApplyId(), accompanyRegistRequest.getAccompanyDetailList(), false);
		
		if (result == accompanyRegistRequest.getAccompanyDetailList().size()) {
			return true;
		}
		
		throw new CreationFailedException("동행 신청 응답 추가 실패.");
	}

	@Override
	public List<AccompanyResponse> getAccompanyList(String userId, String type) {
		
		ApplyType applyType = ApplyType.from(type);
		
		return accompanyRepository.findAccompanyList(userId, applyType);
	}

	@Override
	public boolean updateAccompany(String userId, AccompanyUpdateRequest accompanyUpdateRequest) {
		
		if (accompanyUpdateRequest == null || accompanyUpdateRequest.getAccompanyDetailList() == null) {
	        throw new InvalidParameterException("요청 데이터가 올바르지 않습니다.");
	    }
		
		if (!accompanyRepository.existsRegistByUserIdAndApplyId(userId, accompanyUpdateRequest.getApplyId())) {
			throw new RegistNotFoundException("해당 동행 신청을 찾을 수 없습니다.");
		}
		
		// 전부 삭제
		accompanyRepository.deleteAllApplyDetails(accompanyUpdateRequest.getApplyId());
		
		// 전부 삽입
		int insertCount = accompanyRepository.insertApplyDetails(accompanyUpdateRequest.getApplyId(), accompanyUpdateRequest.getAccompanyDetailList(), true);
		
		if (insertCount != accompanyUpdateRequest.getAccompanyDetailList().size()) {
            throw new CreationFailedException("답변 등록 중 오류가 발생했습니다.");
        }
		
		// 상태 변경 (수정)
		if (accompanyRepository.updateApplyStatus(userId, accompanyUpdateRequest.getApplyId()) == 1) {
			return true;
		}
		
		throw new UpdateFailedException("동행 신청 응답 수정 실패.");
	}
	
	@Override
	public boolean deleteAccompany(String userId, Long applyId) {
		
		if (!accompanyRepository.existsRegistByUserIdAndApplyId(userId, applyId)) {
			throw new RegistNotFoundException("해당 동행 신청을 찾을 수 없습니다.");
		}
		
		if (accompanyRepository.deleteAccompany(userId, applyId) == 1) {
			return true;
		}
		
		throw new UpdateFailedException("동행 신청 삭제 실패.");
	}
	
}
