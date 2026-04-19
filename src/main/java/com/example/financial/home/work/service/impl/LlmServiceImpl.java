package com.example.financial.home.work.service.impl;

import com.example.financial.home.work.config.LlmConfig;
import com.example.financial.home.work.entity.ChatRecord;
import com.example.financial.home.work.service.LlmService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class LlmServiceImpl implements LlmService {

    @Resource
    LlmConfig llmConfig;
    @Resource
    RestTemplate restTemplate;

    // ============================
    // 接口2：DeepSeek 向量化（修复401）
    // ============================
    @Override
    public List<Double> embedding(String text) {
        RestTemplate restTemplate = new RestTemplate();
        // 1. 构建请求头（官方标准）
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // ✅ 正确：必须加 Bearer
        headers.set("Authorization", "Bearer " + llmConfig.getEmbeddingKey().trim());

        // 2. 构建请求体（官方标准，无task字段）
        Map<String, Object> requestBody = Map.of(
                "model", llmConfig.getEmbeddingModel(), // 或v3/v4
                "input", Map.of("texts", new String[]{text})
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        // 3. 正确URL（关键！）
        String url = llmConfig.getEmbeddingUrl();

        try {
            // 发送请求
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            Map<String, Object> body = response.getBody();

            Map<String, Object> output = (Map<String, Object>) body.get("output");
            List<Map<String, Object>> embeddings = (List<Map<String, Object>>) output.get("embeddings");
            Map<String, Object> firstEmbedding = embeddings.get(0);

            return (List<Double>) firstEmbedding.get("embedding");
        } catch (Exception e) {
            throw new RuntimeException("向量化失败：" + e.getMessage(), e);
        }
    }
    @Override
    public String generateAnswer(String prompt) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // 请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + llmConfig.getApiKey().trim());

            // ===============================
            // 🔥 关键：必须构造 messages 数组
            // ===============================
            Map<String, Object> message = Map.of(
                    "role", "user",
                    "content", prompt
            );

            Map<String, Object> requestBody = Map.of(
                    "model", llmConfig.getModel(),  // 模型名正确
                    "messages", List.of(message) // 必须传 messages！！！
            );

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // 发送请求
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    llmConfig.getUrl(),
                    request,
                    Map.class
            );

            // 解析返回
            Map<String, Object> body = response.getBody();
            Map<String, Object> choices = ((List<Map<String, Object>>) body.get("choices")).get(0);
            Map<String, Object> resMessage = (Map<String, Object>) choices.get("message");

            return resMessage.get("content").toString();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("DeepSeek 调用失败：" + e.getMessage());
        }
    }
    // ==============================
    // 接口4：上下文语义增强（补全问题）
    // ==============================
    @Override
    public String contextEnhance(String question, List<ChatRecord> history) {
        try {
            // 1. 拼接历史对话
            StringBuilder historyPrompt = new StringBuilder();
            for (ChatRecord record : history) {
                historyPrompt.append("用户：").append(record.getQuestion()).append("\n");
                historyPrompt.append("助手：").append(record.getAnswer()).append("\n");
            }

            // 2. 构造增强提示词（让大模型补全省略指代）
            String enhancePrompt = """
                    你是一个上下文补全助手。
                    根据历史对话，把用户当前问题里的省略、指代补全成完整、明确、独立的问题。
                    只输出最终补全后的问题，不要解释。
                                        
                    历史对话：
                    %s
                                        
                    当前问题：%s
                    """.formatted(historyPrompt, question);

            // 3. 直接调用大模型生成
            return generateAnswer(enhancePrompt);

        } catch (Exception e) {
            log.error("上下文增强失败，返回原问题", e);
            return question; // 失败则返回原问题
        }
    }
}