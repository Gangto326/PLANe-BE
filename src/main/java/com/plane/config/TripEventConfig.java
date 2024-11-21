package com.plane.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class TripEventConfig {
	
	@Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5); // 동시 실행 가능한 스레드 수
        scheduler.setThreadNamePrefix("task-scheduler-");
        scheduler.setErrorHandler(throwable -> {
            System.err.println("Scheduling failed: " + throwable.getMessage());
        });
        scheduler.initialize(); // 스케줄러 초기화
        return scheduler;
    }
	
}