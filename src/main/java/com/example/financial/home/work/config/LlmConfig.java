package com.example.financial.home.work.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class LlmConfig {
    /**
     * 大模型API Key
     */
    @Value("${deepseek.api.key}")
    private String apiKey;

    /**
     * 对话模型名称
     */
    @Value("${deepseek.model}")
    private String model;

    /**
     * 对话生成接口地址
     */
    @Value("${deepseek.api.url}")
    private String url;

}