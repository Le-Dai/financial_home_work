package com.example.financial.home.work.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LangChainConfig {
    @Resource
    LlmConfig llmConfig;
    /**
     * 这里自动创建 ChatLanguageModel 交给 Spring 管理
     * 支持 DeepSeek / Ollama / 通义千问 / 讯飞 等等
     */
    @Bean
    public ChatLanguageModel chatLanguageModel() {

        // 你用 DeepSeek 就用这个配置（兼容 OpenAI 协议）
        return OpenAiChatModel.builder()
                .apiKey(llmConfig.getApiKey())
                .modelName(llmConfig.getModel())
                .baseUrl(llmConfig.getUrl())
                .build();
    }
}