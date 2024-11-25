package com.plane.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plane.ai.dto.AiGenerateRequest;
import com.plane.ai.service.AiService;
import com.plane.common.annotation.UserId;
import com.plane.common.response.ApiResponse;

@RestController
@RequestMapping("/api/ai")
public class AiController {

	private final AiService aiService;
	
	@Autowired
    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/generate")
    public ResponseEntity<ApiResponse<String>> generateText(
    		@UserId String userId,
    		@RequestBody AiGenerateRequest aiGenerateRequest) {
    	
        String response = aiService.generateWithTemplate(userId, aiGenerateRequest);
        return ResponseEntity.ok(ApiResponse.success(response, "AI 응답이 성공적으로 반환되었습니다."));
    }
	
}
