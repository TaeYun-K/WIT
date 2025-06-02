package com.example.WIT.config;

import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    @Bean
    public InMemoryChatMemory chatMemory() {
        // "wit-user"는 사용자별로 구분할 수 있도록 유동적으로 바꿔도 됨
        return new InMemoryChatMemory();
    }
}
