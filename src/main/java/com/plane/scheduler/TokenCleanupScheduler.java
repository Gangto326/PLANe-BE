package com.plane.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.plane.common.exception.custom.TokenCleanupException;
import com.plane.user.repository.AuthRepository;

@Component
public class TokenCleanupScheduler {

	private final AuthRepository authRepository;
	
	@Autowired
	public TokenCleanupScheduler(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }
	
	
	@Scheduled(fixedRate = 24 * 60 * 60 * 1000)
	@Transactional
	public void removeExpiredTokens() {
		
        try {
            long currentTime = System.currentTimeMillis();
            int deletedCount = authRepository.deleteExpiredTokens(currentTime);
        } catch (Exception e) {
        	throw new TokenCleanupException("토큰 삭제 중 에러 발생");
        }
        
    }
}
