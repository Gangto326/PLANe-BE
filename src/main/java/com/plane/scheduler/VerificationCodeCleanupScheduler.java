package com.plane.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.plane.common.exception.custom.TokenCleanupException;
import com.plane.user.repository.UserRepository;

@Component
public class VerificationCodeCleanupScheduler {
	
	private final UserRepository userRepository;

	@Autowired
	public VerificationCodeCleanupScheduler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	
	@Scheduled(fixedRate = 24 * 60 * 60 * 1000)
	@Transactional
	public void removeExpiredTokens() {
		
        try {
            int deletedCount = userRepository.deleteVerificationCodes();
        } catch (Exception e) {
        	throw new TokenCleanupException("인증 코드 삭제 중 에러 발생");
        }
        
    }
	
}
