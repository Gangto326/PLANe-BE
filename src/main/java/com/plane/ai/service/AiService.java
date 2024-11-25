package com.plane.ai.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import com.plane.ai.dto.AiGenerateRequest;
import com.plane.trip.dto.CoordinateDto;

@Service
public class AiService {
	
	private final ChatModel chatModel;
	
	public AiService(ChatModel chatModel) {
	       this.chatModel = chatModel;
	}
	
	
	public String generateWithTemplate(String userId, AiGenerateRequest aiGenerateRequest) {
		
		String command = """
						<instruction>
						너는 여행을 좋아하고 여행을 다녀온 후기를 적는 블로거야.
						내가 다녀온 여행의 일정과 느낀 점을 간단하게 말해줄게.
						이 키워드들을 가지고 여행 후기를 작성해줘.
						추가 참고사항: %s
						</instruction>
						<example>
						방문 장소들:
						%s
						</example>
						""";
		
		StringBuilder locationDetails = new StringBuilder();
	    for (CoordinateDto coordinate : aiGenerateRequest.getCoordinateDtoList()) {
	        locationDetails.append(String.format("""
	            %d번째 방문 장소:
	            - 장소명: %s
	            - 주소: %s
	            - 메모: %s
	            
	            """,
	            coordinate.getTripOrder(),
	            coordinate.getTitle(),
	            coordinate.getAddr1(),
	            coordinate.getMemo() != null ? coordinate.getMemo() : "없음"
	        ));
	    }
	    
	    Map<String, Object> variables = new HashMap<>();
	    variables.put("content", aiGenerateRequest.getContent() != null ? aiGenerateRequest.getContent() : "특별한 참고사항 없음");
	    variables.put("locations", locationDetails.toString());
	    
		PromptTemplate template = new PromptTemplate(command);
		String message = template.render(variables);
		
		
		Message systemMessage = new SystemMessage(
				"""
				여행 후기를 작성할 때는 다음 사항들을 지켜주세요:
	            1. 장소의 특징과 메모를 자연스럽게 포함시켜주세요
	            2. 전체적인 여행의 흐름이 잘 드러나도록 작성해주세요
				"""
		);
		
		
		Message userMessage = new UserMessage(message);
		
		return chatModel.call(systemMessage, userMessage);
	}

}
