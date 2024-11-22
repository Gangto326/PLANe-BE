package com.plane.ai.service;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import com.plane.ai.dto.AiGenerateRequest;

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
						</instruction>
						<example>
						여기는 답변 예시야.
						</example>
						""";
		
		PromptTemplate template = new PromptTemplate(command);
		String message = template.render();
		
		Message systemMessage = new SystemMessage(
				"""
				지금은 테스트 중이야 아무 말이나 해줘.
				"""
		);
		
		Message userMessage = new UserMessage(message);
		
		
		return chatModel.call(systemMessage, userMessage);
	}

}
