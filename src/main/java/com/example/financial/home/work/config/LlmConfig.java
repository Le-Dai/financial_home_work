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
    @Value("${deepseek.api.model}")
    private String model;

    /**
     * 对话生成接口地址
     */
    @Value("${deepseek.api.url}")
    private String url;

    /**
     * 文本向量化接口地址
     */
    @Value("${aliyun.embedding.url}")
    private String embeddingUrl;

    /**
     * 向量化模型名称
     */
    @Value("${aliyun.embedding.model}")
    private String embeddingModel;

    /**
     * 向量化模型名称
     */
    @Value("${aliyun.embedding.key}")
    private String embeddingKey;
}