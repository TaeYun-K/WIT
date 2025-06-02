package com.example.WIT.chatbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatClient chatClient;
    private final InMemoryChatMemory chatMemory;

    @Autowired
    ChatController(ChatClient.Builder builder, InMemoryChatMemory chatMemory){
        this.chatClient = builder.build();
        this.chatMemory = chatMemory;
    }

    private static final String SYSTEM_PROMPT = """
    당신은 국내 여행 전문가입니다. 사용자가 여행 지역, 여행 기간, MBTI 또는 자유로운 질문을 입력하면
    해당 정보에 맞는 여행 일정을 추천해 주세요.
    
    출력은 반드시 아래의 템플릿 형식을 따라야 합니다:
    
    1일차: [추천 장소1], [추천 장소2], ...
    2일차: ...
    3일차: ...
    ...
    
    선정 이유: [여행지 추천 이유를 2~3줄로 요약]
    
    📌 추가 안내사항:
    - 입력이 "서울, 3박 4일, ISTP"처럼 지역, 기간, MBTI 형식일 경우, 성향에 맞는 여행지를 배치하세요.
    - 입력이 "국내 여행지 추천해줘"처럼 일반적인 경우에도 위 형식으로 반환하세요.
    - 실제 존재하는 한국의 도시, 명소, 관광지, 음식거리 위주로 추천해주세요.
    - 일자 수는 'n박 (n+1)일'로 계산해서 작성하세요.
    - 형식에서 벗어난 응답은 절대 하지 마세요.
    - bold 처리는 빼주세요.
    """;

    @PostMapping
    public ResponseEntity<Map<String, String>> chat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        String answer = chatClient
                .prompt()
                .system(SYSTEM_PROMPT)
                .advisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build()
                )
                .user(message)
                .call()
                .content();
        return ResponseEntity.ok(Map.of("answer", answer));
    }
}
